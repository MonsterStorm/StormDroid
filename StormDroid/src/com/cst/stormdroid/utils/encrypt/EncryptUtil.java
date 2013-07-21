package com.cst.stormdroid.utils.encrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import android.util.Base64;
import android.util.Base64OutputStream;

import com.cst.stormdroid.utils.log.SDLog;

/**
 * Encrypt util for encrypt string
 * @author MonsterStorm
 * @version 1.0
 */
public class EncryptUtil {
	private static final String TAG = EncryptUtil.class.getSimpleName();

	public enum EncryptMethod {
		NONE, MD5, SHA_1, SHA_256, SHA_384, SHA_512
	};

	/**
	 * Encrypt a string
	 * @param pass
	 * @param algorithm
	 * @return encrypted string
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String from, EncryptMethod algorithm) {
		String to = null;
		try {
			if (algorithm == null || algorithm == EncryptMethod.MD5) {// MD5
				to = new String(Hex.encodeHex(DigestUtils.md5(from)));
			} else if (algorithm == EncryptMethod.NONE) {// NONE
				to = from;
			} else if (algorithm == EncryptMethod.SHA_1) {// SHA_1
				to = new String(Hex.encodeHex(DigestUtils.sha1(from)));
			} else if (algorithm == EncryptMethod.SHA_256) {// SHA_256
				to = new String(Hex.encodeHex(DigestUtils.sha256(from)));
			} else if (algorithm == EncryptMethod.SHA_384) {// SHA_384
				to = new String(Hex.encodeHex(DigestUtils.sha384(from)));
			} else if (algorithm == EncryptMethod.SHA_512) {// SHA_512
				to = new String(Hex.encodeHex(DigestUtils.sha512(from)));
			} else {
				to = new String(Hex.encodeHex(DigestUtils.sha(from)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return to;
	}

	/**
	 * encrypt file
	 * @param filename
	 * @param algorithm
	 */
	public static void encryptFile(String filename, String algorithm) {
		byte[] b = new byte[1024 * 4];
		int len = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			fis = new FileInputStream(filename);
			while ((len = fis.read(b)) != -1) {
				md.update(b, 0, len);
			}
			byte[] digest = md.digest();
			StringBuffer fileNameBuffer = new StringBuffer(128).append(filename).append(".").append(algorithm);
			fos = new FileOutputStream(fileNameBuffer.toString());
			OutputStream encodedStream = new Base64OutputStream(fos, Base64.DEFAULT);
			encodedStream.write(digest);
			encodedStream.flush();
			encodedStream.close();
		} catch (Exception e) {
			SDLog.e(TAG, "Error computing Digest: " + e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception ignored) {
			}
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ignored) {
			}
		}
	}
}
