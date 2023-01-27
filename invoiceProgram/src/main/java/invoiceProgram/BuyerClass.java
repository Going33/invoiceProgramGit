package invoiceProgram;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class BuyerClass implements invoiceParticipantsInterface  {

	private Map<String, String> BuyerMap = new TreeMap<String, String>();
	private Set<String> keys = BuyerMap.keySet(); // The set of keys in the map.
	private Iterator<String> keyIter = keys.iterator();
	
	
	
	//////////
	
	public Map<String, String> getMapBuyer()
	{
		return this.BuyerMap;
	}
	

	//////////
	
	@Override
	public void putInInfos(String string1, String string2) {
		
		BuyerMap.put(string1, string2);
	}
	@Override
	public void displayInInfos() {
		for (Map.Entry<String, String> set : BuyerMap.entrySet()) {
			// Printing all elements of a Map
			System.out.println(set.getKey() + " " + set.getValue());
		}


	}
	
	@Override
	public void deleteDuplicate() {
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value = BuyerMap.get(key);
			BuyerMap.put(value, key);
		}
		
	}
	@Override
	public void clearTreeMap() {
		BuyerMap.clear();
		
	}

	
	
}
