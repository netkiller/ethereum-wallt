package cn.netkiller.wallet.ethereum;

import org.web3j.crypto.Credentials;

public class Account {
	private String address;
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public String getAddressFromPrivateKey(String privateKey) {
		Credentials credentials = Credentials.create(privateKey);
		this.address = credentials.getAddress();
		return this.address;
	}
}
