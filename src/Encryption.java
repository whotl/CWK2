import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	public static String encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(s.getBytes());
			return new BigInteger(md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
	}
}
//import javax.crypto.Cipher;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Encryption {
//	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
//	public static void main(String[] args) throws Exception {
//		//生成公钥和私钥
//		generateKeyPair();
//		//加密字符串
//		String message = "df723820";
//		System.out.println("随机生成的公钥为:" + keyMap.get(0));
//		System.out.println("随机生成的私钥为:" + keyMap.get(1));
//		String messageEn = encrypt(message,keyMap.get(0));
//		System.out.println(message + "\t加密后的字符串为:" + messageEn);
//		String messageDe = decrypt(messageEn,keyMap.get(1));
//		System.out.println("还原后的字符串为:" + messageDe);
//	}
//	
//	public static void generateKeyPair() throws NoSuchAlgorithmException {  
//		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");  
//		generator.initialize(1024, new SecureRandom());  
//		KeyPair keyPair = generator.generateKeyPair();  
//		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//		String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));  
//		String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));  
//		keyMap.put(0,publicKeyString);  //0表示公钥
//		keyMap.put(1,privateKeyString);  //1表示私钥
//	}
//	
//	public static String encrypt(String str, String publicKey) throws Exception{
//		byte[] decoded = Base64.getDecoder().decode(publicKey);
//		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//		String outStr = new String(Base64.getEncoder().encode(cipher.doFinal(str.getBytes("UTF-8"))));
//		return outStr;
//	}
//	
//	public static String decrypt(String str, String privateKey) throws Exception{
//		byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
//		byte[] decoded = Base64.getDecoder().decode(privateKey);  
//        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, priKey);
//		String outStr = new String(cipher.doFinal(inputByte));
//		return outStr;
//	}
//}