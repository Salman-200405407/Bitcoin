import qa.edu.qu.bean.MyKeyPair;
import qa.edu.qu.util.CryptographyUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		MyKeyPair keypair = CryptographyUtils.getInstance().getKeyPair();
//		
//		String msg = "Hi I'M Salman!";
//		String cypherText = CryptographyUtils.getInstance().encryptText(msg, keypair.getPrivateKey());
//		String plainText = CryptographyUtils.getInstance().decryptText(cypherText, keypair.getPublicKey());
//		System.out.println("msg is: "+ msg);
//		System.out.println("cypher text: "+cypherText);
//		System.out.println("plain text: "+plainText);

//		org.apache.commons.codec.digest.DigestUtils.sha256
		
	}
}
