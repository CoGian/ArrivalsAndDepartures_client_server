import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
public class ServerThread extends Thread{
	
	private Socket dataSocket;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
   	private ConcurrentHashMap<String,Flight> tableOfArrivals_Departures ; 

   	public ServerThread(Socket socket,ConcurrentHashMap<String, Flight>  map) 
   	{
      		dataSocket = socket;
      		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
			tableOfArrivals_Departures = map;
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
      		}
    	}

	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			inmsg = in.readLine(); //read a line from buffer reader
			ServerProtocol app = new ServerProtocol(tableOfArrivals_Departures);
			outmsg = app.processRequest(inmsg); 
			
			while (!outmsg.equals("EXIT")) { //keep reading while EXIT was not sent by client 
				out.println(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg);		
			}
			
			System.out.println("A client has left");
			dataSocket.close();	

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}
