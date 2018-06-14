package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

public class TestToken {

	public TestToken() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Transaction.DEFAULT_GAS);
		System.out.println(Contract.GAS_LIMIT);

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

			BigDecimal val = token.formatBalance(tokenBalance, decimal);
			System.out.println("格式化后：" + val);

			String transactionHash = token.sendTransaction("0xCdF0253d8362d6c3334c8F28A6BFd74c90d03d92", BigInteger.valueOf(10));
			System.out.println("代币转账：" + transactionHash);
			TransactionReceipt transactionReceipt = token.getTransactionReceipt("0xece52bdbc6d4fa0c8eba7578a7c6e537883265199fa07ef8e5b1038e4bcdefb9");
			System.out.println("转账状态：" + transactionReceipt.toString());

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
