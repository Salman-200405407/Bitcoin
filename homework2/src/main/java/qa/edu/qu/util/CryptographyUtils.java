package qa.edu.qu.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;

import qa.edu.qu.bean.Input;
import qa.edu.qu.bean.MyKeyPair;
import qa.edu.qu.bean.Output;
import qa.edu.qu.bean.Transaction;

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


	public String encryptTransaction(Transaction transaction, PrivateKey privateKey) throws IOException, GeneralSecurityException {
		String input = getHash(transaction);
		this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return Base64.encodeBase64String(cipher.doFinal(input.getBytes("UTF-8")));
	}
	

	public String decryptTransactionSignature(String msg, PublicKey publicKey) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
	}

	/**
	 * @param object
	 * @return
	 * This method will return SHA256 hash data for the given object
	 * @throws IOException
	 */
	public String getHash(Transaction transaction) throws IOException {
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(getTransactionValueString(transaction));
	}

	private String getTransactionValueString(Transaction transaction) {
		StringBuffer transactionValueString = new StringBuffer();
		if(transaction != null) {
			if(CollectionUtils.isNotEmpty(transaction.getInputs())) {
				for(Input currentInput:transaction.getInputs()) {
					transactionValueString.append(currentInput.getValueString());
				}
			}
			
			if(CollectionUtils.isNotEmpty(transaction.getOutputs())) {
				for(Output currentOutput:transaction.getOutputs()) {
					transactionValueString.append(currentOutput.getValueString());
				}
			}
			
		}
		return transactionValueString.toString();
	}

	
	// NOT USED
	
//	/**
//	 * @param object
//	 * @return
//	 * This method will serialise the given object and return the byte array
//	 * @throws IOException
//	 */
//	private byte[] serializeObject(Object object) throws IOException {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ObjectOutput out = null;
//		byte[] yourBytes = null;
//		try {
//			out = new ObjectOutputStream(bos);
//			out.writeObject(object);
//			out.flush();
//			yourBytes = bos.toByteArray();
//		} finally {
//			try {
//				bos.close();
//			} catch (IOException ex) {
//				throw ex;
//			}
//		}
//		return yourBytes;
//	}

 	
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
