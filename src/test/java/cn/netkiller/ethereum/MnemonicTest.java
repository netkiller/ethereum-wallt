package cn.netkiller.ethereum;

import cn.netkiller.wallet.ethereum.Mnemonic;

public class MnemonicTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mnemonic mnemonic = new Mnemonic();
		String code = mnemonic.getMnemonic();
		System.out.println(code);
		
		Mnemonic mnemonic1 = new Mnemonic("chen");
		String code1 = mnemonic1.getMnemonic();
		System.out.println(code1);
	}

}
