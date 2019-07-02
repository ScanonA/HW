package iteration1.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// Description:
// Class used for de-crypting, encrypting and salting passwords

public class PBKDF2 {
	public static byte[] encrypt(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(password, salt, 1000, 128);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		SecretKey key = skf.generateSecret(spec);
		byte[] result = key.getEncoded();
		
		return result;
	}
	
	public static boolean decrypt(byte[] hash, char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] result = encrypt(password, salt);
		return Arrays.equals(result, hash);
	}
	
	public static byte[] getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
}
