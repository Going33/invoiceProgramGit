package Client;

import java.util.ArrayList;
import java.util.List;

public class DataOfAll implements InvoiceMethodsInterface {
	private final List<String> infoList = new ArrayList<String>();

	/**
	 * Unification all datas to the one class. i=1 - seller i=2 - buyer i=3 -......
	 * service dataOfSeller, dataOfBuyer and dataOfService have been deleted
	 */

	public DataOfAll(float i) {
		switch ((int) i) {
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
			infoList.add("01.No.");
			infoList.add("02.Name of the goods/service");
			infoList.add("03.PKWiU");
			infoList.add("04.Qty");
			infoList.add("05.Unit");
			infoList.add("06.Unit price");
			infoList.add("07.VAT rate");
			infoList.add("08.Net value");
			infoList.add("09.VAT amount");
			infoList.add("10.Gross value");
			break;
		case 4:
			infoList.add("VAT %");
			infoList.add("No: ");
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
	public void fillUpTheMap(String string1, String string2) {
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