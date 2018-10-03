package qa.edu.qu.bean;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author salmanr
 * This class Represent public and private key pairs. 
 *
 */
public class MyKeyPair {

	private KeyPair keyPair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public MyKeyPair(KeyPairGenerator keyGen) {
		super();
		keyPair = keyGen.genKeyPair();
		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	
}
