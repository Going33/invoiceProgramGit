package Client;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import Data.BinBase;



public class Client implements InvoiceMethodsInterface {

	private SortedMap<String, String> ClientMap = new TreeMap<String, String>();
	private Set<String> keys = ClientMap.keySet(); // The set of keys in the map.
	private Iterator<String> keyIter = keys.iterator();
	BinBase writeTo = new BinBase();
	byte[] bytes = new byte[8192];
	
	
	//////////
	
	public Map<String, String> getClientMap()
	{
		return this.ClientMap;
	}
	

	//////////
	
	@Override
	public void fillUpTheMap(String string1, String string2) {
		
		ClientMap.put(string1, string2);
	//	displayInInfos();
		writeTo.writeToTheBin(string1,string2);
		
	}
public void dupa() throws IOException
{
    // Save the byte array to a file
    FileOutputStream fos = new FileOutputStream("treeMapData.bin");
    fos.write(bytes);
    fos.close();
    // Print the byte array
    for (byte b : bytes) {
        System.out.print(b + " ");
    }
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



	@Override
	public String elements(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int findSize() {
		// TODO Auto-generated method stub
		return 0;
	}


}
