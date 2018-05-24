package cn.netkiller.wallet.ethereum;

import java.security.SecureRandom;
import java.util.List;

import org.bitcoinj.wallet.DeterministicSeed;

public class Mnemonic {
	private String passphrase;

	public Mnemonic() {
		this.passphrase = "";

	}

	public Mnemonic(String passphrase) {
		if (passphrase != null) {
			this.passphrase = passphrase;
		}
	}

	public String getMnemonic() {
		SecureRandom secureRandom = new SecureRandom();
		long creationTimeSeconds = System.currentTimeMillis() / 1000;
		DeterministicSeed deterministicSeed = new DeterministicSeed(secureRandom, 128, this.passphrase, creationTimeSeconds);
		List<String> mnemonicCode = deterministicSeed.getMnemonicCode();
		return (String.join(" ", mnemonicCode));
	}
}
