package projetoCriptografia;

import java.io.Serializable;

public class Criptografia implements Serializable {

	private static final long serialVersionUID = -2870707874483151194L;

	private String key = "thepower";
	private String value = "AIzaSyBTOqMtlFD7KUsK7p9ObGlMBOJuQRyMxuM";
	
	public String encryptedValue = encrypt(value);
	
	public Criptografia() {
	}

	/**
	 * Encripta uma {@link String}
	 * 
	 * @param value
	 *            {@link String}
	 */
	public Criptografia(String value) {
		encrypt(value);
	}

	/**
	 * Encrypta uma {@link String}
	 * 
	 * @param value
	 *            {@link String}
	 * @return {@link String}
	 */
	public String encrypt(String value) {

		value = value.trim();
		String dest = "";
		int srcAsc;
		int keyLen = key.length();
		int keyPos = 0;
		int range = 1; // 256
		int offset = (int) Math.random() * range;

		try {
			dest = String.format("%02x", offset);

			for (int srcPos = 0; srcPos < value.length(); srcPos++) {
				srcAsc = ((int) value.charAt(srcPos) + offset) % 255;
				if (keyPos < keyLen) {
					keyPos++;
				} else {
					keyPos = 1;
				}

				srcAsc ^= (int) key.charAt(keyPos - 1);
				dest += String.format("%02x", srcAsc);
				offset = srcAsc;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.value = dest.toUpperCase();
		return this.value;
	}

	/**
	 * Decrypta uma {@link String}
	 * 
	 * @param value
	 *            {@link String}
	 * @return {@link String}
	 */
	public String decrypt(String value) {

		if ((value.trim() == "") || (null == value)) {
			return null;
		}

		this.value = value.trim();
		String dest = "";
		int keyLen = key.length();
		int keyPos = -1;
		int offset = Integer.parseInt(this.value.substring(0, 2), 16);
		int srcPos = 02;
		int srcAsc = 0;
		int tempSrcAsc = 0;

		do {
			srcAsc = Integer.parseInt(this.value.substring(srcPos, srcPos + 2),
					16);
			if (keyPos < keyLen - 1) {
				keyPos++;
			} else {
				keyPos = 0;
			}

			tempSrcAsc = srcAsc ^ (int) key.charAt(keyPos);
			if (tempSrcAsc < offset) {
				tempSrcAsc = 255 + tempSrcAsc - offset;
			} else {
				tempSrcAsc -= offset;
			}

			dest = dest + (char) tempSrcAsc;
			offset = srcAsc;
			srcPos = srcPos + 2;

		} while (srcPos < this.value.length());

		this.value = dest;

		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}