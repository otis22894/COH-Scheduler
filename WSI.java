public class WSI extends LifeGuard{

	private boolean teaching; 

	public WSI(double hours,String name){
		super(hours,name); 
		teaching = false; 
	}

	public void setTeaching(){
		teaching = true; 
	}
}