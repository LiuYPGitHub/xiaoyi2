package com.xiaoyi.bis.xiaoyi.util;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

/**
 * 添翼申学接口工具类
 * des加密，传入对应的加密串，然后可以对字符串进行加密和解密
 * key的有效长度为8
 * @author syy
 */
public class XiaoYiSecurityDesUtil {

	private Key key;
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * 传入加密串，解密时需要传入同样的串
	 * 
	 * @param keyStr
	 * @throws Exception
	 */
	public XiaoYiSecurityDesUtil(String keyStr) throws Exception {
		Security.addProvider(new SunJCE());
		/**
		 * 加密的钥匙串
		 */
		key = getKey(keyStr.getBytes());
	}

	/**
	 * 
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return
	 * @throws Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 二进制转十六进制
	 * @param arrB
	 * @return
	 * @throws Exception
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}
	
	/**
	 * 十六进制转二进制
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
       return hexStr2ByteArr(strIn, "UTF-8");
    }
	
	/**
	 * 十六进制转二进制
	 * @param strIn
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static byte[] hexStr2ByteArr(String strIn,String charset) throws Exception {
		byte[] arrB = strIn.getBytes(charset);
		int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

	/**
	 * 加密字节
	 * 
	 * @param arrB
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		if (encryptCipher == null) {
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		}
		return encryptCipher.doFinal(arrB);
	}
	
	/**
	 * 加密字符串
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节
	 * 
	 * @param arrB
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		if (decryptCipher == null) {
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		}
		return decryptCipher.doFinal(arrB);
	}
	
	/**
	 * 解密字符串
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

}
