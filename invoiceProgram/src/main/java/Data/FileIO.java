package Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Client.Client;



//TODO
//below it's only an idea how to create "data base"
//inspired by https://github.com/ssoad/BankingSystem
// must be adjusted and implemented later

public class FileIO {

	public static Client client=null;

	public static void Read()
	{
//		Bank bank =null;
		FileInputStream fis =null;
		ObjectInputStream oin=null;
		try {
			fis =new FileInputStream("data");
			oin=new ObjectInputStream(fis);
			FileIO.client=(Client)oin.readObject();
			}

		catch (Exception en) {
			FileIO.client=new Client();
				}

		finally{
			try{
				if(oin!=null) oin.close();
			if(fis!=null) fis.close();
			}
			catch (IOException en) {
					}

		}
		//return bank;
	}

	public static void Write()
	{
		try {
			FileOutputStream fout=new FileOutputStream("data");
			ObjectOutputStream out=new ObjectOutputStream(fout);
			out.writeObject(FileIO.client);
			out.flush();
			fout.close();
			}
			catch(Exception en)
			{

			}
	}

}
