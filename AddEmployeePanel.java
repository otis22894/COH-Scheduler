import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 
import java.io.*; 

public class AddEmployeePanel extends JPanel{
	
	private JTextField employeeName; 
	private JRadioButton wsiButton; 
	private JRadioButton lifeGuardButton; 
	private JPanel radioButtonPanel; 
	private JLabel nameLabel; 
	private ButtonGroup buttonGroup; 
	private JButton addEmployee; 
	private JButton goBack; 
	private JLabel message; 

	private JLabel[] dayLabels = {new JLabel("Monday Availability"),new JLabel("Tuesday Availability"),new JLabel("Wednesday Availability"),new JLabel("Thursday Availability"),new JLabel("Friday Availability"),new JLabel("Saturday Availability"),new JLabel("Sunday Availability")}; 
	private ArrayList<JTextField> availabilityFields; 
	public static final JLabel[] dashLabel = {new JLabel("-"),new JLabel("-"),new JLabel("-"),new JLabel("-"),new JLabel("-"),new JLabel("-"),new JLabel("-")};

	public AddEmployeePanel(){ 

		MenuPanel.openWindows++; 
		employeeName = new JTextField(10); 
		nameLabel = new JLabel("Name:"); 
		addEmployee = new JButton("Add Employee");
		goBack = new JButton("Go Back"); 
		addEmployee.addActionListener(new ButtonListener());  
		message = new JLabel("       Enter -1 for OPEN OR CLOSE              "); 

		wsiButton = new JRadioButton("WSI",false); 
		lifeGuardButton = new JRadioButton("LifeGuard",true); 
		buttonGroup = new ButtonGroup(); 
		buttonGroup.add(wsiButton); 
		buttonGroup.add(lifeGuardButton); 
		radioButtonPanel = new JPanel(); 
		radioButtonPanel.add(wsiButton); 
		radioButtonPanel.add(lifeGuardButton); 

		availabilityFields = new ArrayList<JTextField>(); 
		for(int i = 0; i<14; i++){
			JTextField current = new JTextField(5); 
			availabilityFields.add(current); 
		}

		this.add(nameLabel); 
		this.add(employeeName); 
		this.add(radioButtonPanel);
		this.add(message);  
		for(int i = 0; i<7; i++){
			this.add(dayLabels[i]);
			for(int j = 0; j < 2; j++){
				this.add(availabilityFields.get(i*2)); 
				this.add(dashLabel[i]); 
				this.add(availabilityFields.get((i*2)+1)); 
			}
		}
		this.add(addEmployee); 
		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace();
		if(!elements[1].getClassName().equals("EmployeeLists")){
		this.add(goBack); 
		goBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				EditEmployeePanel.addEmployeeFrame.dispose();  
				MenuPanel menuPanel = new MenuPanel(false);  
			}
		});
		}else{
			this.add(goBack);
			goBack.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){ 
					EmployeeLists.check = false;
					EmployeeLists.myFrame.dispose();  
				}
			}); 
		}
	}

	private class ButtonListener implements ActionListener{

		private String line;
		private PrintWriter out;

		public void actionPerformed(ActionEvent e){
			try{
				if(employeeName.getText().length() == 0){
					throw new Exception("Please enter a name (first and last!)"); 
				}
				for(int i = 0;i<14;i++){
					if(availabilityFields.get(i).getText().length() == 0){
						throw new Exception("One or more availability fields are empty"); 
					}
					if(Integer.parseInt(availabilityFields.get(i).getText()) > 2400 | (Integer.parseInt(availabilityFields.get(i).getText()) < 0 & Integer.parseInt(availabilityFields.get(i).getText()) != -1)){
						throw new Exception("One or more availability fields have invalid inputs\n Military Time Only"); 
					}
				}
				if(lifeGuardButton.isSelected()){
					line = "L " + employeeName.getText() + " 0 "; 
				}else{
					line = "W " + employeeName.getText() + " 0 ";  
				}
				for(int i = 0; i < 14; i++){
					line = line + availabilityFields.get(i).getText() + " "; 
				}
				try{
					out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt", true))); 
					BufferedReader br = new BufferedReader(new FileReader(ScheduleDriver.facility + " Employees.txt"));     
					if (br.readLine() != null) {
						out.print("\n" + line);
					}else{
						out.print(line); 
					}
					out.close(); 
				}catch(IOException w){
					System.out.println("Failure"); 
					System.exit(0); 
				}
				employeeName.setText(""); 
				for(int i = 0; i<14;i++){
					availabilityFields.get(i).setText(""); 
				} 
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage()); 
				return; 
			}
		}
	}
}