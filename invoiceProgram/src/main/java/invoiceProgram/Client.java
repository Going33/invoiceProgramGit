package invoiceProgram;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Client implements invoiceParticipantsInterface {

	private Map<String, String> ClientMap = new TreeMap<String, String>();
	private Set<String> keys = ClientMap.keySet(); // The set of keys in the map.
	private Iterator<String> keyIter = keys.iterator();
	
	
	
	//////////
	
	public Map<String, String> getMapBuyer()
	{
		return this.ClientMap;
	}
	

	//////////
	
	@Override
	public void putInInfos(String string1, String string2) {
		
		ClientMap.put(string1, string2);
	}
	@Override
	public void displayInInfos() {
		for (Map.Entry<String, String> set : ClientMap.entrySet()) {
			// Printing all elements of a Map
			System.out.println(set.getKey() + " " + set.getValue());
		}


	}
	
	@Override
	public void deleteDuplicate() {
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			String value = ClientMap.get(key);
			ClientMap.put(value, key);
		}
		
	}
	@Override
	public void clearTreeMap() {
		ClientMap.clear();
		
	}


}
