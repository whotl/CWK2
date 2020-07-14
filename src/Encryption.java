import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encryption class can encrypt a string with SHA encryption algorithm
 *
 * @author created by Haowen Miao
 */
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