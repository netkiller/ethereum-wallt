package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
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

	public Token() {
	}

	public Token(Web3j web3j) {
		web3 = web3j;
	}

	public Token(String url) {
		web3 = Web3j.build(new HttpService(url));
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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

	public BigDecimal formatBalance(BigInteger balance, int decimal) {
		BigDecimal value = new BigDecimal(balance);
		value = value.divide(BigDecimal.TEN.pow(decimal));
		return value;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Token token = new Token();
		// BigDecimal val = token.formatBalance(BigInteger.valueOf(1000200), 4);
		// System.out.println(val);
	}

}
