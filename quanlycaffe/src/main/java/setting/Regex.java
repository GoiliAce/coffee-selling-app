package setting;

public class Regex {
	public static String capitalizeName(String name) {
		StringBuffer sb = new StringBuffer();
		String[] nameSplit = name.split("\\s+");
		for (int i = 0; i < nameSplit.length; i++) {
			nameSplit[i] = nameSplit[i].substring(0, 1).toUpperCase() + nameSplit[i].substring(1);
			sb.append(nameSplit[i]).append(" ");
		}
		String nameCap = sb.toString();
		sb.setLength(0);
		return nameCap;
	}

	public static boolean checkRegex(String string, String type) {
		if (type.equalsIgnoreCase("Tên")) {

			return string.matches(
					"[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]*");
		} else if (type.equalsIgnoreCase("SDT")) {
			return string.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$");
		}
		return false;

	}
}
