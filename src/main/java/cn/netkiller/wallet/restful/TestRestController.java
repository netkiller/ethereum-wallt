package cn.netkiller.wallet.restful;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

@RestController
public class TestRestController {
	private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

	@Autowired
	private Web3j web3j;

	public TestRestController() {
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/version")
	public String version() throws IOException {
		Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		logger.info(clientVersion);
		return clientVersion;
	}

}
