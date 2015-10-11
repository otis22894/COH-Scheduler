import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;  

public class LifeGuardPanel extends JPanel{
	
	private JTextField start; 
	private JTextField end; 
	private JTextField quantity; 
	private JButton getGuards; 
	private JLabel startLabel; 
	private JLabel endLabel; 
	private JLabel guardLabel;
	private JCheckBox monday; 
	private JCheckBox tuesday; 
	private JCheckBox wednesday; 
	private JCheckBox thursday; 
	private JCheckBox friday; 
	private JCheckBox saturday;
	private JCheckBox sunday;
	private JPanel checkboxPanel; 
	private JPanel startTimePanel; 
	private JPanel endTimePanel;
	private JPanel numberPanel;
	private JRadioButton lifeGuardsOnly; 
	private JRadioButton lifeGuardsandWSI; 
	private JPanel checkboxPanel2; 
	private ButtonGroup buttonGroup; 

	public LifeGuardPanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){
		final EmployeeLists emplist = employeelist; 
		final ArrayList<ArrayList<Shift>> list = shiftlist; 
		start = new JTextField(5); 
		end = new JTextField(5); 
		quantity = new JTextField(5); 
		getGuards = new JButton("Enter"); 
		startLabel = new JLabel("Enter Shift Start Time Here");
		endLabel = new JLabel("Enter Shift End Time Here"); 
		guardLabel = new JLabel("Enter the Number of Guards Here");  
		getGuards.addActionListener(new ButtonListener(shiftlist,employeelist)); 
		startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		numberPanel = new JPanel(new FlowLayout(FlowLayout.LEADING)); 
		
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

		lifeGuardsandWSI = new JRadioButton("Lifeguards and WSI",true); 
		lifeGuardsOnly = new JRadioButton("Lifeguards Only",false);
		checkboxPanel2 = new JPanel(new GridLayout(0,1)); 
		buttonGroup = new ButtonGroup(); 
		buttonGroup.add(lifeGuardsOnly); 
		buttonGroup.add(lifeGuardsandWSI); 
		checkboxPanel2.add(lifeGuardsOnly);  
		checkboxPanel2.add(lifeGuardsandWSI); 

		startTimePanel.add(startLabel); 
		startTimePanel.add(start);
		endTimePanel.add(endLabel); 
		endTimePanel.add(end); 
		numberPanel.add(guardLabel); 
		numberPanel.add(quantity);  
		
		this.add(startTimePanel); 
		this.add(endTimePanel); 
		this.add(numberPanel); 
		this.add(checkboxPanel2); 
		this.add(checkboxPanel); 
		this.add(getGuards); 
	}

	private class ButtonListener implements ActionListener{

		private ArrayList<ArrayList<Shift>> shiftlist; 
		private EmployeeLists employeelist;
		private int startTime; 
		private int endTime; 
		private int number; 
		private ArrayList<Integer> dayList;  

		public ButtonListener(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist){ 
			this.shiftlist = shiftlist; 
			this.employeelist = employeelist;  
		}

		public void actionPerformed(ActionEvent e){
			try{
				dayList = new ArrayList<Integer>();
				if(start.getText().length() == 0 | end.getText().length() == 0 | quantity.getText().length() == 0){
					throw new Exception("One or more input fields are empty"); 
				}
				if(Integer.parseInt(start.getText()) >2400 | Integer.parseInt(start.getText()) < 0 | Integer.parseInt(end.getText()) > 2400 | Integer.parseInt(end.getText())< 0 | Integer.parseInt(quantity.getText())<0){
					throw new Exception("Invalid input format\n Military time and positive integers only"); 
				} 
				startTime = Integer.parseInt(start.getText()); 
				endTime = Integer.parseInt(end.getText()); 
				number = Integer.parseInt(quantity.getText());
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
				quantity.setText(""); 
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
				quantity.setText(""); 
				return; 
			}
		}

		public void addGuard(ArrayList<Integer> day){
			LifeGuard currentGuard; 
			Shift shift; 
			for (int i = 1; i<=number; i++){
				for(int j = 0; j<day.size(); j++){
					if(lifeGuardsandWSI.isSelected()){
						do{
							currentGuard = employeelist.getRandomGuard();
							shift = new Shift(currentGuard,startTime,endTime);
						}
						while(currentGuard.getHours() > (employeelist.getAverageHours()) | !currentGuard.availability[day.get(j)].canWork(startTime,endTime) | (alreadyWorking(shiftlist,currentGuard,shift,day.get(j),startTime,endTime)));
					}else{
						do{
							currentGuard = employeelist.getRandomGuard();
							shift = new Shift(currentGuard,startTime,endTime);
						}
						while(currentGuard.getHours() > (employeelist.getAverageHours()) |!currentGuard.availability[day.get(j)].canWork(startTime,endTime) | (alreadyWorking(shiftlist,currentGuard,shift,day.get(j),startTime,endTime)) | currentGuard instanceof WSI);
					}
					currentGuard.giveHours((endTime-startTime)/100); 
					shiftlist.get(day.get(j)).add(shift); 
				} 
			}
		}
	}

	public static void addGuard(ArrayList<Integer> day,String guardName,ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists employeelist,int startTime,int endTime){
		LifeGuard currentGuard; 
		Shift shift;
		for(int j = 0; j<day.size(); j++){
			do{
				currentGuard = employeelist.getRandomGuard();
				shift = new Shift(currentGuard,startTime,endTime);
			}
			while(guardName.equals(currentGuard.getName()) | currentGuard.getHours() > (employeelist.getAverageHours()) |!currentGuard.availability[day.get(j)].canWork(startTime,endTime) | (alreadyWorking(shiftlist,currentGuard,shift,day.get(j),startTime,endTime)) | currentGuard instanceof WSI);
			currentGuard.giveHours((endTime-startTime)/100); 
			shiftlist.get(day.get(j)).add(shift); 
		}
	}

	public static boolean alreadyWorking(ArrayList<ArrayList<Shift>> shiftlist,LifeGuard currentGuard,Shift shift, int day,int startTime,int endTime){
		for(int i = 0; i<shiftlist.get(day).size(); i++){
			if(shiftlist.get(day).get(i).getLifeGuard().getName().equals(currentGuard.getName())){
				return true; 
			}
		}
		return false; 
	}
}