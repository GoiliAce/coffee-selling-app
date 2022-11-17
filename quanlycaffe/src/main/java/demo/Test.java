package demo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "where s = ? and s = ? and";
		s = s.replaceFirst("and", "");
		System.out.println(s);

	}

}
