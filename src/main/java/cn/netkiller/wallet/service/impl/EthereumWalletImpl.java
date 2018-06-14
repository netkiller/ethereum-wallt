package cn.netkiller.wallet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.netkiller.wallet.ethereum.Ethereum;
import cn.netkiller.wallet.ethereum.Token;
import cn.netkiller.wallet.pojo.TokenResponse;
import cn.netkiller.wallet.service.EthereumWallet;

@Service
public class EthereumWalletImpl implements EthereumWallet {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${ethereum.url.infura}")
	private String url;
	private Ethereum ethereum;

	public EthereumWalletImpl() {
		// TODO Auto-generated constructor stub

		this.ethereum = new Ethereum();
	}

	public String getUrl() {
		return this.url;
	}

	public TokenResponse getToken(String contractAddress) {
		Token token = new Token(this.url);
		token.setContractAddress(contractAddress);
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setName(token.getName());
		tokenResponse.setSymbol(token.getSymbol());
		tokenResponse.setDecimals(token.getDecimals());
		tokenResponse.setContractAddress(contractAddress);
		return tokenResponse;
	}

}
