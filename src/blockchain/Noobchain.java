package blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class Noobchain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions. 
	public static int difficulty = 3;
	public static Wallet walletA;
	public static Wallet walletB;
	public static float minimumTransaction = 0.1f;
	public static Transaction genesisTransaction;
	
	public static void main (String[]args){
		
		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//Create the wallets 
		walletA = new Wallet();
		walletB = new Wallet();
		
		//Test public and private keys 
		System.out.println("Public and private keys");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());
		
	}
}
