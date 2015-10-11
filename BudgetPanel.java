import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat; 

public class BudgetPanel extends JPanel{

	public static double LIFEGUARDPAY = 8.57; 
	public static double WSIPAY = 9.67; 

	private EmployeeLists employeeList; 
	private JLabel lifeGuardLabel; 
	private JLabel wsiLabel;
	private JLabel totalLabel;  

	private double lifeGuardPayTotal; 
	private double wsiPayTotal; 

	private NumberFormat numformat; 

	public BudgetPanel(EmployeeLists employees){


		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		numformat = NumberFormat.getCurrencyInstance(); 

		this.employeeList = employees; 
		lifeGuardPayTotal = 0.0; 
		wsiPayTotal = 0.0; 

		for(int i = 0; i<employeeList.lifeGuardList.size();i++){
			if(employeeList.lifeGuardList.get(i) instanceof WSI){
				wsiPayTotal += employeeList.lifeGuardList.get(i).getHours() * WSIPAY; 
			}else{
				lifeGuardPayTotal += employeeList.lifeGuardList.get(i).getHours() * LIFEGUARDPAY; 
			}
		}

		lifeGuardLabel = new JLabel("Total pay for lifeguards: " + numformat.format(lifeGuardPayTotal)); 
		wsiLabel = new JLabel("Total pay for WSI's " + numformat.format(wsiPayTotal)); 
		totalLabel = new JLabel("Total pay for this schedule: " + numformat.format(lifeGuardPayTotal + wsiPayTotal));

		this.add(lifeGuardLabel); 
		this.add(wsiLabel); 
		this.add(totalLabel);  

	}
}