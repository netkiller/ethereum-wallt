package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

public class Token extends Ethereum {

	private String contractAddress = null;
	private String privateKey = null;
	// private BigInteger gasLimit = BigInteger.ZERO;
	private BigInteger gasPrice = BigInteger.ZERO;

	public Token(String url) {
		web3 = Web3j.build(new HttpService(url));
	}

	public Token(String contractAddress, String privateKey) throws IOException {
		this.contractAddress = contractAddress;
		this.privateKey = privateKey;
		// this.gasLimit = Convert.toWei(BigDecimal.valueOf(21000), Convert.Unit.WEI).toBigInteger();
		this.gasPrice = this.getGasPrice();
	}

	@SuppressWarnings("rawtypes")
	public String getOwner() {
		String owner = null;
		Function function = new Function("owner", Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(null, this.contractAddress, encodedFunction);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			owner = results.get(0).getValue().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return owner;
	}

	@SuppressWarnings("rawtypes")
	public String getName() {
		String name = null;
		/*
		 * String methodName = "name"; List<Type> inputParameters = new ArrayList<>(); List<TypeReference<?>> outputParameters = new ArrayList<>();
		 * 
		 * TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() { }; outputParameters.add(typeReference);
		 * 
		 * Function function = new Function(methodName, inputParameters, outputParameters);
		 */
		Function function = new Function("name", Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(null, this.contractAddress, encodedFunction);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			System.out.println(results.size());
			if (results.size() > 0) {
				name = results.get(0).getValue().toString();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return name;
	}

	@SuppressWarnings("rawtypes")
	public String getSymbol() {
		String symbol = null;
		/*
		 * String methodName = "symbol"; List<Type> inputParameters = new ArrayList<>(); List<TypeReference<?>> outputParameters = new ArrayList<>();
		 * 
		 * TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() { }; outputParameters.add(typeReference);
		 * 
		 * Function function = new Function(methodName, inputParameters, outputParameters);
		 */
		Function function = new Function("symbol", Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(null, this.contractAddress, encodedFunction);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			if (results.size() > 0) {
				symbol = results.get(0).getValue().toString();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return symbol;
	}

	@SuppressWarnings("rawtypes")
	public int getDecimals() {
		int decimal = 0;
		/*
		 * String methodName = "decimals"; List<Type> inputParameters = new ArrayList<>(); List<TypeReference<?>> outputParameters = new ArrayList<>();
		 * 
		 * TypeReference<Uint> typeReference = new TypeReference<Uint>() { }; outputParameters.add(typeReference);
		 * 
		 * Function function = new Function(methodName, inputParameters, outputParameters);
		 */
		Function function = new Function("decimals", Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint>() {
		}));
		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(null, this.contractAddress, encodedFunction);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			if (results.size() > 0) {
				decimal = Integer.parseInt(results.get(0).getValue().toString());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return decimal;
	}

	@SuppressWarnings("rawtypes")
	public BigInteger getTotalSupply() {
		BigInteger totalSupply = BigInteger.ZERO;

		/*
		 * String methodName = "totalSupply"; List<Type> inputParameters = new ArrayList<>(); List<TypeReference<?>> outputParameters = new ArrayList<>();
		 * 
		 * TypeReference<Uint256> typeReference = new TypeReference<Uint256>() { }; outputParameters.add(typeReference);
		 * 
		 * Function function = new Function(methodName, inputParameters, outputParameters);
		 */
		Function function = new Function("totalSupply", Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));
		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(null, this.contractAddress, encodedFunction);

		EthCall ethCall;
		try {
			ethCall = web3.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
			totalSupply = (BigInteger) results.get(0).getValue();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return totalSupply;
	}

	@SuppressWarnings("rawtypes")
	public BigInteger getTokenBalance(String account) throws InterruptedException, ExecutionException, IOException {
		Function function = new Function("balanceOf", Arrays.<Type>asList(new Address(account)), Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);
		// System.out.println(encodedFunction);

		EthCall response = web3.ethCall(Transaction.createEthCallTransaction(account, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();

		// System.out.println(response.getValue());

		List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
		// System.out.println(result.size());
		// System.out.println(response.getRawResponse());
		// System.out.println((BigInteger) result.get(0).getValue());

		BigInteger balance = BigInteger.ZERO;
		if (result.size() == 1) {
			balance = (BigInteger) result.get(0).getValue();
		}
		return balance;
	}

	@SuppressWarnings("rawtypes")
	public BigInteger getAllowance(String _owner, String _spender) throws InterruptedException, ExecutionException {
		Function function = new Function("allowance", Arrays.asList(new Address(_owner), new Address(_spender)), Arrays.asList(new TypeReference<Uint256>() {
		}));
		String encodedFunction = FunctionEncoder.encode(function);

		EthCall response = web3.ethCall(Transaction.createEthCallTransaction(_owner, this.contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).sendAsync().get();
		List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
		BigInteger balance = BigInteger.ZERO;
		if (result.size() == 1) {
			balance = (BigInteger) result.get(0).getValue();
		}
		return balance;
	}

	public String sendTransaction(String toAddress, BigInteger amount) throws InterruptedException, ExecutionException, IOException {
		String txHash = null;
		Credentials credentials = Credentials.create(this.privateKey);
		String fromAddress = credentials.getAddress();

		Function function = new Function("transfer", Arrays.asList(new Address(toAddress), new Uint256(amount)), Arrays.asList(new TypeReference<Bool>() {
		}, new TypeReference<Bool>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);

		EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, this.gasPrice, Transaction.DEFAULT_GAS, this.contractAddress, encodedFunction);
		BigInteger gasLimit = this.getEstimateGas(transaction);
		// System.out.println("评估GAS：" + gasLimit);

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, this.gasPrice, gasLimit, this.contractAddress, encodedFunction);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String signedTransactionData = Numeric.toHexString(signedMessage);
		EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(signedTransactionData).sendAsync().get();
		txHash = ethSendTransaction.getTransactionHash();

		return txHash;
	}

	public String setApprove(String _spender, BigInteger _amount) throws InterruptedException, ExecutionException, IOException {
		String txHash = null;
		Credentials credentials = Credentials.create(this.privateKey);
		String fromAddress = credentials.getAddress();

		Function function = new Function("approve", Arrays.asList(new Address(_spender), new Uint256(_amount)), Arrays.asList(new TypeReference<Bool>() {
		}, new TypeReference<Bool>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);

		EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, this.gasPrice, Transaction.DEFAULT_GAS, this.contractAddress, encodedFunction);
		BigInteger gasLimit = this.getEstimateGas(transaction);

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, this.gasPrice, gasLimit, this.contractAddress, encodedFunction);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String signedTransactionData = Numeric.toHexString(signedMessage);
		EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(signedTransactionData).sendAsync().get();
		txHash = ethSendTransaction.getTransactionHash();

		return txHash;
	}

	public String sendTransactionFrom(String _from, String _to, BigInteger _amount) throws InterruptedException, ExecutionException, IOException {
		String txHash = null;
		Credentials credentials = Credentials.create(this.privateKey);
		String fromAddress = credentials.getAddress();

		Function function = new Function("transferFrom", Arrays.asList(new Address(_from), new Address(_to), new Uint256(_amount)), Arrays.asList(new TypeReference<Bool>() {
		}, new TypeReference<Bool>() {
		}));

		String encodedFunction = FunctionEncoder.encode(function);

		EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, this.gasPrice, Transaction.DEFAULT_GAS, this.contractAddress, encodedFunction);
		BigInteger gasLimit = this.getEstimateGas(transaction);

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, this.gasPrice, gasLimit, this.contractAddress, encodedFunction);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String signedTransactionData = Numeric.toHexString(signedMessage);
		EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(signedTransactionData).sendAsync().get();
		txHash = ethSendTransaction.getTransactionHash();

		return txHash;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Token token = new Token("0xb3cedc76e75fcd278c988b22963c2f35c99c10b7", "166970EC717B3ECCADABF68AC066558537228DB022FFBDE13A06790967F2BC3A");
			String owner = token.getOwner();
			System.out.println("代币创建者：" + owner);

			String name = token.getName();
			System.out.println("代币名称：" + name);

			String symbol = token.getSymbol();
			System.out.println("代币符号：" + symbol);

			int decimal = token.getDecimals();
			System.out.println("小数位数：" + decimal);

			BigInteger totalSupply = token.getTotalSupply();
			System.out.println("发行总量：" + totalSupply);

			BigInteger tokenBalance = token.getTokenBalance("0x22c57F0537414FD95b9f0f08f1E51d8b96F14029");
			System.out.println("代币余额:" + tokenBalance);

			String txhash = token.sendTransaction("0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92", BigInteger.valueOf(10));
			System.out.println("代币转账：" + txhash);

			String hash = token.setApprove("0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92", BigInteger.valueOf(100));
			System.out.println("设置授信：" + hash);

			BigInteger value = token.getAllowance("0x22c57F0537414FD95b9f0f08f1E51d8b96F14029", "0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92");
			System.out.println("查询授信：" + value);

			Token token1 = new Token("0xb3cedc76e75fcd278c988b22963c2f35c99c10b7", "8D160BFB1B98C184121D63C3F668E63CC04CEE44C31D985A6FB37195D189675E");
			System.out.println("授信转出：" + token1.sendTransactionFrom("0x22c57F0537414FD95b9f0f08f1E51d8b96F14029", "0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92", BigInteger.valueOf(20)));

			// System.out.println(token1.getAllowance("0x22c57F0537414FD95b9f0f08f1E51d8b96F14029", "0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92"));
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();

		}
	}

}
