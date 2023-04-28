package Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinBase {

	
	
	
	
	
	
	public void writeToTheBin(String key, String value) {
        String fileName = "data.bin";
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName))) {
            // Write binary data to the file

            out.writeUTF(key);
            out.writeUTF(value);
            out.writeUTF("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 public void readTheBin() {
	        String fileName = "data.bin";
	        try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
	            // Read binary data from the file
	            int intValue = in.readInt();
	            double doubleValue = in.readDouble();
	            boolean boolValue = in.readBoolean();
	            String stringValue = in.readUTF();
	            
	            // Print the read values
	            System.out.println("int value: " + intValue);
	            System.out.println("double value: " + doubleValue);
	            System.out.println("boolean value: " + boolValue);
	            System.out.println("string value: " + stringValue);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
