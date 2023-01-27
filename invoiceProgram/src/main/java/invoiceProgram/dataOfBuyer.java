package invoiceProgram;

import java.util.ArrayList;
import java.util.List;

public final class dataOfBuyer {
	
	private final List<String> buyerInfo = new ArrayList<String>();
	
	dataOfBuyer()
	{
		buyerInfo.add("01.Buyer:");
		buyerInfo.add("02.Address:");
		buyerInfo.add("03.VAT ID:");
		buyerInfo.add("04.Company No.:");
						
	}

	public int size() {
		return buyerInfo.size();		
	}
	public String elements(int i)
	{
		return buyerInfo.get(i);
	}
	
}
