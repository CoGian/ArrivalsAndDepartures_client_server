import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ClientProtocol {
	
	public String prepareRequest(String client) throws IOException {
		
 		String theOutput ;
 		
 		while(true){
 			System.out.print("Input> ");
 	 		Scanner scanner = new Scanner(System.in); // scanner to read from a client
 	 		theOutput = scanner.nextLine();
 	 		if((theOutput.contains("WRITE") || theOutput.contains("DELETE")) && client.equals("WRITER")) // check the order accordingly the client  
 	 			break;
 	 		else if (theOutput.contains("READ") && client.equals("READER"))
 	 			break; 
 	 		else if (theOutput.equals("EXIT"))
 	 			break ;
 	 			
 	 		System.out.println("You are not allowed to do this action because you are a " + client + "! Please type an other order.");	
 	 		
 		}
 		
		
		return theOutput ; 
 		
 	
    }
    
    public String prepareExit() throws IOException {

 		String theOutput = "EXIT";
 		return theOutput;
    }

    public void processReply(String theInput) throws IOException {

    	System.out.println("Reply: " + theInput);
    }

}
