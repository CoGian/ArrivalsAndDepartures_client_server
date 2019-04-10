import java.text.*;
import java.util.Date;

//Simple class for flights 
public class Flight {

	private int code ;// the code of flight
	private String state ; //arrival or departure
	private Date time ; // time of arrival or departure 
	private DateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public Flight(int code, String state, String time) {
		
		this.code = code;
		this.state = state;
		
		//formatting time from string to date 
		try {
			this.time = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	public Date getTime() {
		return time;
	}
	
	
	
	
	
}
