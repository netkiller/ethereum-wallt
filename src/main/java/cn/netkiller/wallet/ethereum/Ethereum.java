package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

public class Ethereum {
	public static Web3j web3;

	public Ethereum() {
		// TODO Auto-generated constructor stub
		web3 = Web3j.build(new HttpService("https://ropsten.infura.io/CsS9shwaAab0z7B4LP2d"));
	}

	public BigInteger getGasPrice() throws IOException {
		BigInteger gasPrice = BigInteger.ZERO;
		EthGasPrice ethGasPrice = web3.ethGasPrice().send();
		gasPrice = ethGasPrice.getGasPrice();
		return gasPrice;
	}

	public BigInteger getEstimateGas(Transaction transaction) {
		BigInteger gasLimit = BigInteger.ZERO;
		try {
			EthEstimateGas ethEstimateGas = web3.ethEstimateGas(transaction).send();
			gasLimit = ethEstimateGas.getAmountUsed();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gasLimit;
	}

	public TransactionReceipt getTransactionReceipt(String transactionHash) throws InterruptedException, ExecutionException {
		EthGetTransactionReceipt ethGetTransactionReceipt = web3.ethGetTransactionReceipt(transactionHash).sendAsync().get();
		return ethGetTransactionReceipt.getResult();
	}

	public BigInteger getBalance(String account) throws IOException {

		EthGetBalance ethGetBalance = web3.ethGetBalance(account, DefaultBlockParameterName.LATEST).send();
		BigInteger balance = ethGetBalance.getBalance();
		return balance;

	}

	public String sendTransaction(String privateKey, String toAddress, Double value) throws InterruptedException, IOException, TransactionException, Exception {

		Credentials credentials = Credentials.create(privateKey);

		TransactionReceipt transactionReceipt = Transfer.sendFunds(web3, credentials, toAddress, BigDecimal.valueOf(value), Convert.Unit.ETHER).send();

		return transactionReceipt.getTransactionHash();
	}

	public static void main(String[] args) {
		Ethereum eth = new Ethereum();
		// TODO Auto-generated method stub

		try {
			TransactionReceipt receipt = eth.getTransactionReceipt("0x7f5d9d1b6babea423f1647c33c95c6233eeb7d9cf8fcd8e5a170ad4ff6bc54fd");
			System.out.println(receipt.getBlockNumber());

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
