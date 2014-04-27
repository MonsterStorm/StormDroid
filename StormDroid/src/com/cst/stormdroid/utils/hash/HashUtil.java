package com.cst.stormdroid.utils.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hash manager
 * @author MonsterStorm
 */
public class HashUtil {
	/**
	 * md5
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String s) {
		return encrypt(s, "MD5");
	}

	/**
	 * to hex string
	 * @param keyData
	 * @return
	 */
	private static String toHexString(byte[] keyData) {
		if (keyData == null) {
			return null;
		}
		int expectedStringLen = keyData.length * 2;
		StringBuilder sb = new StringBuilder(expectedStringLen);
		for (int i = 0; i < keyData.length; i++) {
			String hexStr = Integer.toString(keyData[i] & 0x00FF, 16);
			if (hexStr.length() == 1) {
				hexStr = "0" + hexStr;
			}
			sb.append(hexStr);
		}
		return sb.toString();

	}

	/**
	 * SHA1 algorithm
	 */
	public static String sha1(String s) {
		return encrypt(s, "SHA-1");
	}

	/**
	 * encrypt method
	 * 
	 * @param s
	 * @param method
	 *            encrypt type
	 * @return
	 */
	private static String encrypt(String s, String method) {
		try {
			MessageDigest digest = MessageDigest.getInstance(method);
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
