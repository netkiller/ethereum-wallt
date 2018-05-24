package cn.netkiller.ethereum.mnemonic;

import java.io.IOException;
import java.util.List;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException.MnemonicLengthException;
import org.bitcoinj.wallet.UnreadableWalletException;

public class Test1 {
	public static void main(String[] args) throws UnreadableWalletException, IOException, MnemonicLengthException {
		// TODO Auto-generated method stub
		ECKey key = new ECKey();
		MnemonicCode mc = new MnemonicCode();
		final List<String> split = mc.toMnemonic(key.getSecretBytes());
		System.out.println(split);
	}
}
