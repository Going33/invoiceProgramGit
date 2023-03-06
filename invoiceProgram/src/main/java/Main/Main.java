package Main;

import java.awt.EventQueue;

import GUI.windowApp;



public class Main {
	public static boolean help;
	public static void main(String[] args) {
		{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						 new GUI.windowApp();
			
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	
	
	
	
	
	}
}
