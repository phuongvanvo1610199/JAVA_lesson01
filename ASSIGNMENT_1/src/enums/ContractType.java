package enums;

/*
    ContractType
 */
public enum ContractType {
	/*
	 * ContractType 0 = OFFICIAL || 1 = TRIAL
	 */
	OFFICIAL_EMP(0), TRIAL_EMP(1);

	private final int value;

	private ContractType(int value) {
		this.value = value;

	}

	public int getValue() {
		return value;
	}

}
