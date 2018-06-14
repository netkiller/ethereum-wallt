package cn.netkiller.example.test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String i = Integer.valueOf("0x57c457",16).toString();
		// System.out.println(i);

		int decimal = 6;

		BigInteger amount = BigInteger.valueOf(1000000000000000L);

		System.out.println(amount);

		String tmp = amount.toString();

		if (tmp.length() < decimal) {
			tmp = String.format("%0" + decimal + "d", amount);
		}

		String number = new StringBuffer(tmp).insert(tmp.length() - decimal, ".").toString();
		BigDecimal balance = new BigDecimal(number);

		System.out.println(balance);

		BigDecimal balance1 = new BigDecimal("1234");
		BigDecimal value = balance1.divide(BigDecimal.TEN.pow(decimal));
		// BigDecimal value = balance1.divide(BigDecimal.valueOf(1000L));
		System.out.println(BigDecimal.TEN.pow(decimal));
		System.out.println(value);
	}

}
