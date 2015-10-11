import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 

public class OpeningPanel extends JPanel{
	
	private JLabel welcomeLabel;
	private JTextField startDateField; 
	private JTextField endDateField; 
	private JLabel startDateLabel; 
	private JLabel endDateLabel;
	public static GregorianCalendar startDate; 
	public static GregorianCalendar endDate;
	private JButton no;
	public static JFrame frame;

	public OpeningPanel(){

		welcomeLabel = new JLabel("Welcome to COH Schedule Maker"); 
		welcomeLabel.setForeground(Color.BLUE); 
		startDateField = new JTextField(5); 
		endDateField = new JTextField(5); 
		startDateLabel = new JLabel("Enter Start Date Here: (MMDDYY) - Monday"); 
		endDateLabel = new JLabel("End End Date Here: (MMDDYY) - Sunday");
		no = new JButton("Continue");
		no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					try{
						if(startDateField.getText().length()!=6 | endDateField.getText().length()!=6){
							throw new Exception("Invalid Date Length"); 
						}
						startDate = new GregorianCalendar(Integer.parseInt(startDateField.getText().substring(4)),Integer.parseInt(startDateField.getText().substring(0,2))-1,Integer.parseInt(startDateField.getText().substring(2,4)));
						endDate = new GregorianCalendar(Integer.parseInt(endDateField.getText().substring(4)),Integer.parseInt(endDateField.getText().substring(0,2))-1,Integer.parseInt(endDateField.getText().substring(2,4))); 
						long start = startDate.getTimeInMillis(); 
						long end = endDate.getTimeInMillis(); 
						if((end-start) / (24 * 60 * 60 * 1000) !=6){
							throw new Exception("7 Day Gap Required"); 
						}
					}catch(Exception w){
						JOptionPane.showMessageDialog(null,"Invalid date format, please try again\nReason: " + w.getMessage()); 
						startDateField.setText(""); 
						endDateField.setText("");
						return;
					}
				ScheduleDriver.getRequestOffs();
				ScheduleDriver.openingFrame.dispose();
				MenuPanel menuPanel = new MenuPanel(true);
			}
		});
		this.add(welcomeLabel);
		this.add(startDateLabel);
		this.add(startDateField);
		this.add(endDateLabel);
		this.add(endDateField); 
		this.add(no); 
	}
}