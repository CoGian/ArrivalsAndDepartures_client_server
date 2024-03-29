
import java.util.Scanner;
import java.io.*;
public class ClientProtocol {
	
	public String prepareRequest(String client) throws IOException {
		
 		String theOutput ;
 		
 		while(true){
 			System.out.print("Input> ");
 	 		Scanner scanner = new Scanner(System.in); // scanner to read from a client
 	 		theOutput = scanner.nextLine();
 	 		if((theOutput.contains("WRITE") || theOutput.contains("DELETE") || theOutput.contains("ALTER")) && !client.equals("WRITER")) // check the order accordingly the client  
 	 			System.out.println("You are not allowed to write/delete/alter beacause you are a " + client + "! Please type an other order."); 
 	 		else if (theOutput.contains("READ") && !client.equals("READER"))
 	 			System.out.println("You are not allowed to read because you are a " + client + "! Please type an other order."); 
 	 		else 
 	 			break ;
 	 			
 	 			
 	 		
 		}
 		
		
		return theOutput ; 
 		
 	
    }
    
    public String prepareExit() throws IOException {

 		String theOutput = "EXIT";
 		return theOutput;
    }

    public void processReply(String theInput, Float sec) throws IOException {

    	System.out.println("Reply: " + theInput +  " received in " + sec + " sec");
    }

}
