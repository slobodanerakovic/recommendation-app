package com.recommendation.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Slobodan Erakovic - class is used for encryption/decryption of
 *         sensitive information, currently passed over network, like customerID
 */
public class StandardLib {

	private static final Logger LOG = LoggerFactory.getLogger(StandardLib.class);

	private static String rc4Key = "&h01 &h23 &h45 &h89 &hab &hcd &hef";

	public static String encodeId(final long id) {
		final String encAction = new RC4().doEncryption(rc4Key, String.valueOf(id));
		String idStr = Base64.getEncoder().encodeToString(encAction.getBytes());
		idStr = idStr.replace("/", "_");
		try {
			return URLEncoder.encode(idStr, "UTF-8");
		} catch (final Exception e) {
			return idStr;
		}
	}

	public static long decodeId(final String id) {
		String idString = id;
		try {
			try {
				idString = URLDecoder.decode(idString, "UTF-8");
			} catch (final Exception e1) {
				LOG.error("decodeStrId() " + id + " is not UrlEncoded!", e1);
			}

			idString = idString.replace("_", "/");

			final byte[] decodedId = Base64.getDecoder().decode(idString);
			idString = new String(decodedId);
		} catch (final Exception ex) {
			return -1;
		}
		long idLong = -1;
		try {
			idLong = Long.parseLong(new RC4().doEncryption(rc4Key, idString));
		} catch (final Exception e) {
			LOG.debug("decodeId() Decypher failed!");
		}
		return idLong;
	}

	public static class RC4 {

		final int[] cipherBox = new int[256];
		final int[] cipherKeyArray = new int[256];

		public String doEncryption(final String rc4Key, final String cipherString) {
			// ----Encryption & DecryptionKey
			// ----*****Warning******
			// ----Changing the rc4Key value without recording it will result
			// ----in data loss of any data that this key was used to encrypt

			return doCipher(rc4Key, cipherString);
		}

		private String doCipher(final String cipherKey, final String unencodedText) {
			int z = 0;
			int t = 0;
			int i = 0;
			int cipherBy = 0;
			int tempInt = 0;
			String cipher = "";
			char cipherText;

			doRC4MatrixSeed(cipherKey);

			for (int a = 0; a < unencodedText.length(); a++) {

				i = (i + 1) % 255;
				t = (t + cipherBox[i]) % 255;
				tempInt = cipherBox[i];
				cipherBox[i] = cipherBox[t];
				cipherBox[t] = tempInt;

				z = cipherBox[(cipherBox[i] + cipherBox[t]) % 255];

				// get character at position a
				cipherText = unencodedText.charAt(a);

				// convert to ascii value XOR'd by z
				cipherBy = (int) cipherText ^ z;
				cipher = cipher + (char) cipherBy;
			}
			return cipher;

		}

		private void doRC4MatrixSeed(final String thisKey) {
			// Initialize cipherBox and cipherKey Array's
			int keyLength = 0;
			int dataSwap;
			int b;
			int asciiVal = 0;
			char asciiChar;
			keyLength = thisKey.length();

			for (int a = 0; a < 255; a++) {
				// take the key character at the selected position
				asciiChar = thisKey.charAt(a % keyLength);
				asciiVal = (int) asciiChar;
				cipherKeyArray[a] = asciiVal;
				cipherBox[a] = a;
			}
			b = 0;
			for (int a = 0; a < 255; a++) {
				b = (b + cipherBox[a] + cipherKeyArray[a]) % 255;
				dataSwap = cipherBox[a];
				cipherBox[a] = cipherBox[b];
				cipherBox[b] = dataSwap;
			}
		}
	}
}