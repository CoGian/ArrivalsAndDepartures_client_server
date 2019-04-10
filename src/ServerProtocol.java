import java.util.concurrent.ConcurrentHashMap;

public class ServerProtocol {
	private ConcurrentHashMap<Integer,Flight> tableOfArrivals_Departures ; 

	public ServerProtocol (ConcurrentHashMap<Integer, Flight> map) {
		
		tableOfArrivals_Departures = map;
        }

	//take the input message and return the output message accordingly if the client is a writer or a reader 
	public String processRequest(String theInput) {
	 
		
			String [] arrOfStr = theInput.split(" "); // temporary array of strings to put strings of input message after splitting them 
			System.out.println(theInput);
			String order = arrOfStr[0] ; // order of client WRITE or READ 
			
			if (order.equals("WRITE")) { //writer client
				
				int code = 	Integer.parseInt(arrOfStr[1]) ;// code of flight which will be added in map
				String state = arrOfStr[2]; // state of flight which will be added in map
				String time = arrOfStr[3] ; // time  of flight which will be added in map
				

				Flight newFlight = new Flight(code, state, time) ; //create new flight object 
				
				try {
					Flight oldFlight = tableOfArrivals_Departures.put(code, newFlight) ; //add new flight in map and if code already existed return old flight 
					
					// check if code existed
					if (oldFlight == null )
						return "WOK" ;
					else
						return  "WOK : THIS FLIGHT WAS ALTERED OLD INFO: (" +  oldFlight.getCode() + ' ' + oldFlight.getState() + ' '
						                        + oldFlight.getTime() + ')' ; 
				} catch (Exception e) {
					// TODO: handle exception
					return  "WERR" ; 
				}
				
			}
			else if (order.equals("DELETE")) {
				int code = 	Integer.parseInt(arrOfStr[1]) ;// code of flight which will be deleted from map
				
				Flight oldFlight = tableOfArrivals_Departures.remove(code) ; 
				
				if (oldFlight == null)
					return "WERR : Flight " + code +" wasn't found" ;
				else 
					return "WOK" ;
				
			}
			else if (order.equals("READ"))	{ //reader client
				
				int code = 	Integer.parseInt(arrOfStr[1]) ;// code of flight which will be searched in map
				
				Flight f = tableOfArrivals_Departures.get(code) ; 
				
				//check if flight wan't found 
				if (f == null)
					return "RERR" ; 
				else
					return "ROK " + f.getCode() + ' ' + f.getState() + ' ' + f.getTime()  ; 
			}
			else if (theInput.equals("EXIT")) //prompt to exit of client 
				return "EXIT" ;
			else 
				return "Wrong Format please type: 'WRITE/DELETE <Flight's code> <State> <Time>' if you are a writer "
						+ "or 'READ <Flight's code>' if you are a reader'." ; 
			
						
	        
	        
	        
            
	}
}
