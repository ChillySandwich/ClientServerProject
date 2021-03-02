import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javafx.scene.control.TableView;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ServerThread extends Thread
{
	Socket socket;
	//    TableView table;
	public ServerThread(Socket s)// TableView table)
	{
		//     this.table = table;
		this.socket = s;
	}

	public void run()
	{
		try {

			databaseConnection dbconnect = new databaseConnection();

			InputStreamReader in = new InputStreamReader(socket.getInputStream());
			BufferedReader bf = new BufferedReader(in);

			PrintWriter pr = new PrintWriter(socket.getOutputStream());

			System.out.println("A client request receieved at " + socket);
			//gets current date, converts it to string
			String str = bf.readLine();
			System.out.println(str);
			
//			Decryption decrypt = new Decryption();
//			String decryptstr = decrypt.decrypt(str);
			

			String[] temp;
			String delimiter = ":";

			temp = str.split(delimiter);

			String command = temp[0];
			System.out.println(command);
			if(command.equals("LOGIN")) {
				String username = temp[1];  
				String password = temp[2];
				String userId = dbconnect.GetUserId(username, password);

				pr.println(userId);
				pr.flush();
			}

			else if(command.equals("GETTASKS")) {
				String username = temp[1];
				ArrayList<String> taskList = dbconnect.GetTasks(username);
				String taskStr = "";
	            for (String task : taskList) {
	                taskStr = taskStr + task + "|";
	            }
				pr.println(taskStr);
				pr.flush();
				System.out.println(taskStr);
				

			}

			else if(command.equals("ADDTASKS")) {
				String userID = temp[1];  
				String task = temp[2];
				dbconnect.insertTask(userID, task);
			}

		}
		catch(IOException e)
		{
			System.out.println("Error: ");
			e.printStackTrace();
		}
	}
}