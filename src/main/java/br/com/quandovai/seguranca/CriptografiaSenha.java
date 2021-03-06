package br.com.quandovai.seguranca;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CriptografiaSenha {

	public static String RANDOM_KEY = gerarChaveAleatoria();
	private static String algorithm;

	public static boolean autenticar(String senhaTentativa, byte[] encryptedPassword, byte[] salt) {
		// Encrypt he clear-text password using the same salt that was used to
		// encrypt the original password
		byte[] encryptedAttemptedPassword = pegaSenhaCriptografada(senhaTentativa, salt);

		// Authentication succeeds if encrypted password that the user entered
		// is equal to the stored hash
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	}

	private static String gerarChaveAleatoria() {
		// VERY important to use SecureRandom instead of just Random
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
		byte[] salt = new byte[4];
		random.nextBytes(salt);

		return Base64.getEncoder().encodeToString(salt);
	}

	public static String convertida(byte[] senha, byte[] salt) {
		byte[] hash = pegaSenhaCriptografada(new String(senha).intern(), salt);
		return new String(hash).intern();
	}

	public static byte[] pegaSenhaCriptografada(String senha, byte[] salt) {
		algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes, so that's what makes sense here
		int derivedKeyLength = 160;
		// Pick an iteration count that works for you. The NIST recommends at
		// least 1,000 iterations:
		// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
		// iOS 4.x reportedly uses 10,000:
		// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
		int iterations = 10_000;

		try {
			KeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, iterations, derivedKeyLength);

			SecretKeyFactory f;
			f = SecretKeyFactory.getInstance(algorithm);
			return f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] gerarSalt() {
		// VERY important to use SecureRandom instead of just Random
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
		byte[] salt = new byte[8];
		random.nextBytes(salt);

		return salt;
	}
}
