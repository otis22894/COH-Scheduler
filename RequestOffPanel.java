import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;

public class RequestOffPanel extends JPanel{
	
	private JTextField start; 
	private JTextField end; 
	private JButton enter;
	private JLabel startLabel; 
	private JLabel endLabel; 
	private JLabel nameLabel;
	private JRadioButton allDay;  
	private JPanel startTimePanel; 
	private JPanel endTimePanel;
	private JPanel namePanel;
	private JCheckBox monday; 
	private JCheckBox tuesday; 
	private JCheckBox wednesday; 
	private JCheckBox thursday; 
	private JCheckBox friday; 
	private JCheckBox saturday;
	private JCheckBox sunday;
	private JPanel checkboxPanel;  
	private JComboBox<String> comboBox;
	private JButton moveForward; 
	public static JFrame frame;  
	private static ArrayList<ArrayList<Shift>> shiftlist;
	private static EmployeeLists employeelist;

	public RequestOffPanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){ 

		this.shiftlist = shiftlist; 
		this.employeelist = employeelist; 

		enter = new JButton("Submit R-Off"); 
		enter.addActionListener(new ButtonListener(shiftlist,employeelist)); 
		start = new JTextField(5); 
		end = new JTextField(5); 
		comboBox = new JComboBox<String>(employeelist.getGuardList()); 
		startLabel = new JLabel("Enter Start Time"); 
		endLabel = new JLabel("End End Time"); 
		nameLabel = new JLabel("Enter Employee Name");
		startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));  
		endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		allDay = new JRadioButton("All Day");

		monday = new JCheckBox("Monday"); 
		tuesday = new JCheckBox("Tuesday"); 
		wednesday = new JCheckBox("Wednesday"); 
		thursday = new JCheckBox("Thursday"); 
		friday = new JCheckBox("Friday"); 
		saturday = new JCheckBox("Saturday"); 
		sunday = new JCheckBox("Sunday"); 
		checkboxPanel = new JPanel(new GridLayout(4,2)); 
		checkboxPanel.add(monday); 
		checkboxPanel.add(friday); 
		checkboxPanel.add(tuesday); 
		checkboxPanel.add(saturday); 
		checkboxPanel.add(wednesday); 
		checkboxPanel.add(sunday); 
		checkboxPanel.add(thursday);

		allDay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(allDay.isSelected()){
					start.setText(""); 
					start.setEditable(false);
					end.setEditable(false);  
					end.setText("");  
				}
				else{
					start.setEditable(true); 
					end.setEditable(true); 
				}
			}
		}); 

		startTimePanel.add(startLabel); 
		startTimePanel.add(start); 
		endTimePanel.add(endLabel); 
		endTimePanel.add(end); 
		namePanel.add(nameLabel); 
		namePanel.add(comboBox); 
		
		this.add(namePanel);
		this.add(allDay);
		this.add(startTimePanel); 
		this.add(endTimePanel); 
		this.add(checkboxPanel);  
		this.add(enter);

		moveForward = new JButton("Continue to Menu"); 
		moveForward.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MenuPanel menuPanel = new MenuPanel(true); 
				OpeningPanel.frame.dispose(); 
			}
		});
		this.add(moveForward);  
	}

	private class ButtonListener implements ActionListener{

		private ArrayList<ArrayList<Shift>> shiftlist;
		private EmployeeLists employeelist;
		private int startTime; 
		private int endTime;
		private ArrayList<Integer> dayList;
		private String choosenGuard; 

		public ButtonListener(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){
			this.shiftlist = shiftlist; 
			this.employeelist = employeelist; 
		}

		public void actionPerformed(ActionEvent e){
			try{
				dayList = new ArrayList<Integer>();
				choosenGuard = (String)comboBox.getSelectedItem(); 
				if(!allDay.isSelected()){ 
					try{
						startTime = Integer.parseInt(start.getText()); 
						endTime = Integer.parseInt(end.getText()); 
					}catch(Exception w){
						throw new Exception("Military Time Only"); 
					}
				}
				if(!monday.isSelected() & !tuesday.isSelected() & !wednesday.isSelected() & !thursday.isSelected() & !friday.isSelected() & !saturday.isSelected() & !sunday.isSelected()){
					throw new Exception("Please select at least one day of the week"); 
				}
				if(monday.isSelected()){
					dayList.add(0); 
				}
				if(tuesday.isSelected()){
					dayList.add(1); 
				}
				if(wednesday.isSelected()){
					dayList.add(2); 
				}
				if(thursday.isSelected()){
					dayList.add(3); 
				}
				if(friday.isSelected()){
					dayList.add(4); 
				}
				if(saturday.isSelected()){
					dayList.add(5); 
				}
				if(sunday.isSelected()){
					dayList.add(6); 
				}
				addGuard(dayList); 
				start.setText(""); 
				end.setText(""); 
				monday.setSelected(false); 
				tuesday.setSelected(false); 
				wednesday.setSelected(false); 
				thursday.setSelected(false); 
				friday.setSelected(false); 
				saturday.setSelected(false); 
				sunday.setSelected(false);
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage());
				start.setText(""); 
				end.setText(""); 
				return; 
			} 
		}

		public void addGuard(ArrayList<Integer> day){ 
			LifeGuard currentGuard = employeelist.getGuard(choosenGuard);
			RequestOffShift shift = new RequestOffShift(currentGuard,startTime,endTime); 
			for(int j = 0; j<day.size(); j++){  
				shiftlist.get(day.get(j)).add(shift); 
			} 
		}
	}

	public static void addGuard(ArrayList<Integer> day,String guardName,int start,int end){ 
		LifeGuard currentGuard = ScheduleDriver.employeeList.getGuard(guardName);
		RequestOffShift shift = new RequestOffShift(currentGuard,start,end); 
		for(int j = 0; j<day.size(); j++){  
			ScheduleDriver.weeklyShifts.get(day.get(j)).add(shift);
		} 
	}
}