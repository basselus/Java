package blockchain;

//BLOCKCHAIN :
//made up of blocks that store data. 
//Has a digital signature that chains your blocks together.
//Requires proof of work mining to validate new blocks.
//Can be check to see if data in it is valid and unchanged.


import java.util.ArrayList;
import com.google.gson.*;

public class TestBlockchain {

	
	
	//Store blockchain in an arraylist

	public static ArrayList<Block> myblockchain = new ArrayList<Block>(); 
	public static int difficulty = 5; // the number of zeros needed to mine the block

	public static void main(String[] args) {

		Block firstBlock = new Block("Bonjour je suis le premier block","0");
		System.out.println( "hash du premier block : " + firstBlock.hash);
				
		Block secondBlock = new Block("Bonjour je suis le deuxième block",firstBlock.hash);
		System.out.println( "hash du deuxième block : " + secondBlock.hash);
		
		Block thirdBlock = new Block("Bonjour je suis le troisième block ", secondBlock.hash);
		System.out.println( "hash du troisième block : " + thirdBlock.hash);
				
		//Each block now has its own digital signature based on its information and the signature of the previous block.

		//add our blocks to the blockchain ArrayList:

		myblockchain.add(new Block("Bonjour je suis le premier block","0"));
		System.out.println("Essai de minage du premier block...");
		myblockchain.get(0).mineBlock(difficulty);
		
		myblockchain.add(new Block("Bonjour je suis le deuxième block",myblockchain.get(myblockchain.size()-1).hash));
		System.out.println("Essai de minage du deuxième block...");
		myblockchain.get(1).mineBlock(difficulty);
		
		myblockchain.add(new Block("Bonjour je suis le troisième block",myblockchain.get(myblockchain.size()-1).hash));
		System.out.println("Essai de minage du troisième block...");
		myblockchain.get(2).mineBlock(difficulty);

		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(myblockchain);	
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);

	}

	// Next We verify the integrity of the blockchain with a method called isChainValid : it will loop through all the blocks
	// in the chain and compare the hashes
	// this method below makes our blockchain unattackable : any change in the block will cause this method to return FALSE

	public static boolean isChainValid(){

		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		
		//Loop through blockchain to check hashes

		for (int i = 1; i < myblockchain.size(); i++) {
			currentBlock = myblockchain.get(i);
			previousBlock = myblockchain.get(i-1);

			//compare registered hash and calculated hash

			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes are not equal");
				return false;
			}


			//compare previous hash and registered previous hash

			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes are not equal");
				return false;
			}
			
			//Check if hash is solved
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
				System.out.println("this block has not been mined yet");
				return false;
			}
			
			

		}


		return true;

	}
}




