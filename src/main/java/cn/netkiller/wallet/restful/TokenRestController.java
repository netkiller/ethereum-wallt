package cn.netkiller.wallet.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.wallet.pojo.TokenResponse;
import cn.netkiller.wallet.service.EthereumWallet;

@RestController
public class TokenRestController {

	@Autowired
	EthereumWallet ethereumWallet;

	public TokenRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/token/{contractAddress}")
	public TokenResponse refresh(@PathVariable String contractAddress) {

		return ethereumWallet.getToken(contractAddress);
	}
}
