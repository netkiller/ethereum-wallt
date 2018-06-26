package cn.netkiller.wallet.service;

import cn.netkiller.wallet.pojo.TokenResponse;

public interface EthereumWallet {

	String getUrl();

	TokenResponse getToken(String contractAddress);
	
	public void addToken(String address, String contractAddress);

}
