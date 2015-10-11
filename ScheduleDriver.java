import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.io.*; 

public class ScheduleDriver{
	
	public static String facility; 
	final static String SCHEDULEPANEL = "Schedule Panel"; 
	final static String LGPANEL = "LG Panel";
	final static String WSIPANEL = "WSI Panel";
	public static ArrayList<ArrayList<Shift>> weeklyShifts;
	public static EmployeeLists employeeList;  
	public static JFrame frame; 
	public static JFrame openingFrame; 
	private static File file; 
	private static Scanner scanner; 
	private static ArrayList<Integer> days; 

	public static void main(String[] args){

		weeklyShifts = new ArrayList<ArrayList<Shift>>();  
		ArrayList<Shift> mondayShifts = new ArrayList<Shift>(); 
		ArrayList<Shift> tuesdayShifts = new ArrayList<Shift>(); 
		ArrayList<Shift> wednesdayShifts = new ArrayList<Shift>(); 
		ArrayList<Shift> thursdayShifts = new ArrayList<Shift>(); 
		ArrayList<Shift> fridayShifts = new ArrayList<Shift>();
		ArrayList<Shift> saturdayShifts = new ArrayList<Shift>();
		ArrayList<Shift> sundayShifts = new ArrayList<Shift>();
		weeklyShifts.add(mondayShifts); 
		weeklyShifts.add(tuesdayShifts); 
		weeklyShifts.add(wednesdayShifts); 
		weeklyShifts.add(thursdayShifts); 
		weeklyShifts.add(fridayShifts); 
		weeklyShifts.add(saturdayShifts); 
		weeklyShifts.add(sundayShifts);
		Object[] possibleValues = {"Whitney Ranch","Heritage","Multigen"}; 
		facility = (String)JOptionPane.showInputDialog(null,"Please Choose a Facility", "Input",
		JOptionPane.INFORMATION_MESSAGE, null,possibleValues, possibleValues[0]); 
		if(facility == null){
			System.exit(0); 
		}
		employeeList = new EmployeeLists(facility + " Employees.txt");
		frame = new JFrame("COH Schedule Maker 1.0"); 

		openingFrame = new JFrame("COH Schedule Maker"); 
		OpeningPanel openingPanel = new OpeningPanel(); 
		openingFrame.add(openingPanel);
		openingFrame.setIconImage(new ImageIcon("$.jpg").getImage()); 
		openingFrame.setVisible(true);
		openingFrame.setSize(350,150);
		openingFrame.setLocationRelativeTo(null); 
		openingFrame.setResizable(true);
		openingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public static void addGuards(ArrayList<Integer> days,int number,int startTime,int endTime,String type){
 
		LifeGuard currentGuard; 
		Shift shift; 
		boolean check; 
		int count; 
		if(type.equalsIgnoreCase("L")){
			for (int i = 1; i<=number; i++){ 
				for(int j = 0; j<days.size(); j++){
						do{
							currentGuard = employeeList.getRandomGuard();
							shift = new Shift(currentGuard,startTime,endTime); 
						}
						while(currentGuard.getHours() > (employeeList.getAverageHours()) | !currentGuard.availability[days.get(j)].canWork(startTime,endTime) | (LifeGuardPanel.alreadyWorking(weeklyShifts,currentGuard,shift,days.get(j),startTime,endTime)));
					currentGuard.giveHours(((double)(endTime-startTime))/100); 
					weeklyShifts.get(days.get(j)).add(shift);
				} 
			}
		}else if(type.equalsIgnoreCase("T")){
			for (int i = 1; i<=number; i++){
				do{
					count = 0;
					check = true; 
					do{ 
						currentGuard = employeeList.getRandomGuard();
						shift = new TeachingShift(currentGuard,startTime,endTime);
					}while(currentGuard.getHours() > (employeeList.getAverageHours()) | currentGuard instanceof WSI == false);
					for(int k = 0; k < days.size(); k++){
						if(currentGuard.availability[days.get(k)].canWork(startTime,endTime) & !(WSIPanel.alreadyWorking(weeklyShifts,currentGuard,shift,days.get(k),startTime,endTime))){
							count++;
						}
					}
					if(count == days.size()){
						check = false; 
					}
				}while(check); 
				for(int j = 0; j<days.size(); j++){
					currentGuard.giveHours((endTime-startTime)/100); 
					weeklyShifts.get(days.get(j)).add(shift); 
				} 
			}
		}
	}

	public static void getRequestOffs(){
		try{
			file = new File(ScheduleDriver.facility +  " RequestOffSheet.txt");
			if(!file.exists()){
				file.createNewFile(); 
			}
			scanner = new Scanner(file); 
			while(scanner.hasNext()){
				String line = scanner.nextLine(); 
				Scanner lineScan = new Scanner(line); 
				String name = lineScan.next() + " " + lineScan.next(); 
				GregorianCalendar date = new GregorianCalendar(Integer.parseInt(lineScan.next()),Integer.parseInt(lineScan.next())-1,Integer.parseInt(lineScan.next()));
				String start = lineScan.next(); 
				String end = lineScan.next(); 
				if((date.before(OpeningPanel.endDate) | date.equals(OpeningPanel.endDate)) & (date.after(OpeningPanel.startDate) | (date.equals(OpeningPanel.startDate)))){
					days = new ArrayList<Integer>(); 
					while(lineScan.hasNext()){
						String day = lineScan.next(); 
						if(day.equalsIgnoreCase("M")){
							days.add(0); 
						}
						if(day.equalsIgnoreCase("T")){
							days.add(1);
						}
						if(day.equalsIgnoreCase("W")){
							days.add(2); 
						}
						if(day.equalsIgnoreCase("Th")){
							days.add(3);
						}
						if(day.equalsIgnoreCase("F")){
							days.add(4);
						}
						if(day.equalsIgnoreCase("S")){
							days.add(5);
						}
						if(day.equalsIgnoreCase("Su")){
							days.add(6);
						}
					}
					RequestOffPanel.addGuard(days,name,Integer.parseInt(start),Integer.parseInt(end));
				} 
			}
		}catch(IOException e){
			System.out.println("No File"); 
			System.exit(0); 
		}
	}
} 