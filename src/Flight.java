import java.text.*;

//Simple class for flights 
public class Flight {

	private int code ;// the code of flight
	private String state ; //arrival or departure
	private String time ; // time of arrival or departure 

	
	public Flight(int code, String state, String time) {
		
		this.code = code;
		this.state = state;
		this.time = time ; 
		
		
	}

	public int getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	public String getTime() {
		return time;
	}
	
	
	
	
	
}
