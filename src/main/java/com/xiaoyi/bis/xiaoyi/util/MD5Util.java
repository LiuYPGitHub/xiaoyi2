package com.xiaoyi.bis.xiaoyi.util;

import com.xiaoyi.bis.xiaoyi.constant.APIConstant;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * 添翼申学接口工具类
 * @author CJ
 */
public class MD5Util {
	public static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };
	
	public final static String getMD5(byte[] bytes) {
		String mdStr = null;
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(bytes);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			mdStr = new String(str);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return mdStr;
	}

	public final static String getMD5(String s) {
		return getMD5(s, "UTF-8");
	}

	public final static String getMD5(String s, String charset) {
		try {
			return getMD5(s.getBytes(charset));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String Ksort(Map<String, String> map){
		String str = "";
		String[] key = new String[map.size()];
		int index = 0;
		for (String k : map.keySet()) {
			key[index] = k;
			index++;
		}
		Arrays.sort(key);
		for (String s : key) {
			str += s + "=" + map.get(s) + "&";
		}
		String appentStr= str + APIConstant.API_PREFIX;
		return MD5Util.getMD5(appentStr);
	}
	
}