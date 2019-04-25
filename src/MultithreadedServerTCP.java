import java.net.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.io.*;


public class MultithreadedServerTCP {
	private static final int PORT = 1398;
	private static ConcurrentHashMap<String,Flight> tableOfArrivals_Departures = new ConcurrentHashMap<String,Flight>();
	private static HashMap<String, ReadWriteLock> flights_on_processing = new HashMap<String, ReadWriteLock>() ; 
	
	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		
		//initialize map with some flights
		ServerProtocol app = new ServerProtocol(tableOfArrivals_Departures,flights_on_processing);
		String outmsg = app.processRequest("WRITE XU1800 ARRIVAL 13:15");
		outmsg = app.processRequest("WRITE LO2345 DEPARTURE 03:15");
		outmsg = app.processRequest("ALTER LO2345 ARRIVAL ");
		System.out.println(outmsg);
		outmsg = app.processRequest("WRITE XA1234 ARRIVAL 05:15");
		outmsg = app.processRequest("WRITE HA9000 ARRIVAL 15:25");
		outmsg = app.processRequest("WRITE GI6000 DEPARTURE 18:30");
		
		//create a socket listening 
		ServerSocket connectionSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);
		
		while (true) {	

			//accept a connection 
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			//create and start new thread to service the connection
			ServerThread sthread = new ServerThread(dataSocket,tableOfArrivals_Departures, flights_on_processing);
			sthread.start();
		}

	}

}
