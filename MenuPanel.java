import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.io.*; 

public class MenuPanel extends JPanel{
	
	private JTabbedPane shiftPane;
	private JTabbedPane myPane;
	private EmployeeLists employeeList;
	private ArrayList<ArrayList<Shift>> weeklyShifts;
	private LifeGuardPanel lgPanel;
	private WSIPanel wsiPanel;
	private PrivateLessonPanel plPanel;
	private PermanentShiftPanel permanentPanel; 
	private FinishPanel finishPanel;
	private EditEmployeePanel employeePanel;
	private NewRequestOffPanel requestoffPanel; 
	private EmployeeView employeeViewPanel; 
	public static JFrame myFrame;
	private File shiftFile; 
	private Scanner scanner; 
	private ArrayList<Integer> days; 
	private int startTime; 
	private int endTime; 
	private int number; 
	private String type; 
	private File file; 
	private Scanner scaner; 
	private PrintWriter out; 
	public static ArrayList<String> permanentShifts;
	private String temp;  
	public static int openWindows; 

	public MenuPanel(boolean check){

		openWindows = 1; 

		shiftPane = new JTabbedPane(); 
		myPane = new JTabbedPane();
		myFrame = new JFrame("Menu"); 
		myFrame.setIconImage(new ImageIcon("$.jpg").getImage());  

		employeeList = new EmployeeLists(ScheduleDriver.facility + " Employees.txt");
		weeklyShifts = ScheduleDriver.weeklyShifts;
		if(check){
			this.fillPermanentShifts(); 
			employeeList = ScheduleDriver.employeeList; 
		}
		writeHours();

		lgPanel = new LifeGuardPanel(weeklyShifts,employeeList); 
		wsiPanel = new WSIPanel(weeklyShifts,employeeList); 
		plPanel = new PrivateLessonPanel(weeklyShifts,employeeList); 
		finishPanel = new FinishPanel(weeklyShifts,employeeList); 
		employeePanel = new EditEmployeePanel(weeklyShifts,employeeList); 
		permanentPanel = new PermanentShiftPanel();
		requestoffPanel = new NewRequestOffPanel(weeklyShifts,employeeList); 
		employeeViewPanel = new EmployeeView(employeeList,weeklyShifts); 


		shiftPane.addTab("Auto Shift",null,permanentPanel,"Add Shift to be Included Acutomatically");
		shiftPane.addTab("Lifeguarding",null,lgPanel,"Add LifeGuard Shifts");
		shiftPane.addTab("WSI",null,wsiPanel,"Add WSI Shifts");
		shiftPane.addTab("Private Lesson",null,plPanel,"Add Private Lesson Shifts"); 
 
		myPane.addTab("Review Schedule",null,finishPanel,"Look at current schedule");
		myPane.addTab("Add Shifts",null,shiftPane,"Add Shifts to Schedule");  
		myPane.addTab("Edit/Add Employees",null,employeePanel,"Edit Employee Information or Add New Employees"); 
		myPane.addTab("Add Request Off",null,requestoffPanel,"Add Request Off Shifts"); 
		myPane.addTab("View Employee",null,employeeViewPanel,"View Employee"); 

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width-355)/2;
		int y = (dim.height-355)/2;

		myFrame.add(myPane); 
		myFrame.setVisible(true); 
		myFrame.setLocation(x,y); 
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		myFrame.setSize(355,450);
		myFrame.setResizable(false); 
		myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
    		public void windowClosing(java.awt.event.WindowEvent e) {
        		if (openWindows == 1) {
            		zeroHours();
            		System.exit(0);
        		}
        		openWindows--; 
    		}
		});
	}

	private void fillPermanentShifts(){
		try{ 
			permanentShifts = new ArrayList<String>(); 
			shiftFile = new File(ScheduleDriver.facility + " Shifts.txt"); 
			if(!shiftFile.exists()){
				shiftFile.createNewFile();
				JOptionPane.showMessageDialog(null,"You have no auto-shifts to fill, to add some, click on the \"Add Shifts\" tab"); 
			}
			scanner = new Scanner(shiftFile); 
			while(scanner.hasNext()){
				String shiftLine = scanner.nextLine(); 
				Scanner lineScan = new Scanner(shiftLine); 
				days = new ArrayList<Integer>(); 
				startTime = Integer.parseInt(lineScan.next());
				endTime = Integer.parseInt(lineScan.next()); 
				number = Integer.parseInt(lineScan.next()); 
				type = lineScan.next();
				temp = "";  
				while(lineScan.hasNext()){
					String day = lineScan.next(); 
					if(day.equalsIgnoreCase("M")){
						days.add(0); 
						temp = temp + "Mon"; 
					}
					if(day.equalsIgnoreCase("T")){
						days.add(1);
						temp = temp + " Tues"; 
					}
					if(day.equalsIgnoreCase("W")){
						days.add(2); 
						temp = temp + " Wed"; 
					}
					if(day.equalsIgnoreCase("Th")){
						days.add(3);
						temp = temp + " Thurs"; 
					}
					if(day.equalsIgnoreCase("F")){
						days.add(4);
						temp = temp + " Fri";  
					}
					if(day.equalsIgnoreCase("S")){
						days.add(5);
						temp = temp + " Sat";  
					}
					if(day.equalsIgnoreCase("Su")){
						days.add(6);
						temp = temp + " Sun"; 
					}
				}  
				permanentShifts.add(startTime + "-" + endTime + " " + temp);  
				ScheduleDriver.addGuards(days,number,startTime,endTime,type); 
			}  
		}catch(IOException e){
			System.out.println("No File"); 
			System.exit(0); 
		}
	}

	private void writeHours(){
		 try{
			file = new File(ScheduleDriver.facility + " Employees.txt"); 
			scanner = new Scanner(file); 
			String total = ""; 
			String lineTemp = ""; 
			while(scanner.hasNext()){
				String line = scanner.nextLine(); 
				Scanner lineScan = new Scanner(line);
				String type = lineScan.next() + " "; 
				String name = lineScan.next() + " " + lineScan.next(); 
				lineTemp = type + name + " " + employeeList.getGuard(name).getHours() + " ";
				lineScan.next(); 
				while(lineScan.hasNext()){
					lineTemp = lineTemp + lineScan.next() + " "; 
				}
				if(scanner.hasNext()){
					total = total + lineTemp + "\n"; 
				}else{
					total = total + lineTemp; 
				}
			}
			out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
			out.print(total); 
			out.close();
		}catch(IOException e){
			System.exit(0); 
		}
	}

	private int zeroHours(){
		try{
			file = new File(ScheduleDriver.facility + " Employees.txt"); 
			scanner = new Scanner(file); 
			String total = ""; 
			String lineTemp = ""; 
			while(scanner.hasNext()){
				String line = scanner.nextLine(); 
				Scanner lineScan = new Scanner(line);
				String type = lineScan.next() + " "; 
				String name = lineScan.next() + " " + lineScan.next(); 
				lineTemp = type + name + " " + "0" + " ";
				lineScan.next(); 
				while(lineScan.hasNext()){
					lineTemp = lineTemp + lineScan.next() + " "; 
				}
				if(scanner.hasNext()){
					total = total + lineTemp + "\n"; 
				}else{
					total = total + lineTemp; 
				}
			}
			out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
			out.print(total); 
			out.close();
		}catch(IOException e){
			System.exit(0); 
		}
		return JFrame.DISPOSE_ON_CLOSE; 
	}
}