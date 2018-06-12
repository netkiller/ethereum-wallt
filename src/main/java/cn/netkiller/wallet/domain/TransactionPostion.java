package cn.netkiller.wallet.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TransactionPostion {

	@Id
	private String address;
	private int postion;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostion() {
		return postion;
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	@Override
	public String toString() {
		return "TransactionsPostion [address=" + address + ", postion=" + postion + "]";
	}

}
