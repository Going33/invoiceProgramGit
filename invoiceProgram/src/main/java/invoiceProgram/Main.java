package invoiceProgram;

public class Main {
	static Object buyer;
	static Object seller;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	windowApp test=	new windowApp(buyer, seller);
//jak wyciagnac buyer,seller info
	
		if (test == null) { // check if object is null

			System.out.println("NULL");
		} else {
			System.out.println("no null");
		}

	}

}
