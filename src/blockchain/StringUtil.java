package blockchain;

import java.security.Key;
import java.security.MessageDigest; //to generate a digital signature and access to the SHA256 algorithm
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;

//Create a method that takes a string and applies SHA256 algorithm to it, and returns the generated signature as a string.

public class StringUtil {

	// Applies Sha256 to a string and returns the result.

	public static String applySha256(String input) {

		try {

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain
															// hash as
															// hexadecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	public static int getStringFromKey(PublicKey sender) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Applies ECDSA Signature and returns the result ( as bytes ).
	// applyECDSASig takes in the senders private key and string input, signs it
	// and returns an array of bytes.

	// Verifies a String signature
	// verifyECDSASig takes in the signature, public key and string data and
	// returns true or false if the signature is valid
	public static boolean verifyECDSASig(PublicKey publicKey, String data,
			byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// getStringFromKey returns encoded string from any key.
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {

		Signature dsa;

		byte[] output = new byte[0];

		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException();
		}

		return output;

	}

	// Tacks in array of transactions and returns a merkle root.
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		for (Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.transactionId);
		}
		ArrayList<String> treeLayer = previousTreeLayer;
		while (count > 1) {
			treeLayer = new ArrayList<String>();
			for (int i = 1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(applySha256(previousTreeLayer.get(i - 1)
						+ previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}

}
