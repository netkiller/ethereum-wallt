package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

public class Ethereum {
	public static Web3j web3;

	public Ethereum() {
		// TODO Auto-generated constructor stub
		web3 = Web3j.build(new HttpService("https://localhost:8545"));
	}

	public Ethereum(String url) {
		web3 = Web3j.build(new HttpService(url));
	}

	public Ethereum(Web3j web3j) {
		web3 = web3j;
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

	public String send(String privateKey, String to, BigInteger amount) throws IOException, InterruptedException, ExecutionException {

		Credentials credentials = Credentials.create(privateKey);
		String from = credentials.getAddress();

		BigInteger value = Convert.toWei(amount.toString(), Convert.Unit.ETHER).toBigInteger();

		EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		Transaction transaction = Transaction.createEtherTransaction(from, nonce, this.getGasPrice(), Transaction.DEFAULT_GAS, to, value);
		BigInteger gasLimit = this.getEstimateGas(transaction);

		RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, this.getGasPrice(), gasLimit, to, value);

		// 签名Transaction，这里要对交易做签名
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);

		// 发送交易
		EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
		String transactionHash = ethSendTransaction.getTransactionHash();

		System.out.println(transactionHash);
		return transactionHash;
	}

	// private static void decodeMessage(String signedData) {
	//
	// try {
	// System.out.println(signedData);
	// System.out.println("解密 start " + System.currentTimeMillis());
	// RlpList rlpList = RlpDecoder.decode(Numeric.hexStringToByteArray(signedData));
	// List<RlpType> values = ((RlpList) rlpList.getValues().get(0)).getValues();
	// BigInteger nonce = Numeric.toBigInt(((RlpString) values.get(0)).getBytes());
	// BigInteger gasPrice = Numeric.toBigInt(((RlpString) values.get(1)).getBytes());
	// BigInteger gasLimit = Numeric.toBigInt(((RlpString) values.get(2)).getBytes());
	// String to = Numeric.toHexString(((RlpString) values.get(3)).getBytes());
	// BigInteger value = Numeric.toBigInt(((RlpString) values.get(4)).getBytes());
	// String data = Numeric.toHexString(((RlpString) values.get(5)).getBytes());
	// RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
	// RlpString v = (RlpString) values.get(6);
	// RlpString r = (RlpString) values.get(7);
	// RlpString s = (RlpString) values.get(8);
	// Sign.SignatureData signatureData = new Sign.SignatureData(v.getBytes()[0], r.getBytes(), s.getBytes());
	// BigInteger pubKey = Sign.signedMessageToKey(TransactionEncoder.encode(rawTransaction), signatureData);
	//// System.out.println("publicKey " + pubKey.toString(16));
	//// String address = Numeric.prependHexPrefix(Keys.getAddress(pubKey));
	//// System.out.println("address " + address);
	//// System.out.println("解密 end " + System.currentTimeMillis());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		// Ethereum eth = new Ethereum();
		// EthTransaction transaction = web3.ethGetTransactionByHash("0x7f5d9d1b6babea423f1647c33c95c6233eeb7d9cf8fcd8e5a170ad4ff6bc54fd").sendAsync().get();
		// System.out.println(transaction.getResult().toString());

		// TransactionReceipt receipt = eth.getTransactionReceipt("0x7f5d9d1b6babea423f1647c33c95c6233eeb7d9cf8fcd8e5a170ad4ff6bc54fd");
		// System.out.println(receipt.getBlockNumber());

	}

}
