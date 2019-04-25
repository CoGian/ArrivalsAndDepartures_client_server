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
			
				if (outmsg.equals("EXIT")) {
					outmsg = app.prepareExit();
					out.println(outmsg);
					dataSocket.close();
					break ;
				}
				long start = System.currentTimeMillis(); // finding the time before the operation is executed
				out.println(outmsg);
				
				inmsg = in.readLine();
				long end = System.currentTimeMillis(); // finding the time after the operation is executed
				float sec = (end - start) / 1000F ;  //finding the time difference and converting it into seconds
				app.processReply(inmsg,sec);
			}
		}	
		catch(IOException WERR) {
			System.out.println("WERR") ;
		}
				
		
	}

}
