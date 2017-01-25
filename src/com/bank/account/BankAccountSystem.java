package com.bank.account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankAccountSystem {

	public static final String FILENAME = "log.html";

	public static final String AMOUNT = "amount";

	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to Bank Account System !!!");

		try {
			writeTofile("Amount");
			while (true) {
				System.out.println("Please enter in a command (Deposit, Withdraw, Balance, Exit)");

				String str = br.readLine();
				if (str != null) {
					String lcString = str.toLowerCase();
					if (OptionsEnum.searchValue(lcString)) {
						switch (lcString) {
						case "deposit":
							depositAmount(br);
							break;
						case "withdraw":
							withdrawAmount(br);
							break;
						case "balance":
							Double balance = getCurrentBalance();
							System.out.println("The current balance is: $" + balance);
							break;
						case "exit":
							br.close();
							System.out.println("Exiting  Bank Account System !!!");
							System.exit(0);
							break;
						}

					} else {
						System.out.println("Invalid Command");
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Boolean depositAmount(BufferedReader br) throws IOException {
		Boolean isDeposited = false;

		String amountStr = null;
		// do validation
		while (!isValidDataForInput(amountStr)) {
			System.out.println("Please enter an amount to deposit in the format (x.xx):");
			amountStr = br.readLine();
		}
		Double amount = Double.parseDouble(amountStr);
		// add the amount to the file
		writeTofile(amount.toString());

		isDeposited = true;
		return isDeposited;

	}

	public static Boolean withdrawAmount(BufferedReader br) throws IOException {
		Boolean isWithdrawn = false;
		String amountStr = null;
		// do validation
		while (!isValidDataForInput(amountStr)) {
			System.out.println("Please enter an amount to deposit in the format (x.xx):");
			amountStr = br.readLine();
		}
		Double amount = Double.parseDouble(amountStr);
		// add the negative amount to the file
		writeTofile("-" + amount.toString());
		isWithdrawn = true;
		return isWithdrawn;

	}

	public static Double getCurrentBalance() throws IOException {

		// Read file and get the balance
		Double amount = 0.0;
		try {
			File file = new File(FILENAME);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.contains("<p>")) {
					str = str.replaceAll("<p>", "");
					str = str.replaceAll("</p>", "");
					amount += Double.parseDouble(str);
				}
			}
			in.close();
		} catch (IOException e) {
		}

		return amount;

	}

	public static Boolean isValidDataForInput(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("(^[0-9]+(\\.[0-9]{1,2})?$)");
			Matcher m = p.matcher(str);
			if (m.find()) {
				return true;
			}
		}
		return false;

	}

	public static void writeTofile(String value) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			File file = new File(FILENAME);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			if (value.equalsIgnoreCase(AMOUNT)) {
				value = "<h3>" + value + "</h3>";
			} else {
				value = "<p>" + value + "<p>";
			}

			if (file.length() != 0) {
				String newLine = System.getProperty("line.separator");
				value = newLine + value;
			}

			bw.write(value.toString());
			// bw.newLine();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}

	}

}
