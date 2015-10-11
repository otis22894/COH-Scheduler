import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class EditEmployeePanel extends JPanel{
	
	private JButton addEmployee;  
	private JButton editAvailability; 
	private JButton editInfo; 
	private JButton removeEmployee; 
	private EmployeeLists employeeList;
	private ArrayList<ArrayList<Shift>> weeklyShifts;
	public static JFrame addEmployeeFrame;
	public static JFrame removeEmployeeFrame;   
	public static JFrame changeAvailabilityFrame;
	public static JFrame changeInfoFrame; 


	public EditEmployeePanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists emplists){
		this.employeeList = emplists; 
		this.weeklyShifts = shiftlist; 
		addEmployee = new JButton("Add Employee"); 
		addEmployee.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame.setDefaultLookAndFeelDecorated(true);
				AddEmployeePanel panel = new AddEmployeePanel(); 
				addEmployeeFrame = new JFrame("Add New Employee");
				addEmployeeFrame.setResizable(false); 
				removeMinMaxClose(addEmployeeFrame); 
				addEmployeeFrame.add(panel); 
				addEmployeeFrame.setVisible(true);
				addEmployeeFrame.setSize(325,325);  
				addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				MenuPanel.myFrame.dispose(); 
				addEmployeeFrame.setLocationRelativeTo(null); 
				JFrame.setDefaultLookAndFeelDecorated(false);
			}
		}); 
		removeEmployee = new JButton("Remove Employee"); 
		removeEmployee.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame.setDefaultLookAndFeelDecorated(true);
				RemoveEmployeePanel panel = new RemoveEmployeePanel(employeeList,weeklyShifts); 
				removeEmployeeFrame = new JFrame("Remove Employee");
				removeEmployeeFrame.setResizable(false);
				removeMinMaxClose(removeEmployeeFrame); 
				removeEmployeeFrame.add(panel);  
				removeEmployeeFrame.setVisible(true); 
				removeEmployeeFrame.setSize(250,150);  
				removeEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				MenuPanel.myFrame.dispose(); 
				removeEmployeeFrame.setLocationRelativeTo(null);
				JFrame.setDefaultLookAndFeelDecorated(false); 
			}
		});
		editAvailability = new JButton("Edit Employee Availability"); 
		editAvailability.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame.setDefaultLookAndFeelDecorated(true);
				ChangeAvailabilityPanel panel = new ChangeAvailabilityPanel(employeeList); 
				changeAvailabilityFrame = new JFrame("Change Employee Availability");
				changeAvailabilityFrame.setResizable(false); 
				removeMinMaxClose(changeAvailabilityFrame); 
				changeAvailabilityFrame.add(panel); 
				changeAvailabilityFrame.setVisible(true);
				changeAvailabilityFrame.setSize(350,325);  
				changeAvailabilityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				MenuPanel.myFrame.dispose(); 
				changeAvailabilityFrame.setLocationRelativeTo(null); 
				JFrame.setDefaultLookAndFeelDecorated(false);
			}
		});
		editInfo = new JButton("Edit Employee Info"); 
		editInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame.setDefaultLookAndFeelDecorated(true);
				EditEmployeeInformationPanel panel = new EditEmployeeInformationPanel(employeeList);
				changeInfoFrame = new JFrame("Change Employee Information"); 
				changeInfoFrame.setResizable(false); 
				removeMinMaxClose(changeInfoFrame); 
				changeInfoFrame.add(panel); 
				changeInfoFrame.setVisible(true); 
				changeInfoFrame.setSize(350,325); 
				changeInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				MenuPanel.myFrame.dispose(); 
				changeInfoFrame.setLocationRelativeTo(null); 
				JFrame.setDefaultLookAndFeelDecorated(false);
			}
		});
		this.add(addEmployee); 
		this.add(removeEmployee); 
		this.add(editAvailability); 
		this.add(editInfo); 
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