package com.photography.utils;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

/**
 * 加密工具类

 * @since 2011-8-11
 * @author 李源
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class DESUtil {

	private static Logger log = Logger.getLogger(DESUtil.class);

	private static byte[] key = "123456789abcdefg".getBytes();
	private static BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new DESEngine());

	public static String encrypt(String src) {
		try {
			cipher.init(true, new KeyParameter(key));
			byte[] input = src.getBytes();
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
			int outputLen = cipher.processBytes(input, 0, input.length, cipherText, 0);
			cipher.doFinal(cipherText, outputLen);
			return new String(Hex.encode(cipherText));
		} catch (CryptoException ce) {
			log.error("encrypt error:" + ce.getMessage());
			return src;
		} catch (DataLengthException de) {
			log.error("encrypt runtime error:" + de.getMessage());
			return src;
		} catch (Exception e) {
			log.error("encrypt other error:" + e.getMessage());
			return src;
		}

	}

	public static String decrypt(String src) {
		byte[] cipherText, dencypted, trimed;
		try {
			cipher.init(false, new KeyParameter(key));
			cipherText = Hex.decode(src.getBytes());
			dencypted = new byte[cipher.getOutputSize(cipherText.length)];

			int outputLen = cipher.processBytes(cipherText, 0, cipherText.length, dencypted, 0);

			cipher.doFinal(dencypted, outputLen);
			trimed = trimBytes(dencypted);
			return new String(trimed);
		} catch (CryptoException ce) {
			log.error("decrypt error:" + ce.getMessage());
			return src;
		} catch (DataLengthException de) {
			log.error("decrypt runtime error:" + de.getMessage());
			return src;
		} catch (Exception e) {
			log.error("decrypt other error:" + e.getMessage());
			return src;
		}
	}

	private static byte[] trimBytes(byte[] src) {
		int length = src.length;
		for (int i = src.length - 1; i >= 0; i--) {
			if (src[i] == 0) {
				length--;
			} else {
				break;
			}
		}
		byte[] dest = new byte[length];
		for (int i = 0; i < length; i++) {
			dest[i] = src[i];
		}
		return dest;
	}
}
