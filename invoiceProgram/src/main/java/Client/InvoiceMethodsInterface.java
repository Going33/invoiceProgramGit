package Client;


public interface InvoiceMethodsInterface {
	
	/**
	 * Fill the Map
	  */
	void fillUpTheMap(String string1, String string2);
	/**
	 * Display the informations from the Map
	  */
	void displayInInfos();
	/**
	 * Delete duplicates from the Map
	  */
	void deleteDuplicate();
	/**
	 * Clear the Map
	  */
	void clearTreeMap();
	/**
	 * Find size of the list 
	  */
	int findSize();
	/**
	 * Get a specific item from the map
	  */
	String elements(int i);
}
