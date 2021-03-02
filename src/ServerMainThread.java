import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.control.TableView;

public class ServerMainThread extends Thread {
	ServerSocket listener;
	// TableView table;

	public ServerMainThread(int p, String IPAd){// TableView table) {
		// TODO Auto-generated constructor stub
		try
		{
			// this.table = table;
			int port = Integer.valueOf(p);
			String IP = IPAd;
			listener = new ServerSocket(port);
			System.out.println("Server started on " + port);
			System.out.println("Server is up and running waiting on connections...");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				Socket socket = listener.accept();
				new ServerThread(socket).start();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}