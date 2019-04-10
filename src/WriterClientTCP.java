import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class WriterClientTCP {
	
	private static final String HOST = "localhost";
	private static final int PORT = 1398;
	
	public static void main(String[] args)  {
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
				outmsg = app.prepareRequest("WRITER");
				
				/*
				// code for automation of writer  
				outmsg = "WRITE 1567 ARRIVAL 15:3" + i ;
				if (i == 10) outmsg = "EXIT" ; 
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
		catch(IOException e0) {
			System.out.println("WERR") ;
		}
				
		
	}

}
