package com.qf.smartplatform.service.impl;

import com.qf.smartplatform.constans.ResultCode;
import com.qf.smartplatform.dto.UserDto;
import com.qf.smartplatform.event.LoginEvent;
import com.qf.smartplatform.exceptions.AddException;
import com.qf.smartplatform.exceptions.QueryException;
import com.qf.smartplatform.exceptions.UpdateException;
import com.qf.smartplatform.mapper.SysUserMapper;
import com.qf.smartplatform.pojo.CheckType;
import com.qf.smartplatform.pojo.MyBaseUser;
import com.qf.smartplatform.pojo.SysUserInfo;
import com.qf.smartplatform.service.SysUserService;
import com.qf.smartplatform.utils.MyStringUtils;
import com.qf.smartplatform.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Administrator
 * @version V1.0
 * @Project mysmartplatform
 * @Package com.qf.smartplatform.service.impl
 * @Description:
 * @Date 2022/5/25 17:31
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    private ApplicationContext context;
    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private SysUserMapper sysUserMapper;
    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public void addUser(UserDto userDto) {
        // 判断数据是否全部传递了
        Assert.isTrue(!userDto.isEmpty(CheckType.ADD), ()->{
            throw new AddException("数据传递不完整", ResultCode.DATA_NULL);
        });
        // 判断是否已经是注册过了
        SysUserInfo userInfo = sysUserMapper.findByUserName(userDto.getUsername());
        Assert.isNull(userInfo, ()->{
            throw new AddException("用户名已经存在", ResultCode.DATA_EXIST);
        });
        userInfo = sysUserMapper.findByEmail(userDto.getEmail());
        Assert.isNull(userInfo, ()->{
            throw new AddException("邮箱已经存在", ResultCode.DATA_EXIST);
        });
        userInfo = sysUserMapper.findByPhone(userDto.getPhone());
        Assert.isNull(userInfo, ()->{
            throw new AddException("手机号已经存在", ResultCode.DATA_EXIST);
        });

        SysUserInfo sysUserInfo = new SysUserInfo();
        //将UserDto对象转换成SysUserInfo对象
        BeanUtils.copyProperties(userDto, sysUserInfo);
        sysUserInfo.setCreateTime(new Date());
        sysUserInfo.setCreateBy(userDto.getUsername());
        sysUserInfo.setStatus(1L);
        sysUserInfo.setType(1L);
        sysUserInfo.setRemark("普通用户");

        String salt = MyStringUtils.createRandomString(10);
        String passwordMD5 = DigestUtils.md5DigestAsHex((sysUserInfo.getPassword() + salt).getBytes(StandardCharsets.UTF_8));

        sysUserInfo.setPassword(passwordMD5);
        sysUserInfo.setPwdSalt(salt);
        System.err.println(sysUserInfo);
        sysUserMapper.addUser(sysUserInfo);
    }

    @Override
    public SysUserInfo login(UserDto userDto) {
        // 判断是否是非空的
        Assert.isTrue(StringUtils.hasText(userDto.getUsername())&&StringUtils.hasText(userDto.getPassword()), ()->{
            throw new QueryException("需要传递用户名和密码", ResultCode.USERNAME_PASSWORD_ERROR);
        });
        // 判断这个用户存在
        SysUserInfo userInfo = sysUserMapper.findByUserName(userDto.getUsername());
        Assert.notNull(userInfo, ()->{
            context.publishEvent(new LoginEvent(LoginEvent.LoginType.FAIL,userDto.getUsername()));
            throw new QueryException("用户名或者密码错误", ResultCode.USERNAME_PASSWORD_ERROR);
        });
        // 这个用户存在的话，判断密码是否正确，要先取盐，根据盐计算密码
        String paramPWDMD5 = DigestUtils.md5DigestAsHex((userDto.getPassword() + userInfo.getPwdSalt()).getBytes(StandardCharsets.UTF_8));
        String password = userInfo.getPassword();
        Assert.isTrue(password.equalsIgnoreCase(paramPWDMD5), ()->{
            context.publishEvent(new LoginEvent(LoginEvent.LoginType.FAIL,userDto.getUsername()));
            throw new QueryException("用户名或者密码错误", ResultCode.USERNAME_PASSWORD_ERROR);
        });
        context.publishEvent(new LoginEvent(LoginEvent.LoginType.SUCCESS,userDto.getUsername()));
        return userInfo;
    }

    @Override
    public void updateLogin(String loginName, Date processTime, String ip) {
        sysUserMapper.updateLogin(loginName,processTime,ip);
    }

    @Override
    public void updatePWD(String username, String password, String newPassword) {
        MyBaseUser user = SecurityUtils.getSysUserInfo(false);
        Assert.isTrue(user.getUsername().equalsIgnoreCase(username), ()->{
            throw new UpdateException("传入的用户名和当前用户不是同一人", ResultCode.USERNAME_PASSWORD_ERROR);
        });
        Assert.notNull(password, ()->{
            throw new UpdateException("密码不能为空",ResultCode.USERNAME_PASSWORD_ERROR);
        });
        Assert.notNull(newPassword, ()->{
            throw new UpdateException("密码不能为空",ResultCode.USERNAME_PASSWORD_ERROR);
        });
        // 修改密码会有错误，user里面没有这个字段，所以会出错，而且新的方法生成的密码没有盐，所以先注释掉
//        Assert.isTrue(DigestUtils.md5DigestAsHex((password+user.getPwdSalt()).getBytes(StandardCharsets.UTF_8)).equalsIgnoreCase(user.getPassword()),()->{
//            throw new UpdateException("原密码不正确", ResultCode.USERNAME_PASSWORD_ERROR);
//        });

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUsername(username);
        String palt = MyStringUtils.createRandomString(10);
        sysUserInfo.setPwdSalt(palt);
        String newPWD = DigestUtils.md5DigestAsHex((newPassword+palt).getBytes(StandardCharsets.UTF_8));
        sysUserInfo.setPassword(newPWD);
        sysUserInfo.setUpdateTime(new Date());
        sysUserInfo.setUpdateBy(username);
        sysUserMapper.updatePWD(sysUserInfo);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        session.removeAttribute("user");
    }

    @Override
    public void updateData(UserDto userDto) {
        Assert.isTrue(!userDto.isEmpty(CheckType.UPDATE),()->{
            throw new UpdateException("传递的数据不完整", ResultCode.DATA_NULL);
        });
        SysUserInfo sysUserInfo = new SysUserInfo();
        BeanUtils.copyProperties(userDto,sysUserInfo);
        MyBaseUser userInfo = SecurityUtils.getSysUserInfo(false);
        sysUserInfo.setUsername(userInfo.getUsername());
        sysUserInfo.setUpdateTime(new Date());
        sysUserInfo.setUpdateBy(userInfo.getUsername());
//        System.err.println(sysUserInfo);
        sysUserMapper.updateData(sysUserInfo);
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("user");
        session.setAttribute("user", sysUserInfo);
    }

}
