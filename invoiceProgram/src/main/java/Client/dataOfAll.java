package Client;

import java.util.ArrayList;
import java.util.List;

public class dataOfAll implements SomeInvoiceMethods_DefinitelyItNeedsBetterNameInterface {

	private final List<String> infoList = new ArrayList<String>();

	private int i = 0;

	/**
	 * Unification all datas to the one class.
	 * i=1 - seller
	 * i=2 - buyer
	 * i=3 - service
	 * dataOfSeller, dataOfBuyer and dataOfService have been deleted
	 */

	public dataOfAll(int i) {
		this.i = i;
		switch (i) {
		case 1:
			infoList.add("01.Seller:");
			infoList.add("02.Address:");
			infoList.add("03.VAT ID:");
			infoList.add("04.Company No.:");
			infoList.add("05.Payment method:");
			infoList.add("06.Due date:");
			infoList.add("07.Bank:");
			infoList.add("08.BIC (SWIFT):");
			break;
		case 2:
			infoList.add("01.Buyer:");
			infoList.add("02.Address:");
			infoList.add("03.VAT ID:");
			infoList.add("04.Company No.:");
			break;
		case 3:
			infoList.add("No.");
			infoList.add("Name of the goods/service");
			infoList.add("PKWiU");
			infoList.add("Qty");
			infoList.add("Unit");
			infoList.add("Unit price");
			infoList.add("VAT rate");
			infoList.add("Net value");
			infoList.add("VAT amount");
			infoList.add("Gross value");
			break;
		default:
			break;
		}
	}

	@Override
	public int findSize() {
		return infoList.size();
	}

	@Override
	public String elements(int i) {
		return infoList.get(i);
	}

	@Override
	public void putInInfos(String string1, String string2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayInInfos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDuplicate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTreeMap() {
		// TODO Auto-generated method stub

	}

}