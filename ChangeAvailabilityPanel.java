import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 
import java.io.*; 

public class ChangeAvailabilityPanel extends JPanel{
	 
	private ArrayList<JTextField> textFields;
	private static final JLabel[] dayLabels = {new JLabel("Monday"),new JLabel("Tuesday"),new JLabel("Wednesday"),new JLabel("Thursday"),new JLabel("Friday"),new JLabel("Saturday"),new JLabel("Sunday")};
	private JLabel nameLabel;  
	private ArrayList<JRadioButton> buttonList;
	private JButton changeAvailability; 
	private JButton goBack;   
	private JComboBox<String> comboBox;  

	public ChangeAvailabilityPanel(EmployeeLists employeeList){

		MenuPanel.openWindows++; 

		nameLabel = new JLabel("Choose Employee Name From List");
		comboBox = new JComboBox<String>(employeeList.getGuardList());  
		this.add(nameLabel); 
		this.add(comboBox); 

		textFields = new ArrayList<JTextField>(); 
		buttonList = new ArrayList<JRadioButton>();
		goBack = new JButton("Go Back");     

		for(int i = 0; i<7;i++){
			this.add(dayLabels[i]); 
			JTextField current = new JTextField(7); 
			current.setEditable(false); 
			textFields.add(current);
			this.add(current);
			this.add(AddEmployeePanel.dashLabel[i]);   
			JTextField current2 = new JTextField(7);
			current2.setEditable(false);  
			textFields.add(current2);
			this.add(current2); 
			JRadioButton current4 = new JRadioButton("Activate"); 
			current4.addActionListener(new RadioButtonListener()); 
			buttonList.add(current4); 
			this.add(current4);   
		}

		changeAvailability = new JButton("Change Availability"); 
		changeAvailability.addActionListener(new ButtonListner()); 
		this.add(changeAvailability); 
		this.add(goBack); 
		goBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				EditEmployeePanel.changeAvailabilityFrame.dispose();  
				MenuPanel menuPanel = new MenuPanel(false); 
			}
		}); 
	}

	private class RadioButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			for(int i = 0; i<7;i++){
				if(buttonList.get(i).equals(e.getSource())){
					if(buttonList.get(i).isSelected()){
						textFields.get(2*i).setEditable(true); 
						textFields.get(2*i+1).setEditable(true);
					}else{
						textFields.get(2*i).setEditable(false); 
						textFields.get(2*i+1).setEditable(false); 
					}
				}
			}
		}
	}

	private class ButtonListner implements ActionListener{

		private PrintWriter out; 
		private File file; 
		private Scanner scan; 
		private String total; 
		private String lineSoFar;
		private String line;
		private Scanner lineScan; 
		private String name;  

		public void actionPerformed(ActionEvent e){ 
			try{
				int count = 0; 
				for(int i = 0;i<7;i++){
					if(!buttonList.get(i).isSelected()){
						count++; 
					}
				}
				if(count == 7){
					throw new Exception("Please select at least one day to change Availability"); 
				}
				for(int i = 0;i<14;i++){
					if(textFields.get(i).getText().length() == 0){
						throw new Exception("One or more Availability fields are empty"); 
					}
					if(Integer.parseInt(textFields.get(i).getText()) > 2400 | Integer.parseInt(textFields.get(i).getText()) < 0){
						throw new Exception("One or more Availability fields have invalid inputs\n Military Time Only"); 
					}
				}
				for(int i = 0; i<7;i++){ 
					file = new File(ScheduleDriver.facility + " Employees.txt");
					scan = new Scanner(file); 
					total = ""; 
					lineSoFar = ""; 
					while(scan.hasNext()){
						line = scan.nextLine(); 
						lineScan = new Scanner(line); 
						lineSoFar = lineScan.next();  
						name = lineScan.next() + " " +  lineScan.next() + " " + lineScan.next(); 
						lineSoFar = lineSoFar +" " + name;
						if(name.equals((String)comboBox.getSelectedItem())){
							if(buttonList.get(i).isSelected()){
								for(int j = 0; j<(i*2);j++){
									if(i!=0){
										lineSoFar = lineSoFar + " " + lineScan.next();
									}
								}
								lineSoFar = lineSoFar +" " +  textFields.get(2*i).getText() + " " + textFields.get(2*i+1).getText(); 
								lineScan.next(); 
								lineScan.next(); 
								while(lineScan.hasNext()){
									lineSoFar = lineSoFar + " " + lineScan.next(); 
								}
								line = lineSoFar;
								textFields.get(2*i).setText(""); 
								textFields.get(2*i+1).setText("");  
							}
						}
						if(scan.hasNext()){
							total = total + line + "\n"; 
						}else{
							total = total + line; 
						}
					}
					out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
					out.print(total); 
					out.close();
				} 
			}catch(IOException w){
				System.out.println("You Suck"); 
				System.exit(0); 
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage()); 
				return; 
			}
		}
	}
}