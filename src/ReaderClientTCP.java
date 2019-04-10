import java.net.*;
import java.io.*;

public class ReaderClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1398;
		
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
					

		Socket dataSocket = new Socket(HOST, PORT);
				
		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os,true);
				       	
		String inmsg, outmsg;
		ClientProtocol app = new ClientProtocol();
				
				
		outmsg = app.prepareRequest();
		out.println(outmsg);
		inmsg = in.readLine();
		app.processReply(inmsg);
					
		outmsg = app.prepareExit();
		out.println(outmsg);
				
		dataSocket.close();
			
	}
}
