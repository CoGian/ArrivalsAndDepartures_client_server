import java.util.concurrent.ConcurrentHashMap;

public class ServerProtocol {
	private ConcurrentHashMap<Integer,Flight> tableOfArrivals_Departures ; 

	public ServerProtocol (ConcurrentHashMap map) {
		
		tableOfArrivals_Departures = map;
        }

	public String processRequest(String theInput) {
	 
	        String theOutput;
            
		
		return theInput;
	}
}
