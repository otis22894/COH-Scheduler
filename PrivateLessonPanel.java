import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class PrivateLessonPanel extends JPanel{
	
	private JTextField start; 
	private JTextField end; 
	private JButton getGuards; 
	private JLabel startLabel; 
	private JLabel endLabel; 
	private JCheckBox monday; 
	private JCheckBox tuesday; 
	private JCheckBox wednesday; 
	private JCheckBox thursday; 
	private JCheckBox friday; 
	private JCheckBox saturday;
	private JCheckBox sunday; 
	private JPanel checkboxPanel;
	private JComboBox<String> comboBox;  
	private JLabel guardLabel; 
	private JPanel startTimePanel; 
	private JPanel endTimePanel; 
	private JPanel numberPanel; 
	private JRadioButton random; 
	private Object[] options = {"Replace their shifts","Cancel"};

	public PrivateLessonPanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){
		start = new JTextField(5); 
		end = new JTextField(5); 
		getGuards = new JButton("Enter"); 
		getGuards.addActionListener(new ButtonListener(shiftlist,employeelist)); 
		startLabel = new JLabel("Enter Shift Start Time Here");
		endLabel = new JLabel("Enter Shift End Time Here"); 
		guardLabel = new JLabel("Choose Guard"); 
		startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		numberPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		comboBox = new JComboBox<String>(employeelist.getWSIList());
		random = new JRadioButton("Random"); 
		random.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(random.isSelected()){
					comboBox.setEnabled(false); 
				}else{
					comboBox.setEnabled(true); 
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
		numberPanel.add(guardLabel);
		numberPanel.add(comboBox); 
		numberPanel.add(random); 
		
		this.add(startTimePanel); 
		this.add(endTimePanel); 
		this.add(numberPanel); 
		this.add(checkboxPanel); 
		this.add(getGuards); 
	}

	private class ButtonListener implements ActionListener{

		private ArrayList<ArrayList<Shift>> shiftlist; 
		private EmployeeLists employeelist;
		private int startTime; 
		private int endTime;  
		private ArrayList<Integer> dayList;  

		public ButtonListener(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){ 
			this.shiftlist = shiftlist; 
			this.employeelist = employeelist;
		}

		public void actionPerformed(ActionEvent e){
			try{
				dayList = new ArrayList<Integer>(); 
				if(start.getText().length() == 0 | end.getText().length() == 0){
					throw new Exception("One or more input fields are empty"); 
				}
				if(Integer.parseInt(start.getText()) >2400 | Integer.parseInt(start.getText()) < 0 | Integer.parseInt(end.getText()) > 2400 | Integer.parseInt(end.getText())< 0){
					throw new Exception("Invalid input format\n Military time only"); 
				} 
				startTime = Integer.parseInt(start.getText()); 
				endTime = Integer.parseInt(end.getText()); 
				if(!monday.isSelected() & !tuesday.isSelected() & !wednesday.isSelected() & !thursday.isSelected() & !friday.isSelected() & !saturday.isSelected()){
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
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage());
				start.setText(""); 
				end.setText("");
				return; 
			}
		}

		public void addGuard(ArrayList<Integer> day){
			LifeGuard currentGuard; 
			int count; 
			boolean check; 
			PrivateLessonShift shift; 
			if (random.isSelected()){
				do{
					count = 0;
					check = true; 
					do{ 
						currentGuard = employeelist.getRandomGuard();
						shift = new PrivateLessonShift(currentGuard,startTime,endTime);
					}while(currentGuard.getHours() > (employeelist.getAverageHours()) | currentGuard instanceof WSI == false);
					for(int k = 0; k < day.size(); k++){
						if(currentGuard.availability[day.get(k)].canWork(startTime,endTime) & !(alreadyWorking(shiftlist,currentGuard,shift,day.get(k),startTime,endTime))){
							count++;
						}
					}
					if(count == day.size()){
						check = false; 
					}
				}while(check);
			}else{
				currentGuard = employeelist.getGuard((String)comboBox.getSelectedItem());
				shift = new PrivateLessonShift(currentGuard,startTime,endTime); 
				for(int k = 0;k<day.size(); k++){
					if(alreadyWorking(shiftlist,currentGuard,shift,day.get(k),startTime,endTime)){
						int n = JOptionPane.showOptionDialog(null,(String)comboBox.getSelectedItem()+" is already working, would you like \n to replace their shifts, or cancel?",
							"Scheduling Conflict",JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,null,options,null);
						if(n==JOptionPane.YES_OPTION){
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
							for(int j = 0; j<day.size(); j++){
								currentGuard.giveHours((endTime-startTime)/100);  
								shiftlist.get(day.get(j)).add(shift); 
							} 
						}
						break; 
					}
				}
			}
		}

		public boolean alreadyWorking(ArrayList<ArrayList<Shift>> shiftlist,LifeGuard currentGuard,Shift shift, int day,int startTime,int endTime){
			for(int i = 0; i<shiftlist.get(day).size(); i++){
				if(shiftlist.get(day).get(i).getLifeGuard().getName().equals(currentGuard.getName())){
					//return true; 
					if(shiftlist.get(day).get(i).alreadyWorking(startTime,endTime)){
						return true; 
					}
				}
			}
			return false;
		}
	}
}