package com.qf.smartplatform.utils;


import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class RequestUtil {

	private static String serverUrlPre = "https://www.ip.cn/ip/";
	private static RestTemplate restTemplate = new RestTemplate();

	/**
	 * 获取真实IP地址
	 */
	public static String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		System.out.println("请求来源IP:" + ip);
		return ip;
	}

	public static String getPlatForm(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		if (agent == null) {
			return "(Unknown Device)";
		}
		try {
			agent = agent.substring(agent.indexOf("("), agent.length());
		} catch (Exception e) {
			return agent;
		}
		return agent;
	}

	public static boolean isAjax(HttpServletRequest request) {
		String header = request.getHeader("x-requested-with");
		if (StringUtils.isEmpty(header)) {
			return false;
		}
		return true;
	}

	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		return getParameterMap(request, new String[] {});
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParameterMap(HttpServletRequest request, String... excludeArgs) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if (includeArgs(name, excludeArgs)) {
				continue;
			}
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"),"gbk");
			try {
				valueStr = URLDecoder.decode(valueStr, "UTF-8");
				params.put(name, valueStr);
			} catch (Exception e) {
				params.put(name, valueStr);
			}
		}
		System.out.println(params);
		return params;
	}

	public static boolean includeArgs(String param, String... excludeArgs) {
		if (excludeArgs == null) {
			return false;
		}
		for (int i = 0; i < excludeArgs.length; i++) {
			if (param.equals(excludeArgs[i])) {
				return true;
			}
		}
		return false;
	}

	public static void setParameterMap(HttpServletRequest request, Map<String, Object> params) {
		if (params == null)
			return;
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> next = iterator.next();
			request.setAttribute(next.getKey(), next.getValue());
		}
	}


	public static Map<String, String> getOsAndBrowserInfo(HttpServletRequest request) {
		Map<String, String> map = Maps.newHashMap();
		String browserDetails = request.getHeader("User-Agent" );
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";
		//=================OS Info=======================
		if (userAgent.toLowerCase().contains("windows" )) {
			os = "Windows";
		} else if (userAgent.toLowerCase().contains("mac" )) {
			os = "Mac";
		} else if (userAgent.toLowerCase().contains("x11" )) {
			os = "Unix";
		} else if (userAgent.toLowerCase().contains("android" )) {
			os = "Android";
		} else if (userAgent.toLowerCase().contains("iphone" )) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		//===============Browser===========================
		if (user.contains("edge" )) {
			browser = (userAgent.substring(userAgent.indexOf("Edge" )).split(" " )[0]).replace("/" , "-" );
		} else if (user.contains("msie" )) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE" )).split(";" )[0];
			browser = substring.split(" " )[0].replace("MSIE" , "IE" ) + "-" + substring.split(" " )[1];
		} else if (user.contains("safari" ) && user.contains("version" )) {
			browser = (userAgent.substring(userAgent.indexOf("Safari" )).split(" " )[0]).split("/" )[0]
					+ "-" + (userAgent.substring(userAgent.indexOf("Version" )).split(" " )[0]).split("/" )[1];
		} else if (user.contains("opr" ) || user.contains("opera" )) {
			if (user.contains("opera" )) {
				browser = (userAgent.substring(userAgent.indexOf("Opera" )).split(" " )[0]).split("/" )[0]
						+ "-" + (userAgent.substring(userAgent.indexOf("Version" )).split(" " )[0]).split("/" )[1];
			} else if (user.contains("opr" )) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR" )).split(" " )[0]).replace("/" , "-" ))
						.replace("OPR" , "Opera" );
			}
		} else if (user.contains("chrome" )) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome" )).split(" " )[0]).replace("/" , "-" );
		} else if ((user.contains("mozilla/7.0" )) || (user.contains("netscape6" )) ||
				(user.contains("mozilla/4.7" )) || (user.contains("mozilla/4.78" )) ||
				(user.contains("mozilla/4.08" )) || (user.contains("mozilla/3" ))) {
			browser = "Netscape-?";
		} else if (user.contains("firefox" )) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox" )).split(" " )[0]).replace("/" , "-" );
		} else if (user.contains("rv" )) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv" )).split(" " )[0]).replace("rv:" , "-" );
			browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		map.put("os" , os);
		map.put("browser" , browser);
		return map;
	}

	public static String getLocationByIp(String ip) {
		if (!StringUtils.hasText(ip)) {
			return "未知地址";
		}

		try {
			//String ipLocationInfo = restTemplate.getForObject(serverUrlPre + ip + ".html", String.class);
			//和地址相关的数据是在<div id="tab0_address">中国 江苏省 南京市 </div> 这个div中
			URL url = new URL(serverUrlPre + ip + ".html");
			HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.addRequestProperty("authority", "www.ip.cn");
			httpURLConnection.addRequestProperty("method", "GET");
			httpURLConnection.addRequestProperty("scheme", "https");
			httpURLConnection.addRequestProperty("path", "/ip/"+ip+".html");
			httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36");
			httpURLConnection.addRequestProperty("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
			httpURLConnection.addRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

			httpURLConnection.connect();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			String ipLocationInfo = stringBuffer.toString();
			String substring = ipLocationInfo.substring(ipLocationInfo.indexOf("<div id=\"tab0_address\">")).replace("<div id=\"tab0_address\">", "");
			String location = substring.substring(0, substring.indexOf("</div>"));
			return location;
		}catch (Exception e){
		   e.printStackTrace();
		}
		return "未知地址";
	}
}
