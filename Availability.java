import java.util.*; 

public class Availability{

	private int beginningHour; 
	private int endingHour; 

	public Availability(int beginningHour, int endingHour){
		this.beginningHour = beginningHour; 
		this.endingHour = endingHour; 
	}

	public void changeAvailability(int beginningHour,int endingHour){
		this.beginningHour = beginningHour; 
		this.endingHour = endingHour; 
	}

	public boolean canWork(int shiftBegin, int shiftEnd){
		if(beginningHour == -1 & endingHour == -1){
			return true; 
		}else{
			return (shiftBegin >= beginningHour) & ((shiftEnd <= endingHour) | (endingHour == -1)); 
		}
	}
}