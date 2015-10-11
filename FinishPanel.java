import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class FinishPanel extends JPanel{
	
	private JButton showSchedule;
	private JButton showHours;  
	private JButton showScheduleByPerson;
	private JButton budgetPanel; 
	private EmployeeLists employeeList;
	private ArrayList<ArrayList<Shift>> weeklyShifts;
	private JScrollPane scrollPane; 

	public FinishPanel(ArrayList<ArrayList<Shift>> shiftlist,EmployeeLists emplists){

		weeklyShifts = shiftlist;
		employeeList = emplists; 
		showSchedule = new JButton("Show Schedule By Day");
		showSchedule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SchedulePanel panel = new SchedulePanel(weeklyShifts); 
				JFrame newframe = new JFrame("Schedule Viewer");
				scrollPane = new JScrollPane(panel);
				newframe.add(scrollPane);  
				newframe.setVisible(true);  
				newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				newframe.pack();
				newframe.setLocationRelativeTo(null);
			}
		}); 
		showHours = new JButton("Show Hours"); 
		showHours.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				HoursPanel panel = new HoursPanel(employeeList); 
				JFrame newframe = new JFrame("Hours");
				scrollPane = new JScrollPane(panel); 
				newframe.add(scrollPane); 
				newframe.setVisible(true); 
				newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				newframe.setSize(200,200); 
				newframe.setLocationRelativeTo(null); 
			}
		});
		showScheduleByPerson = new JButton("Show Schedule By Person"); 
		showScheduleByPerson.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ScheduleByNamePanel panel = new ScheduleByNamePanel(weeklyShifts,employeeList); 
				JFrame newframe = new JFrame("Schedule Viewer");
				newframe.setLayout(new BorderLayout()); 
				scrollPane = new JScrollPane(panel);
				scrollPane.setPreferredSize(new Dimension(50,50));   
				newframe.add(scrollPane,BorderLayout.CENTER);  
				newframe.setVisible(true);  
				newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				newframe.setSize(900,500); 
				newframe.setLocationRelativeTo(null);  
			}
		});
		budgetPanel = new JButton("Show Schedule Budget");
		budgetPanel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BudgetPanel panel = new BudgetPanel(employeeList); 
				JFrame newframe = new JFrame("Budget Viewer"); 
				newframe.add(panel); 
				newframe.setVisible(true); 
				newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				newframe.setLocationRelativeTo(null); 
				newframe.pack(); 
			}
		});
		this.add(showSchedule);
		this.add(showHours);  
		this.add(showScheduleByPerson); 
		this.add(budgetPanel); 
	}
}