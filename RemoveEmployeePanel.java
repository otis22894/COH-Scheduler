import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 
import java.io.*;

public class RemoveEmployeePanel extends JPanel{
	
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> boxModel;  
	private JButton removeEmployee; 
	private JButton goBack; 
	private static EmployeeLists employeeList;
	private static ArrayList<ArrayList<Shift>> shiftList;   

	public RemoveEmployeePanel(EmployeeLists employeeList,ArrayList<ArrayList<Shift>> shiftList){

		this.employeeList = employeeList; 
		this.shiftList = shiftList; 
		MenuPanel.openWindows++; 
		comboBox = new JComboBox<String>(employeeList.getGuardList());
		boxModel = new DefaultComboBoxModel<String>(employeeList.getGuardList());  
		comboBox.setModel(boxModel); 
		removeEmployee = new JButton("Remove Selected Employee"); 
		removeEmployee.addActionListener(new ButtonListener()); 
		goBack = new JButton("Go Back"); 
		goBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				EditEmployeePanel.removeEmployeeFrame.dispose();  
				MenuPanel menuPanel = new MenuPanel(false);  
			}
		}); 
		this.add(comboBox); 
		this.add(removeEmployee); 
		this.add(goBack);  
	}

	private class ButtonListener implements ActionListener{

		private File file; 
		private Scanner scanner;
		private PrintWriter out;
		private String name;
		private String finalName;   

		public void actionPerformed(ActionEvent e){
			try{
				file = new File(ScheduleDriver.facility + " Employees.txt"); 
				scanner = new Scanner(file);
				String total = ""; 
				while(scanner.hasNext()){
					String line = scanner.nextLine(); 
					Scanner lineScan = new Scanner(line); 
					lineScan.next(); 
					name = lineScan.next() + " " + lineScan.next(); 
					if(!name.equals((String)comboBox.getSelectedItem())){
						if(scanner.hasNext()){
							total = total + line + "\n"; 
						}else{
							total = total + line; 
						}
					}else{
						finalName = name; 
					}
				} 
				out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Employees.txt"))); 
				out.print(total); 
				out.close();
				for(int i = 0;i<7;i++){
					for(int j = 0;j<shiftList.get(i).size();j++){
						if(shiftList.get(i).get(j).getLifeGuard().getName().equals(finalName)){
							if(shiftList.get(i).get(j) instanceof TeachingShift){
								ArrayList<Integer> days = new ArrayList<Integer>(); 
								days.add(i); 
								WSIPanel.addGuard(days,finalName,shiftList,employeeList,shiftList.get(i).get(j).getStart(),shiftList.get(i).get(j).getEnd());
							}else if(shiftList.get(i).get(j) instanceof RequestOffShift == false & shiftList.get(i).get(j) instanceof PrivateLessonShift == false){
								ArrayList<Integer> days = new ArrayList<Integer>(); 
								days.add(i); 
								LifeGuardPanel.addGuard(days,finalName,shiftList,employeeList,shiftList.get(i).get(j).getStart(),shiftList.get(i).get(j).getEnd()); 
							}
						}
					}
				}
				JOptionPane.showMessageDialog(null,"Employee " + finalName + " has been deleted");
				boxModel.removeElement(finalName); 
			}catch(IOException w){
				System.out.println("No File"); 
				System.exit(0); 
			}
		}
	}
}