package com.wang.lms.util;

import java.security.MessageDigest;
import java.util.Arrays;


public class MD5 {
	
	/**
	 * MD5加密的方法 (MD5加密不可逆)
	 * @param str 明文
	 * @return 加密后的内容(32位长度的字符串)
	 * @throws Exception 
	 */
	public static String getMD5(String str) throws Exception {
		/** 创建加密对象 */
		MessageDigest md = MessageDigest.getInstance("MD5");
		/** 进行加密 */
		md.update(str.getBytes("utf-8"));
		/** 获取加密后的内容  返回的加密字节数组永远是16位*/
		byte[] md5Bytes = md.digest();
		
		System.out.println("加密前：" + Arrays.toString(str.getBytes("utf-8")));
		System.out.println("加密后：" + Arrays.toString(md5Bytes));

		/** 
		 * 把加密后的字节数组16位，转化成32，把其中一位转化成16进制的2位，
		 * 如果转化16进制中够两位前面补零 */
		String res = "";
		for (int i = 0; i < md5Bytes.length; i++){
			int temp = md5Bytes[i] & 0xFF;
			if (temp <= 0XF){
				res += "0";
			}
			res += Integer.toHexString(temp);
		}
		return res;
	}
}
