import java.util.*; 
import java.io.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class EmployeeLists{

	public ArrayList<LifeGuard> lifeGuardList; 
	private Scanner scanner; 
	private File employeeFile; 
	private static final String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"}; 
	public static boolean check; 
	public static JFrame myFrame;

	public EmployeeLists(String fileName){
		try{
			employeeFile = new File(fileName); 
			if(!employeeFile.exists()){
				JFrame.setDefaultLookAndFeelDecorated(true);
				employeeFile.createNewFile(); 
				JOptionPane.showMessageDialog(null,"No Employees Exist, Please Create Some to Continue"); 
				myFrame = new JFrame("Create New Employee"); 
				AddEmployeePanel myPanel = new AddEmployeePanel(); 
				myFrame.setResizable(false); 
				removeMinMaxClose(myFrame);  
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (dim.width-355)/2;
				int y = (dim.height-355)/2; 
				myFrame.add(myPanel);	
				myFrame.setVisible(true);
				myFrame.setSize(345,450);
				myFrame.setLocation(x,y); 
				myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JFrame.setDefaultLookAndFeelDecorated(false); 
				check = true; 
			}
			do{
				Thread.yield(); 
			}while(check); 
			scanner = new Scanner(employeeFile); 
			lifeGuardList = new ArrayList<LifeGuard>(); 

			while(scanner.hasNext()){
				String employeeLine = scanner.nextLine(); 
				Scanner lineParse = new Scanner(employeeLine); 
				lineParse.useDelimiter(" "); 
				if(lineParse.next().equalsIgnoreCase("L")){
					String name = lineParse.next()+" "+lineParse.next();
					Double hours = Double.parseDouble(lineParse.next());
					LifeGuard currentLifeGuard = new LifeGuard(hours,name); 
					for(int i = 0; i<=6; i++){
						Availability currentAvailability = new Availability(lineParse.nextInt(),lineParse.nextInt()); 
						currentLifeGuard.availability[i] = currentAvailability; 
					}
					lifeGuardList.add(currentLifeGuard); 
				}else{
					String name = lineParse.next()+" "+lineParse.next();
					Double hours = Double.parseDouble(lineParse.next());
					WSI currentLifeGuard = new WSI(hours,name); 
					for(int i = 0; i<=6; i++){
						Availability currentAvailability = new Availability(lineParse.nextInt(),lineParse.nextInt()); 
						currentLifeGuard.availability[i] = currentAvailability; 
					}
					lifeGuardList.add(currentLifeGuard); 
				}
			}
		}catch(IOException e){
			System.out.println("Please give me a real file, idiot"); 
			System.exit(0); 
		}
	}
	
	public LifeGuard getRandomGuard(){
		Random rand = new Random(); 
		return lifeGuardList.get(rand.nextInt(lifeGuardList.size())); 
	}
	
	public LifeGuard getGuard(String name){
		LifeGuard current = lifeGuardList.get(0);
		int i = 0;  
		while(!current.getName().equalsIgnoreCase(name)){
			current = lifeGuardList.get(++i); 
		}
		return current; 
	}

	public double getAverageHours(){
		double average = 0; 
		for(int i = 0; i<lifeGuardList.size();i++){
			average += lifeGuardList.get(i).getHours(); 
		}
		return average/lifeGuardList.size(); 
	}

	public String[] getGuardList(){
		String[] collection = new String[lifeGuardList.size()];
		for(int i = 0; i<lifeGuardList.size();i++){
			collection[i] = lifeGuardList.get(i).getName(); 
		}
		java.util.Arrays.sort(collection); 
		return collection; 
	}

	public String[] getWSIList(){
		String[] collection = new String[getWSICount()];
		int ind = 0; 
		for(int i = 0; i<lifeGuardList.size();i++){
			if(lifeGuardList.get(i) instanceof WSI){
				collection[ind] = lifeGuardList.get(i).getName();
				ind++; 
			}
		}
		java.util.Arrays.sort(collection); 
		return collection; 
	}

	public int getWSICount(){
		int count = 0; 
		for(int i = 0; i<lifeGuardList.size();i++){
			if(lifeGuardList.get(i) instanceof WSI){
				count++; 
			}
		}
		return count; 
	}

	public ArrayList<String> getGuardShifts(ArrayList<ArrayList<Shift>> shiftList, LifeGuard guard){
		ArrayList<String> returning = new ArrayList<String>(); 
		for(int i = 0; i<=6; i++){
			for(int j= 0; j<shiftList.get(i).size(); j++){
				if(shiftList.get(i).get(j).getLifeGuard().getName().equalsIgnoreCase(guard.getName())){
					returning.add(days[i] + " " + shiftList.get(i).get(j).toString()); 
				}
			}
		}
		return returning; 
	}

	private Object filler(){
		return null; 
	}

	public void removeMinMaxClose(Component comp){  
		if(comp instanceof JButton){  
    		String accName = ((JButton) comp).getAccessibleContext().getAccessibleName();   
    		if(accName.equals("Maximize")|| accName.equals("Iconify")||accName.equals("Close")) 
    			comp.getParent().remove(comp);  
  		}  
  		if (comp instanceof Container){  
    		Component[] comps = ((Container)comp).getComponents();  
    		for(int x = 0, y = comps.length; x < y; x++){  
      			removeMinMaxClose(comps[x]);  
    		}  
  		}  
	} 


}