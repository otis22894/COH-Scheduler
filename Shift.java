import java.util.*; 

public class Shift{
	
	private LifeGuard lifeguard; 
	private int startTime; 
	private int endTime; 
	private double hours; 

	Shift(LifeGuard guard,int startTime,int endTime){
		this.lifeguard = guard; 
		this.startTime = startTime; 
		this.endTime = endTime; 
		this.hours = endTime-startTime; 
	}

	public LifeGuard getLifeGuard(){
		return lifeguard; 
	}

	public int getStart(){
		return startTime; 
	}

	public int getEnd(){
		return endTime; 
	}

	public double getHours(){
		return hours; 
	}

	public boolean alreadyWorking(int start, int end){
		return (start>=startTime & start<=endTime) | (end>=startTime & end<=endTime); 
	}

	public String toString(){
		if(this instanceof RequestOffShift){
			return "Request Off"; 
		}
		return startTime + "-" + endTime; 
	}

	/*public boolean alreadyWorking(int day){
		for(int i = 0; i<shiftList.get(day).size();i++){
			Shift current = shiftList.get(day).get(i); 
			if(this.alreadyWorking(current.getStart(),current.getEnd())){
				return true; 
			}
		}
		return false; 
	}

	public int getTotalShifts(LifeGuard guard){
		int count = 0; 
		for(int i = 0; i <shiftList.size(); i++){
			if(shiftList.get(i).get(day).getLifeGuard().equals(guard)){
				count++;
			}
		}
		return count; 
	}*/
}