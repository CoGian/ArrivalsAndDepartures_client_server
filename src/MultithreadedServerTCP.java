import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.*;


public class MultithreadedServerTCP {
	private static final int PORT = 1398;
	private static ConcurrentHashMap<Integer,Flight> tableOfArrivals_Departures = new ConcurrentHashMap<Integer,Flight>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ServerSocket connectionSocket = connectionSocket = new ServerSocket(PORT);
		
		
		while (true) {	

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			//ServerThread sthread = new ServerThread(dataSocket);
			//sthread.start();
		}

	}

}
