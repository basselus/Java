package blockchain;

import java.security.PublicKey;

public class TransactionOutput {

	public String id;
	public PublicKey reciepient; // also known as the new owner of these coins.
	public float value; // the amount of coins they own
	public String parentTransactionId; // the id of the transaction this output
										// was created in

	/**
	 * @param id
	 * @param reciepient
	 * @param value
	 * @param parentTransactionId
	 */
	public TransactionOutput(String id, PublicKey reciepient, float value,
			String parentTransactionId) {
		super();
		this.id = StringUtil.applySha256(StringUtil
				.getStringFromKey(reciepient)
				+ Float.toString(value)
				+ parentTransactionId);
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
	}
	
	
	
	//Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
}

}
