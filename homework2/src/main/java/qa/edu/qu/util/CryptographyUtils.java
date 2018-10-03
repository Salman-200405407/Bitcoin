package qa.edu.qu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import qa.edu.qu.bean.MyKeyPair;

public class CryptographyUtils {

	private static final CryptographyUtils utils = new CryptographyUtils();
	private KeyPairGenerator keyGen;
	private Cipher cipher;

	private CryptographyUtils() {
		super();
		try {
			this.keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			this.cipher = Cipher.getInstance("RSA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CryptographyUtils getInstance() {
		return utils;
	}

	/**
	 * This method will return {@link MyKeyPair}
	 * which will contain public and private key pairs.
	 * @return
	 */
	public MyKeyPair getKeyPair() {
		return new MyKeyPair(keyGen);
	}


	/**
	 * 
	 * @param object
	 * @param privateKey
	 * @return 
	 * This method will return byte array of encrypted give object data
	 * 
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public byte[] encryptObject(Object object, PrivateKey privateKey) throws IOException, GeneralSecurityException {
		byte[] input = getHash(object);
		this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return this.cipher.doFinal(input);
	}
	
	/**
	 * @param input
	 * @param publicKey
	 * @return
	 * This method will return the decrypted data of the given encrypted data.
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public byte[] decryptObject(byte[] input, PublicKey publicKey) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return this.cipher.doFinal(input);
	}

	/**
	 * @param object
	 * @return
	 * This method will return SHA256 hash data for the given object
	 * @throws IOException
	 */
	public byte[] getHash(Object object) throws IOException {
		return org.apache.commons.codec.digest.DigestUtils.sha256(serializeObject(object));
	}

	/**
	 * @param object
	 * @return
	 * This method will serialise the given object and return the byte array
	 * @throws IOException
	 */
	private byte[] serializeObject(Object object) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] yourBytes = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.flush();
			yourBytes = bos.toByteArray();
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
		return yourBytes;
	}

	
// NOT USED 	
//	public String encryptText(String msg, PrivateKey privateKey) throws Exception {
//	this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//	return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
//}
//
//public String decryptText(String msg, PublicKey publicKey) throws Exception {
//	this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
//	return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
//}
	
}
