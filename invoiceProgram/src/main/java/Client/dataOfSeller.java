package Client;

import java.util.ArrayList;
import java.util.List;

public final class dataOfSeller {
	
	private final List<String> sellerInfo = new ArrayList<String>();
	
	public dataOfSeller()
	{
		sellerInfo.add("01.Seller:");
		sellerInfo.add("02.Address:");
		sellerInfo.add("03.VAT ID:");
		sellerInfo.add("04.Company No.:");
		sellerInfo.add("05.Payment method:");
		sellerInfo.add("06.Due date:");
		sellerInfo.add("07.Bank:");
		sellerInfo.add("08.BIC (SWIFT):");
				
	}

	public int size() {
		return sellerInfo.size();		
	}
	public String elements(int i)
	{
		return sellerInfo.get(i);
	}
	
}
