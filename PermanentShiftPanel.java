import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.io.*; 

public class PermanentShiftPanel extends JPanel{
	
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> boxModel; 
	private JLabel explination;  
	private JRadioButton remove; 
	private JRadioButton add; 
	private JRadioButton lifeGuard; 
	private JRadioButton wsi; 
	private ButtonGroup buttonGroup;
	private ButtonGroup buttonGroup2;  
	private JTextField startTime; 
	private JTextField endTime; 
	private JTextField number; 
	private JCheckBox monday; 
	private JCheckBox tuesday; 
	private JCheckBox wednesday; 
	private JCheckBox thursday; 
	private JCheckBox friday; 
	private JCheckBox saturday;
	private JCheckBox sunday;
	private JPanel checkboxPanel;
	private JLabel startLabel; 
	private JLabel endLabel;
	private JLabel numberLabel;  
	private JButton enter;  

	public PermanentShiftPanel(){

		comboBox = new JComboBox<String>(MenuPanel.permanentShifts.toArray(new String[0]));
		boxModel = new DefaultComboBoxModel<String>(MenuPanel.permanentShifts.toArray(new String[0])); 
		comboBox.setModel(boxModel);  
		remove = new JRadioButton("Remove Permanent Shift"); 
		add = new JRadioButton("Add Permanent Shift");
		buttonGroup = new ButtonGroup(); 
		buttonGroup.add(add); 
		buttonGroup.add(remove); 
		explination = new JLabel("<html>Use this tab to add and remove permaent shifts <br /> Permanent Shifts are those that are added to the <br />schedule automaticaly</html>");  
		
		startTime = new JTextField(10); 
		endTime = new JTextField(10); 
		number = new JTextField(10);
		wsi = new JRadioButton("WSI Shift"); 
		lifeGuard = new JRadioButton("LifeGuard Shift"); 
		buttonGroup2 = new ButtonGroup(); 
		buttonGroup2.add(wsi); 
		buttonGroup2.add(lifeGuard); 

		startLabel = new JLabel("Enter Shift Start Time Here     "); 
		endLabel = new JLabel("Enter Shift End Time Here"); 
		numberLabel = new JLabel("Enter Number of Employees");  

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

		enter = new JButton("Enter");
		enter.addActionListener(new ButtonListener());  

		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(add.isSelected()){
					comboBox.setEnabled(false); 
					startTime.setEditable(true); 
					endTime.setEditable(true);
					monday.setEnabled(true); 
					number.setEditable(true); 
					monday.setEnabled(true); 
					tuesday.setEnabled(true); 
					wednesday.setEnabled(true); 
					thursday.setEnabled(true); 
					friday.setEnabled(true); 
					saturday.setEnabled(true); 
					sunday.setEnabled(true);
					wsi.setEnabled(true); 
					lifeGuard.setEnabled(true); 
				}
			}
		});
		remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(remove.isSelected()){
					comboBox.setEnabled(true); 
					startTime.setEditable(false); 
					endTime.setEditable(false);
					number.setEditable(false); 
					monday.setEnabled(false);
					tuesday.setEnabled(false); 
					wednesday.setEnabled(false); 
					thursday.setEnabled(false); 
					friday.setEnabled(false); 
					saturday.setEnabled(false); 
					sunday.setEnabled(false); 
					wsi.setEnabled(false); 
					lifeGuard.setEnabled(false);  
				}
			}
		});

		this.add(explination); 
		this.add(add); 
		this.add(remove); 
		this.add(comboBox); 
		this.add(startLabel); 
		this.add(startTime);
		this.add(endLabel);  
		this.add(endTime);
		this.add(numberLabel);  
		this.add(number); 
		this.add(wsi); 
		this.add(lifeGuard); 
		this.add(checkboxPanel);
		this.add(enter); 
	}

	private class ButtonListener implements ActionListener{

		private File file; 
		private Scanner scanner; 
		private PrintWriter out; 

		public void actionPerformed(ActionEvent e){
			try{
				if(add.isSelected()){
					String temp = ""; 
					String temp2 = ""; 
					if(wsi.isSelected()){
						temp = "T"; 
					}else if(lifeGuard.isSelected()){
						temp = "L"; 
					}else{
						throw new Exception("Select type of shift (WSI/LifeGuard)"); 
					}
					if(!monday.isSelected() & !tuesday.isSelected() & !wednesday.isSelected() & !thursday.isSelected() & !friday.isSelected() & !saturday.isSelected() & !sunday.isSelected()){
						throw new Exception("Please select at least one day of the week"); 
					}
					if(monday.isSelected()){
						temp = temp + " M"; 
						temp2 = temp2 + " Mon"; 
					}
					if(tuesday.isSelected()){
						temp = temp + " T"; 
						temp2 = temp2 + " Tues"; 
					}
					if(wednesday.isSelected()){
						temp = temp + " W"; 
						temp2 = temp2 + " Wed"; 
					}
					if(thursday.isSelected()){
						temp = temp + " Th";
						temp2 = temp2 + " Thurs";  
					}
					if(friday.isSelected()){
						temp = temp + " F"; 
						temp2 = temp2 + " Fri"; 
					}
					if(saturday.isSelected()){
						temp = temp + " S";
						temp2 = temp2 + " Sat";  
					}
					if(sunday.isSelected()){
						temp = temp + " Su"; 
						temp2 = temp2 + " Sun"; 
					}
					try{
						out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Shifts.txt", true)));
						if(startTime.getText().length() == 0 | endTime.getText().length() == 0 | number.getText().length() == 0){
							throw new Exception("One or more input fields are empty"); 
						}
						if(Integer.parseInt(startTime.getText()) >2400 | Integer.parseInt(startTime.getText()) < 0 | Integer.parseInt(endTime.getText()) > 2400 | Integer.parseInt(endTime.getText())< 0 | Integer.parseInt(number.getText())<0){
							throw new Exception("Invalid input format\n Military time and positive integers only"); 
						}
						BufferedReader br = new BufferedReader(new FileReader(ScheduleDriver.facility + " Shifts.txt"));
						if (br.readLine() != null) {
							out.print("\n" + startTime.getText() + " " + endTime.getText() + " " + number.getText() + " " + temp);
						}else{ 
							out.print(startTime.getText() + " " + endTime.getText() + " " + number.getText() + " " + temp);
						}
						out.close();
						boxModel.addElement(startTime.getText() + "-" + endTime.getText() + " " + temp2); 
						JOptionPane.showMessageDialog(null,"Shift has been added, to impliment, please restart program");
					}catch(IOException w){
						System.out.println("Failure"); 
						System.exit(0); 
					}catch(Exception w){
						throw new Exception(w.getMessage()); 
					}
					startTime.setText(""); 
					endTime.setText(""); 
					number.setText(""); 
					monday.setSelected(false); 
					tuesday.setSelected(false); 
					wednesday.setSelected(false); 
					thursday.setSelected(false); 
					friday.setSelected(false); 
					saturday.setSelected(false); 
					sunday.setSelected(false); 
				}else if(remove.isSelected()){
					try{
						file = new File(ScheduleDriver.facility + " Shifts.txt"); 
						scanner = new Scanner(file); 
						String total = ""; 
						while(scanner.hasNext()){
							String line = scanner.nextLine(); 
							String comboBoxStartTime = ((String)comboBox.getSelectedItem()).substring(0,((String)comboBox.getSelectedItem()).indexOf("-"));  
							String comboBoxEndTime = ((String)comboBox.getSelectedItem()).substring(((String)comboBox.getSelectedItem()).indexOf("-") + 1,((String)comboBox.getSelectedItem()).indexOf(" "));
							String fileStartTime = line.substring(0,4);
							String fileEndTime = line.substring(5,9); 
							if(!(Integer.parseInt(comboBoxStartTime) == Integer.parseInt(fileStartTime)) | !(Integer.parseInt(comboBoxEndTime) == Integer.parseInt(fileEndTime))){
								if(scanner.hasNext()){
									total = total + line + "\n"; 
								}else{
									total = total + line; 
								}
							}
						}
						out = new PrintWriter(new BufferedWriter(new FileWriter(ScheduleDriver.facility + " Shifts.txt"))); 
						out.print(total); 
						out.close();
						boxModel.removeElement((String)comboBox.getSelectedItem());
						JOptionPane.showMessageDialog(null,"Shift has been removed, to impliment, please restart program"); 
					}catch(IOException w){
						System.out.println("No File!"); 
						System.exit(0); 
					}
				}else{
					throw new Exception("Select to add or remove permanent shift");
				}
			}catch(Exception w){
				JOptionPane.showMessageDialog(null,"Invalid Request, please try again\nReason: " + w.getMessage());
				startTime.setText(""); 
				endTime.setText(""); 
				number.setText(""); 
				return; 
			} 
		}
	}
}