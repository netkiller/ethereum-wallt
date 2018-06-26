package cn.netkiller.wallet.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import cn.netkiller.wallet.domain.UserToken;
import cn.netkiller.wallet.domain.UserToken.UserTokenPrimaryKey;
import cn.netkiller.wallet.ethereum.Ethereum;
import cn.netkiller.wallet.ethereum.Token;
import cn.netkiller.wallet.pojo.TokenResponse;
import cn.netkiller.wallet.repository.UserTokenRepository;
import cn.netkiller.wallet.service.EthereumWallet;

@Service
public class EthereumWalletImpl implements EthereumWallet {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${ethereum.url.infura}")
	private String url;

	@Autowired
	private Web3j web3j;
	// private Ethereum ethereum;

	@Autowired
	UserTokenRepository userTokenRepository;

	public EthereumWalletImpl() {
		// TODO Auto-generated constructor stub

		// this.ethereum = new Ethereum(this.web3j);
	}

	public String getUrl() {
		return this.url;
	}

	public TokenResponse getToken(String contractAddress) {
		Token token = new Token(web3j);
		token.setContractAddress(contractAddress);
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setName(token.getName());
		tokenResponse.setSymbol(token.getSymbol());
		tokenResponse.setDecimals(token.getDecimals());
		tokenResponse.setContractAddress(contractAddress);
		return tokenResponse;
	}

	public void addToken(String address, String contractAddress) {

		UserTokenPrimaryKey primaryKey = new UserTokenPrimaryKey(address, contractAddress);

		logger.info("UserTokenPrimaryKey: " + primaryKey.toString());
		logger.info(userTokenRepository.getByAddress(address).toString());

		if (userTokenRepository.findOneByPrimaryKey(primaryKey) == null) {

			TokenResponse tokenResponse = this.getToken(contractAddress);

			UserToken userToken = new UserToken();

			userToken.setPrimaryKey(primaryKey);
			userToken.setName(tokenResponse.getName());
			userToken.setSymbol(tokenResponse.getSymbol());
			userToken.setDecimals(tokenResponse.getDecimals());
			userTokenRepository.save(userToken);

			logger.info("Add token to table: " + userToken.toString());

		}
	}
}
