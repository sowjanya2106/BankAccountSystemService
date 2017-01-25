package com.bank.account;

public enum OptionsEnum {
	BALANCE("balance"), DEPOSIT("deposit"), WITHDRAW("withdraw"), EXIT("exit");

	private String value;

	private OptionsEnum(String value) {

		this.value = value;
	}

	public String toString() {
		return this.value;
	}

	public static Boolean searchValue(String searchValue) {
		OptionsEnum[] enums = OptionsEnum.values();

		if (enums != null && enums.length > 0) {
			for (OptionsEnum optionsEnum : enums) {
				if (optionsEnum.value.equals(searchValue)) {
					return true;
				}
			}
		}
		return false;
	}
}
