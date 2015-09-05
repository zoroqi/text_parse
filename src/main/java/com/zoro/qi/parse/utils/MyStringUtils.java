package com.zoro.qi.parse.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 常用方法
 * @author ZQ
 *
 */
public class MyStringUtils {
	
	
	private final static Log logger = LogFactory.getLog(MyStringUtils.class);
	
	/**
	 * 当前工作路径
	 */
	public static String USERDIR = System.getProperty("user.dir");


	/**
	 * 字符串转换成 MD5 值
	 */
	public static String md5Hex32(String str) {
		StringBuffer hexString = new StringBuffer();
		if (str != null && str.trim().length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte[] hash = md.digest();
				for (int i = 0; i < hash.length; i++) {
					if ((0xff & hash[i]) < 0x10) {
						hexString.append("0"
								+ Integer.toHexString((0xFF & hash[i])));
					} else {
						hexString.append(Integer.toHexString(0xFF & hash[i]));
					}
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
				return "";
			}

		}
		return hexString.toString(); // 8~24位
	}
	
	/**
	 * 得到正则内容
	 * @param regex 正则
	 * @param src 数据
	 * @param patternCase 模式
	 * @param groupNum 分组 -1 为提取 0 或 1组
	 * @param i 取第几次匹配结果
	 * @return
	 */
	public static List<String> getRegexContent(String regex, String src, int patternCase, int groupNum, int i) {
		String ret = "";
		Pattern pTemp = Pattern.compile(regex,patternCase);
		Matcher mTemp = pTemp.matcher(src);
		
		List<String> list = new ArrayList<String>();
		
		int index = 0;
		while(mTemp.find()){
			if (-1 == groupNum) {
				if (0 == mTemp.groupCount() ) {
					ret = mTemp.group();
				} else {
					ret = mTemp.group(1);
				}
			} else {
				ret = mTemp.group(groupNum);
			}
			list.add(ret);
			if (index == i) {
				break;
			}
			index++;
		}
		
		if (list.size() == 0) {
			list.add("");
		}
		
		return list;
	}
	
	/**
	 * 得到正则内容 分组 -1 为提取 0 或 1组
	 * @param regex 正则
	 * @param src 数据
	 * @param patternCase 模式
	 * @return
	 */
	public static String getRegexContent(String regex, String src, int patternCase) {
		return getRegexContent(regex,src,patternCase,-1,0).get(0);
	}
	
	public static List<String> getRegexContents(String regex, String src, int patternCase) {
		return getRegexContent(regex,src,patternCase,-1,Integer.MAX_VALUE);
	}
	
	/**
	 * 删除HTML标签语言保留空格
	 * @param str
	 * @return
	 */
	public static String deleteHtmlLabel(String str) {
		if (str == null || "".equals(str)) {
			return "";
		}
		if (str.contains("<") || str.contains(">")) {
			str = str.replaceAll("(?i)<script[\\s\\S]*?</script>", " ");
			str = str.replaceAll("(?i)<style[\\s\\S]*?</style>", " ");
			str = str.replaceAll("<!--[\\s\\S]*?-->", " ");
			str = str.replaceAll("<[^>]*?>", " ");
			str = str.replaceAll("\\s+", " ");
		}
		return str.trim();
	}
	
	/**
	 * 得到图片Base64编码
	 * @param imgUrl 图片地址
	 * @param referer 父链接
	 * @return
	 */
	/*public static String imgToBase64(String imgUrl,String referer) {
		ScaledImage2Base64 scaled =new ScaledImage2Base64();
		if (imgUrl == null || "".equals(imgUrl)) {
			return "";
		}
		if (!imgUrl.startsWith("http")) {
			return "";
		}
		return scaled.parse(imgUrl,referer);
	}
	
	/**
	 * 获得url的页面代码
	 * @param url
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 
	public static String getUrlContent(String url) throws Exception {
		Thread.sleep(Constant.IMG_SLEEP_TIME);
		ZQHttpClientBuilder httpClientBuilder = new ZQHttpClientBuilder();
		
		ZQHttpGet webHttpGet = new ZQHttpGet(httpClientBuilder.getHttpBuild());
		
		ZQResponse response = webHttpGet.getResponse(url);
		
		String content = "";
		if (!response.isGet()) {
			return "";
		}
		content = new String(response.getContent(), response.getCharset());

		return content;
	}*/
	
	/**
	 * 将相对路径转换为合法的 URL (http[s]?://xxx.xxx.xxx)
	 * @param s
	 * @return
	 */
	public static String getURLs(String parentURL,String url){
		
		URL  u = null;
		try {
			u = new URL(new URL(parentURL),url);
		} catch (MalformedURLException e) {
			return "";
		}
		
       return u.toExternalForm();
		
	}
	public static void main(String[] args) {
		System.out.println("<".contains("<") || "<".contains(">"));
	}
}
