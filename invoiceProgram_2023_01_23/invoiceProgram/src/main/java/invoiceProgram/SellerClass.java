package invoiceProgram;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class SellerClass implements invoiceParticipantsInterface {

	private Map<String, String> SellerMap = new TreeMap<String, String>();
	private Set<String> keys = SellerMap.keySet(); // The set of keys in the map.
	private Iterator<String> keyIter = keys.iterator();
	
	//////////
	
	public Map<String, String> getMapSeller()
	{
		return this.SellerMap;
	}
	

	//////////
	@Override
	public void putInInfos(String string1, String string2) {
		SellerMap.put(string1, string2);
		
	}


	
	
	@Override
	public void displayInInfos() {
		for (Map.Entry<String, String> set : SellerMap.entrySet()) {

			// Printing all elements of the Map
			System.out.println(set.getKey() + " " + set.getValue());
		}

	}

	@Override
	public void deleteDuplicate() {
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value = SellerMap.get(key);
			SellerMap.put(value, key);
		}

	}

	@Override
	public void clearTreeMap() {
		SellerMap.clear();
		
	}





}
