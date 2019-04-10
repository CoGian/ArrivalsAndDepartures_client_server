import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;


public class MultithreadedServerTCP {
	private static final int PORT = 1398;
	private static ConcurrentHashMap<Integer,Flight> tableOfArrivals_Departures = new ConcurrentHashMap<Integer,Flight>();

	
	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		
		//initialize map with some flights
		Flight f0 = new Flight(1800, "Arrival", "13:15") ; 
		Flight f1 = new Flight(1367, "Departure", "03:15") ; 
		Flight f2 = new Flight(2115, "Arrival", "05:15") ; 
		Flight f3 = new Flight(1821, "Arrival", "15:25") ; 
		Flight f4 = new Flight(1567, "Departure", "18:30") ; 
		
		tableOfArrivals_Departures.put(f0.getCode(), f0) ; 
		tableOfArrivals_Departures.put(f1.getCode(), f1) ; 
		tableOfArrivals_Departures.put(f2.getCode(), f2) ; 
		tableOfArrivals_Departures.put(f3.getCode(), f3) ; 
		tableOfArrivals_Departures.put(f4.getCode(), f4) ; 
		
		//create a socket listening 
		ServerSocket connectionSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);
		
		while (true) {	

			//accept a connection 
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			//create and start new thread to service the connection
			ServerThread sthread = new ServerThread(dataSocket,tableOfArrivals_Departures);
			sthread.start();
		}

	}

}
