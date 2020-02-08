package blockchain;
import java.util.ArrayList;
import java.util.Date;
import java.security.MessageDigest;

import org.bouncycastle.*;

public class Block {

	public String hash;
	public String previousHash;
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();//our data will be a simple message.
	private String data; 	// the data will be a simple message
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int nonce;


	//Block Constructor.  
	public Block(String previousHash) {
		super();
		this.previousHash = previousHash;
		//this.data = data;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();//Making sure we do this after we set the other values.

	}

	public String calculateHash(){

		String calculatedhash = StringUtil.applySha256(

				previousHash +
				Long.toString(timeStamp)+
				Integer.toString(nonce)+
				merkleRoot // we replace data by merklRoot

				);
		return calculatedhash;

	}

	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty){
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0');//Create a string with difficulty * "0"
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash=calculateHash();
		}
		System.out.println("Block mined!!! : " + hash);
	}
	
	//Add transactions to this block

	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
}
	
//Notice we also updated our Block constructor as we no longer need pass in string data and included the merkle root in the calculate hash method.

//Our addTransaction boolean method will add the transactions and will only return true if the transaction has been successfully added.
	

}
