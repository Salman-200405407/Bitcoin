package qa.edu.qu.bean;

import java.io.Serializable;
import java.security.PublicKey;

public class Output implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2558376210740896723L;
	private Double value; // a value representing the number of coins
	private PublicKey publicKey;// the public key of the entity being paid.

	public Output() {
		super();
	}

	public Output(Double value, PublicKey publicKey) {
		super();
		this.value = value;
		this.publicKey = publicKey;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Output other = (Output) obj;
		if (publicKey == null) {
			if (other.publicKey != null)
				return false;
		} else if (!publicKey.equals(other.publicKey))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
