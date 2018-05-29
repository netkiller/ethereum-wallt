package cn.netkiller.wallet.ethereum;

import java.io.IOException;
import java.math.BigInteger;

import org.bitcoinj.wallet.UnreadableWalletException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

public class Ethereum {
	public Ethereum() {

		// // TODO Auto-generated method stub
		// try {
		// Web3j web3 = Web3j.build(new
		// HttpService("https://rinkeby.infura.io/CsS9shwaAab0z7B4LP2d"));
		// Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		// System.out.println(web3ClientVersion.getWeb3ClientVersion());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) throws UnreadableWalletException, IOException {
		// TODO Auto-generated method stub
		Mnemonic mnemonic = new Mnemonic();
		String code = mnemonic.getMnemonic();
		System.out.println(code);

		HdWallet hdWallet = new HdWallet(code, "netkiller");
		BigInteger privateKeyBigInteger = hdWallet.generate();
		String publicKey = hdWallet.getPublicKey();
		String privateKey = hdWallet.getPrivateKey();
		System.out.println(publicKey);
		System.out.println(privateKey);

		Account account = new Account();
		String address = account.getAddressFromPrivateKey(privateKey);
		System.out.println(address);
		account.exportKeystore(privateKeyBigInteger, "12345678", "/tmp");
		// System.out.println(privateKey);
	}
}
