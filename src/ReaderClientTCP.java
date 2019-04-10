import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ReaderClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1398;
		
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
					
		try {
			Socket dataSocket = new Socket(HOST, PORT);
					
			InputStream is = dataSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter out = new PrintWriter(os,true);
					       	
			String inmsg, outmsg;
			ClientProtocol app = new ClientProtocol();
			
			
			int i = 0 ;
			while (true) {
				outmsg = app.prepareRequest("READER");
				
				/* 
				// code for automation of reader  
				outmsg = "READ 1567";
				if (i==15) outmsg = "EXIT" ; 
				i++ ;
				*/
				
				if (outmsg.equals("EXIT")) {
					outmsg = app.prepareExit();
					out.println(outmsg);
					dataSocket.close();
					break ;
				}
				out.println(outmsg);
				
				inmsg = in.readLine();
				
				app.processReply(inmsg);
		
					
			}
		}
		catch(IOException RERR) {
			System.out.println("RERR") ;
		}
	}
}
