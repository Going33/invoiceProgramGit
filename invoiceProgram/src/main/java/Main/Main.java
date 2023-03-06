package Main;

import java.awt.EventQueue;



public class Main {
	static Object buyer;
	static Object seller;

	public static void main(String[] args) {
		{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						new GUI.windowApp(buyer, seller);
					} catch (Exception e) {
						
					}
				}
			});
		}

	
	
	
	
	
	}
}
