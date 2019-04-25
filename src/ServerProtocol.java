import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.*;


public class ServerProtocol {
	private ConcurrentHashMap<String,Flight> tableOfArrivals_Departures ;
	private HashMap<String, ReadWriteLock> flights_on_processing;  // Read Write locks are used only to held some experiments 
	
	

	public ServerProtocol (ConcurrentHashMap<String, Flight> table, HashMap<String, ReadWriteLock> processing  ) {
		
		tableOfArrivals_Departures = table;
		flights_on_processing = processing;
        }

	//take the input message and return the output message accordingly if the client is a writer or a reader 
	public String processRequest(String theInput) {
	 
		
			String [] arrOfStr = theInput.split(" "); // temporary array of strings to put strings of input message after splitting them 
			System.out.println("Order: " + theInput);
			String order = arrOfStr[0] ; // order of client WRITE ,DELETE or READ 
			
			if (order.equals("WRITE")) { //writer client
				
				String code = 	arrOfStr[1] ;// code of flight which will be added in map
				try {
					// check if flight existed
					Lock writelock = flights_on_processing.get(code).writeLock() ; // if flight doesn't exist here we will have null exception
					try {
						writelock.lock(); 
						return "WERR : Flight " + code + " already in table" ; 
					}finally {
						writelock.unlock();
					}
										
				}catch (Exception e) {
					// TODO: handle exception
					//no need to lock because flight doesn't exist  
					String state = arrOfStr[2]; // state of flight which will be added in map
					String time = arrOfStr[3] ; // time  of flight which will be added in map
										
					
					Flight newFlight = new Flight(code, state, time) ; //create new flight object 
					ReadWriteLock lock = new ReentrantReadWriteLock() ; 
										
					tableOfArrivals_Departures.put(code, newFlight); //add new flight in map 
					flights_on_processing.putIfAbsent(code, lock);
					return "WOKK" ;
				}
								
			}
			else if (order.equals("ALTER")) {
				String code = 	arrOfStr[1] ;// code of flight which will be altered in map
				try {
				Lock writelock = flights_on_processing.get(code).writeLock() ; 
				
					try {
						
						writelock.lock(); // if flight doesn't exist here we will have null exception
						System.out.println("A thread has taken write lock on " + code);
						String state = arrOfStr[2]; // state of flight which will be altered in map
						String time = null ;
						if (arrOfStr.length == 4)						
							 time = arrOfStr[3] ; // time  of flight which will be altered in map
						
						if (state.toLowerCase().equals("arrival") || state.toLowerCase().equals("departure")) {
							if (time == null )
								time = tableOfArrivals_Departures.get(code).getTime() ; 
						}else
						{
							time = state; 
							state =  tableOfArrivals_Departures.get(code).getState() ;
						}
						System.out.println("Going to sleep");
						Thread.sleep(1000);
						Flight newFlight = new Flight(code, state, time) ; //create new flight object 
						
						tableOfArrivals_Departures.replace(code, newFlight) ;// replace previous flight's info 
						
						return "WOKK" ;
					}finally {
						writelock.unlock();
					}
					
					
				}
				catch (Exception e) {
					// TODO: handle exception
					return "WERR : Flight " + code +" wasn't found" ; 
					}
			}
			else if (order.equals("DELETE")) {
				String code = 	arrOfStr[1] ;// code of flight which will be deleted from map
				
				try {
					Lock writelock = flights_on_processing.get(code).writeLock() ; // if flight doesn't exist here we will have null exception
					
					try {
						writelock.lock();
						
						tableOfArrivals_Departures.remove(code);
						
						return "WOKK" ;
					}finally {
						writelock.unlock();
						flights_on_processing.remove(code);
					}
				}catch (Exception e) {
					// TODO: handle exception
					return "WERR : Flight " + code +" wasn't found" ;
				}
							
			}
			else if (order.equals("READ"))	{ //reader client
				
				String code = 	arrOfStr[1] ;// code of flight which will be searched in map
				
				try {
					Lock readlock = flights_on_processing.get(code).readLock(); // if flight doesn't exist here we will have null exception
					
					try {
						readlock.lock();
						
						System.out.println("A thread has taken read lock on " + code);
						Flight f = tableOfArrivals_Departures.get(code) ; 
						return "ROK " + f.getCode() + ' ' + f.getState() + ' ' + f.getTime()  ;
					}finally {
						readlock.unlock();
					}
				}catch (Exception e) {
					// TODO: handle exception
					return "RERR" ; 
				}				
				
			}
			else if (theInput.equals("EXIT")) //prompt to exit of client 
				return "EXIT" ;			
			
			
			return "Wrong Format please type: 'WRITE/DELETE/ALTER <Flight's code> <State> <Time>' if you are a writer "
						+ "or 'READ <Flight's code>' if you are a reader'." ;
			
		
			
						
	        
	        
	        
            
	}
}
