package cn.netkiller.wallet.ethereum;

import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.tx.Contract;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Transaction.DEFAULT_GAS);
		System.out.println(Contract.GAS_LIMIT);

	}

}
