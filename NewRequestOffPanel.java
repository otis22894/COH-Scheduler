import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import java.io.*; 

public class NewRequestOffPanel extends JPanel{

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
	public static JFrame frame;  
	private static ArrayList<ArrayList<Shift>> shiftlist;
	private static EmployeeLists employeelist;
	private JTextField dateField; 
	private JLabel dateLabel; 
	private JPanel datePanel; 

	public NewRequestOffPanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){

		this.shiftlist = shiftlist; 
		this.employeelist = employeelist; 

		enter = new JButton("Submit R-Off"); 
		enter.addActionListener(new ButtonListener()); 
		start = new JTextField(5); 
		end = new JTextField(5); 
		dateField = new JTextField(5); 
		comboBox = new JComboBox<String>(employeelist.getGuardList()); 
		startLabel = new JLabel("Enter Start Time"); 
		endLabel = new JLabel("End End Time"); 
		nameLabel = new JLabel("Select Employee Name");
		dateLabel = new JLabel("<html>Enter R-Off Date: Any one of the <br /> dates if multiple (MMDDYY)</html>"); 
		startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));  
		endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		namePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		datePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		allDay = new JRadioButton("All Day"); 
		allDay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(allDay.isSelected()){
					start.setText(""); 
					start.setEditable(false); 
					end.setText(""); 
					end.setEditable(false); 
				}
				else{
					start.setEditable(true); 
					end.setEditable(true); 
				}
			}
		}); 

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

		startTimePanel.add(startLabel); 
		startTimePanel.add(start); 
		endTimePanel.add(endLabel); 
		endTimePanel.add(end); 
		namePanel.add(nameLabel); 
		namePanel.add(comboBox); 
		datePanel.add(dateLabel); 
		datePanel.add(dateField); 
		
		this.add(namePanel);
		this.add(allDay);
		this.add(startTimePanel); 
		this.add(endTimePanel); 
		this.add(datePanel); 
		this.add(checkboxPanel);  
		this.add(enter);
	}

	private class ButtonListener implements ActionListener{

		private int startTime; 
		private int endTime;
		private ArrayList<Integer> dayList;
		private String line; 
		private PrintWriter out; 

		public void actionPerformed(ActionEvent e){
			try{
				line = (String)comboBox.getSelectedItem(); 
				if(!allDay.isSelected()){ 
					if(start.getText().length() == 0 | end.getText().length() == 0){
						throw new Exception("One or more input fields are empty"); 
					}
					if(Integer.parseInt(start.getText()) >2400 | Integer.parseInt(start.getText()) < 0 | Integer.parseInt(end.getText()) > 2400 | Integer.parseInt(end.getText())< 0){
						throw new Exception("Invalid input format\n Military time only"); 
					}
					startTime = Integer.parseInt(start.getText()); 
					endTime = Integer.parseInt(end.getText()); 
				}else{
					startTime = 0; 
					endTime = 0; 
				}
				if(dateField.getText().length()!=6){
					throw new Exception("Invalid Date Length"); 
				}
				line = line + " " + dateField.getText().substring(4) + " " + dateField.getText().substring(0,2) + " " + dateField.getText().substring(2,4) + " " + startTime + " " + endTime; 
				GregorianCalendar date = new GregorianCalendar(Integer.parseInt(dateField.getText().substring(4)),Integer.parseInt(dateField.getText().substring(0,2))-1,Integer.parseInt(dateField.getText().substring(2,4))); 
				LifeGuard currentGuard = employeelist.getGuard((String)comboBox.getSelectedItem()); 
				ArrayList<Integer> day = new ArrayList<Integer>(); 
				if(monday.isSelected()){
					line = line + " M"; 
					day.add(0); 
				}
				if(tuesday.isSelected()){
					line = line + " T"; 
					day.add(1); 
				}
				if(wednesday.isSelected()){
					line = line + " W";
					day.add(2); 
				}
				if(thursday.isSelected()){
					line = line + " Th";
					day.add(3);  
				}
				if(friday.isSelected()){
					line = line + " F"; 
					day.add(4); 
				}
				if(saturday.isSelected()){
					line = line + " S";
					day.add(5);  
				}
				if(sunday.isSelected()){
					line = line + " Su"; 
					day.add(6); 
				}
				if(date.before(OpeningPanel.endDate) & date.after(OpeningPanel.startDate)){
					for(int k = 0; k < day.size(); k++){
					for(int i = 0;i<7;i++){
						for(int j = 0;j<shiftlist.get(i).size();j++){
							if(shiftlist.get(i).get(j).getLifeGuard().getName().equals(currentGuard.getName()) & i==day.get(k)){
								if(shiftlist.get(i).get(j) instanceof TeachingShift){
									ArrayList<Integer> days = new ArrayList<Integer>(); 
									days.add(i); 
									WSIPanel.addGuard(days,currentGuard.getName(),shiftlist,employeelist,shiftlist.get(i).get(j).getStart(),shiftlist.get(i).get(j).getEnd());
								}else if(shiftlist.get(i).get(j) instanceof RequestOffShift == false & shiftlist.get(i).get(j) instanceof PrivateLessonShift == false){
									ArrayList<Integer> days = new ArrayList<Integer>(); 
									days.add(i); 
									LifeGuardPanel.addGuard(days,currentGuard.getName(),shiftlist,employeelist,shiftlist.get(i).get(j).getStart(),shiftlist.get(i).get(j).getEnd()); 
								}
								shiftlist.get(i).remove(shiftlist.get(i).get(j)); 
							}
						}
					}
					}
					RequestOffShift shift = new RequestOffShift(currentGuard,startTime,endTime); 
					for(int j = 0; j<day.size(); j++){  
						shiftlist.get(day.get(j)).add(shift);
						System.out.println(shift);  
					}
				}
				if(!monday.isSelected() & !tuesday.isSelected() & !wednesday.isSelected() & !thursday.isSelected() & !friday.isSelected() & !saturday.isSelected()){
					throw new Exception("Please select at least one day of the week"); 
				}
				try{
					out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " RequestOffSheet.txt", true))); 
					BufferedReader br = new BufferedReader(new FileReader(ScheduleDriver.facility + " RequestOffSheet.txt"));
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
				start.setText(""); 
				end.setText(""); 
				dateField.setText(""); 
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
				dateField.setText(""); 
				return; 
			}
		}
	}
}