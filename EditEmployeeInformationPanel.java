import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 
import java.io.*; 

public class EditEmployeeInformationPanel extends JPanel{

	private JComboBox<String> comboBox; 
	private JLabel nameLabel; 
	private JTextField nameField; 
	private JLabel nameLablel;
	private JRadioButton wsiButton; 
	private JRadioButton lifeGuardButton; 
	private JLabel changeEmployeeType; 
	private ButtonGroup buttonGroup; 
	private JRadioButton activateNameChange;  
	private JRadioButton activateTypeChange; 
	private JButton makeChanges; 
	private JButton goBack; 

	public EditEmployeeInformationPanel(EmployeeLists employeeList){

		MenuPanel.openWindows++; 

		nameLabel = new JLabel("Please choose employee"); 
		comboBox = new JComboBox<String>(employeeList.getGuardList()); 
		this.add(nameLabel); 
		this.add(comboBox); 

		nameLabel = new JLabel("Change Employee Name To:");
		nameField = new JTextField(5);
		activateNameChange = new JRadioButton("Activate"); 
		nameField.setEditable(false); 
		activateNameChange.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(activateNameChange.isSelected()){
					nameField.setEditable(true); 
				}else{
					nameField.setEditable(false); 
				}
			}
		}); 
		this.add(nameLabel); 
		this.add(nameField);
		this.add(activateNameChange);  

		changeEmployeeType = new JLabel("Change Emp To:");
		buttonGroup = new ButtonGroup();  
		wsiButton = new JRadioButton("WSI"); 
		wsiButton.setEnabled(false); 
		lifeGuardButton = new JRadioButton("LifeGuard");
		lifeGuardButton.setEnabled(false); 
		activateTypeChange = new JRadioButton("Activate"); 
		activateTypeChange.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(activateTypeChange.isSelected()){
					lifeGuardButton.setEnabled(true); 
					wsiButton.setEnabled(true); 
				}else{
					lifeGuardButton.setEnabled(false); 
					wsiButton.setEnabled(false); 
				}
			}
		}); 

		buttonGroup.add(wsiButton); 
		buttonGroup.add(lifeGuardButton); 
		this.add(changeEmployeeType); 
		this.add(wsiButton); 
		this.add(lifeGuardButton);
		this.add(activateTypeChange);  

		makeChanges = new JButton("Submit Changes"); 
		makeChanges.addActionListener(new ButtonListener()); 
		this.add(makeChanges);

		goBack = new JButton("Go Back"); 
		goBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				EditEmployeePanel.changeInfoFrame.dispose();  
				MenuPanel menuPanel = new MenuPanel(false); 
			}
		});
		this.add(goBack); 


	}

	private class ButtonListener implements ActionListener{

		private File file; 
		private PrintWriter out;
		private Scanner scan; 
		private Scanner lineScan;
		private String line; 
		private String lineSoFar; 
		private String total;
		private String name; 
		private String choosenName;  
		private String type; 


		public void actionPerformed(ActionEvent e){
			try{
				if(!activateNameChange.isSelected() & !activateTypeChange.isSelected()){
					throw new Exception("Please select at least one value to change"); 
				}
				if(activateNameChange.isSelected()){
					try{
						if(nameField.getText().length() == 0){
							throw new Exception("Please enter a name; or deactivate row"); 
						}
						file = new File(ScheduleDriver.facility + " Employees.txt"); 
						scan = new Scanner(file);
						total = ""; 
						lineSoFar = ""; 
						while(scan.hasNext()){
							line = scan.nextLine(); 
							lineScan = new Scanner(line);
							lineSoFar = lineScan.next() + " "; 
							name = lineScan.next() +" "+ lineScan.next();
							choosenName = (String)comboBox.getSelectedItem();   
							if(choosenName.equals(name)){
								lineSoFar = lineSoFar + nameField.getText(); 
							}else{
								lineSoFar = lineSoFar + name; 
							}
							while(lineScan.hasNext()){
								lineSoFar = lineSoFar +" " + lineScan.next(); 
							}
							line = lineSoFar; 
							if(scan.hasNext()){
								total = total + line + "\n"; 
							}else{
								total = total + line; 
							}
						}
						out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
						out.print(total); 
						out.close();
						nameField.setText(""); 
					}catch(IOException w){
						System.out.println("You Suck"); 
						System.exit(0); 
					} 
				}
				if(activateTypeChange.isSelected()){
					try{
						if(!wsiButton.isSelected() & !lifeGuardButton.isSelected()){
							throw new Exception("Please select type to change to; or deactivate row");
						}
						file = new File(ScheduleDriver.facility + " Employees.txt"); 
						scan = new Scanner(file);
						total = ""; 
						lineSoFar = ""; 
						while(scan.hasNext()){
							line = scan.nextLine(); 
							lineScan = new Scanner(line);
							type = lineScan.next(); 
							name = lineScan.next() + " " + lineScan.next(); 
							if(name.equals((String)comboBox.getSelectedItem())){
								if(wsiButton.isSelected()){
									lineSoFar = "W " + name;
								}else if(lifeGuardButton.isSelected()){
									lineSoFar = "L " + name; 
								}
							}else{
								lineSoFar = type + " " + name; 
							}
							while(lineScan.hasNext()){
								lineSoFar = lineSoFar +" " + lineScan.next(); 
							}
							line = lineSoFar; 
							if(scan.hasNext()){
								total = total + line + "\n"; 
							}else{
								total = total + line; 
							}
						}
						out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
						out.print(total); 
						out.close();
						nameField.setText(""); 
					}catch(IOException w){
						System.out.println("You Suck"); 
						System.exit(0); 
					}
				}
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage()); 
				return; 
			}
		}
	}
}