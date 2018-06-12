package cn.netkiller.wallet.restful;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.netkiller.wallet.domain.Token;
import cn.netkiller.wallet.domain.TransactionHistory;
import cn.netkiller.wallet.domain.TransactionPostion;
import cn.netkiller.wallet.pojo.JsonRpc;
import cn.netkiller.wallet.pojo.TokenTransaction;
import cn.netkiller.wallet.pojo.TokenTransactionResponse;
import cn.netkiller.wallet.pojo.Transaction;
import cn.netkiller.wallet.pojo.TransactionResponse;
import cn.netkiller.wallet.repository.TokenRepository;
import cn.netkiller.wallet.repository.TransactionHistoryRepository;
import cn.netkiller.wallet.repository.TransactionPostionRepository;

@RestController
public class TestRestController {
	private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;

	@Autowired
	TransactionPostionRepository transactionPostionRepository;

	@Autowired
	TokenRepository tokenRepository;

	public TestRestController() {
		// TODO Auto-generated constructor stub
		Token token = new Token();
		token.setName("Enterprise Token Ecosystem (ETE)");
		token.setSymbol("ETE");
		token.setDecimals("5");
		token.setContractAddress("0x6333050c7a025027b51a8039cbafd2584933299d");

		// tokenRepository.save(token);

	}

	@GetMapping("/transaction/refresh/{address}")
	public String refresh(@PathVariable String address) {
		this.syncTransaction(address);
		return "ok";
	}

	@GetMapping("/transaction/{address}")
	public Page<TransactionHistory> transaction(@PathVariable String address, @PageableDefault(sort = { "block_number" }) Pageable pageable) {
		Page<TransactionHistory> transactionHistory;

		transactionHistory = transactionHistoryRepository.findEthByAddress(address, pageable);

		return transactionHistory;
	}

	@GetMapping("/transaction/{address}/{symbol}")
	public Page<TransactionHistory> transactionToken(@PathVariable String address, @PathVariable String symbol, @PageableDefault(sort = { "block_number" }) Pageable pageable) {
		Page<TransactionHistory> transactionHistory;

		transactionHistory = transactionHistoryRepository.findTokenByAddressAndContactAddress(address, symbol, pageable);

		return transactionHistory;
	}

	private void syncTransaction(String address) {
		TransactionPostion transactionPostion = transactionPostionRepository.findOneByAddress(address);

		System.out.println("POSTION: " + transactionPostion);

		String startblock;
		String endblock = (this.getBlockNumber());
		if (transactionPostion == null) {
			transactionPostion = new TransactionPostion();
			startblock = "0";
		} else {
			startblock = String.valueOf(transactionPostion.getPostion());
		}

		TransactionResponse transactionsResponse = this.getTransactions(startblock, endblock, address);
		if (transactionsResponse.getStatus().equals("1")) {
			List<Transaction> transactions = transactionsResponse.getResult();
			for (Transaction transaction : transactions) {

				if (transaction.getContractAddress().equals("") && transaction.getInput().equals("0x")) {
					TransactionHistory transactionHistory = new TransactionHistory();
					transactionHistory.setBlockNumber(transaction.getBlockNumber());
					transactionHistory.setHash(transaction.getHash());
					transactionHistory.setSymbol("ETH");
					transactionHistory.setFrom(transaction.getFrom());
					transactionHistory.setTo(transaction.getTo());
					transactionHistory.setGas(transaction.getGas());
					transactionHistory.setGasPrice(transaction.getGasPrice());
					transactionHistory.setGasUsed(transaction.getGasUsed());
					transactionHistory.setIsError(transaction.getIsError());
					transactionHistory.setTimeStamp(transaction.getTimeStamp());
					transactionHistory.setValue(transaction.getValue());
					transactionHistoryRepository.save(transactionHistory);
				}
			}
			transactionPostion.setAddress(address);
			transactionPostion.setPostion(Integer.valueOf(endblock));
			transactionPostionRepository.save(transactionPostion);
		}

		TokenTransactionResponse tokenTransactionResponse = this.getTokenTransactions(startblock, endblock, address);
		if (tokenTransactionResponse.getStatus().equals("1")) {
			List<TokenTransaction> tokenTransactions = tokenTransactionResponse.getResult();
			for (TokenTransaction token : tokenTransactions) {
				TransactionHistory transactionHistory = new TransactionHistory();
				transactionHistory.setBlockNumber(token.getBlockNumber());
				transactionHistory.setHash(token.getHash());
				transactionHistory.setSymbol(token.getTokenSymbol());
				transactionHistory.setFrom(token.getFrom());
				transactionHistory.setTo(token.getTo());
				transactionHistory.setGas(token.getGas());
				transactionHistory.setGasPrice(token.getGasPrice());
				transactionHistory.setGasUsed(token.getGasUsed());
				transactionHistory.setIsError("");
				transactionHistory.setTimeStamp(token.getTimeStamp());
				transactionHistory.setValue(token.getValue());
				transactionHistory.setContractAddress(token.getContractAddress());
				transactionHistoryRepository.save(transactionHistory);
			}
		}

	}

	private TransactionResponse getTransactions(String startblock, String endblock, String address) {
		final String url = "http://api.etherscan.io/api?module={module}&action={action}&address={address}&startblock={startblock}&endblock={endblock}&sort={sort}&apikey={apikey}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("module", "account");
		params.put("action", "txlist");
		params.put("address", address);
		params.put("startblock", startblock);
		params.put("endblock", endblock);
		params.put("sort", "asc");
		params.put("apikey", "RT5JW37AKEZVSW3C91Z86IGI2FF7JDPF1N");
		RestTemplate restTemplate = new RestTemplate();
		TransactionResponse result = restTemplate.getForObject(url, TransactionResponse.class, params);
		logger.info(params.toString());
		logger.info(result.toString());
		return result;
	}

	private String getBlockNumber() {
		final String url = "https://api.etherscan.io/api?module=proxy&action=eth_blockNumber&apikey=RT5JW37AKEZVSW3C91Z86IGI2FF7JDPF1N";
		Map<String, String> params = new HashMap<String, String>();
		params.put("module", "proxy");
		params.put("action", "eth_blockNumber");
		params.put("apikey", "RT5JW37AKEZVSW3C91Z86IGI2FF7JDPF1N");
		RestTemplate restTemplate = new RestTemplate();
		JsonRpc result = restTemplate.getForObject(url, JsonRpc.class, params);

		return Integer.valueOf(result.getResult().substring(2), 16).toString();
	}

	private TokenTransactionResponse getTokenTransactions(String startblock, String endblock, String address) {

		final String url = "http://api.etherscan.io/api?module={module}&action={action}&address={address}&startblock={startblock}&endblock={endblock}&sort={sort}&apikey={apikey}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("module", "account");
		params.put("action", "tokentx");
		params.put("address", address);
		params.put("startblock", startblock);
		params.put("endblock", endblock);
		params.put("sort", "asc");
		params.put("apikey", "RT5JW37AKEZVSW3C91Z86IGI2FF7JDPF1N");
		RestTemplate restTemplate = new RestTemplate();
		TokenTransactionResponse result = restTemplate.getForObject(url, TokenTransactionResponse.class, params);
		logger.info(params.toString());
		logger.info(result.toString());
		return result;
	}
}
