package qa.edu.qu.bean;

import java.io.Serializable;

public class Input implements TransactionObject, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2294996879430345467L;

	/*
	 * the hash of the previous transaction in which the amount used in this input
	 * was an output. prevTxHash is null if there no previous transaction (e.g. if
	 * it is a coinbase transaction for creating new coins)
	 */
	private String prevTxHash;

	/*
	 * the index of the corresponding output in the previous transaction. This value
	 * is not used and will take the default value of zero in a coinbase
	 * transaction.
	 */
	private Integer outputIndex;

	/*
	 * a signature over the current transaction with the secret key corresponding to
	 * the public key listed in the corresponding output. If the previous
	 * transaction is null (i.e., a coinbase transaction), the signature is valid if
	 * it is by the secrete key of the coin creator (you – you can create a fixed
	 * pair of keys for you that you can use for coinbase transactions).
	 */
	private String signature;

	public Input() {
		super();
	}

	public Input(String prevTxHash, Integer outputIndex, String signature) {
		super();
		this.prevTxHash = prevTxHash;
		this.outputIndex = outputIndex;
		this.signature = signature;
	}

	public String getPrevTxHash() {
		return prevTxHash;
	}

	public void setPrevTxHash(String prevTxHash) {
		this.prevTxHash = prevTxHash;
	}

	public Integer getOutputIndex() {
		return outputIndex;
	}

	public void setOutputIndex(Integer outputIndex) {
		this.outputIndex = outputIndex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((outputIndex == null) ? 0 : outputIndex.hashCode());
		result = prime * result + ((prevTxHash == null) ? 0 : prevTxHash.hashCode());
		result = prime * result + ((signature == null) ? 0 : signature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Input other = (Input) obj;
		if (outputIndex == null) {
			if (other.outputIndex != null)
				return false;
		} else if (!outputIndex.equals(other.outputIndex))
			return false;
		if (prevTxHash == null) {
			if (other.prevTxHash != null)
				return false;
		} else if (!prevTxHash.equals(other.prevTxHash))
			return false;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		return true;
	}

	public String getValueString() {
		return getPrevTxHash() != null ? getPrevTxHash():""+
				getValueString() != null ? getValueString():""+
						getOutputIndex() != null?getOutputIndex().toString():"";
	}

}
