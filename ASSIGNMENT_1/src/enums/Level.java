package enums;

import java.util.Arrays;
import java.util.Objects;

public enum Level {
	LEVEL_1(1), LEVEL_2(2), LEVEL_3(3), LEVEL_4(4),;

	private final int value;

	private Level(int value) {
		this.value = value;

	}

	/**
	 * convert String to Level
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Level convertStringToLevel(String str) throws Exception {
		var obj = Arrays.stream(Level.values()).filter(item -> item.getValue() == Integer.parseInt(str)).findFirst()
				.orElse(null);
		if (Objects.isNull(obj)) {
			throw new Exception("Loi converted Enum");
		} else {
			return obj;
		}
	}

	public int getValue() {
		return value;
	}
}
