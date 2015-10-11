import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class HoursPanel extends JPanel{
	
	private JLabel myLabel; 
	private String currentText;
	private String[] list;  

	public HoursPanel(EmployeeLists emplist){
		currentText = ""; 
		myLabel = new JLabel(); 
		list = emplist.getGuardList(); 
		for(int i = 0; i<list.length;i++){
			currentText = currentText + emplist.getGuard(list[i]).getName() + " " + emplist.getGuard(list[i]).getHours() + "<br />";
		}
		currentText = "<html>" + currentText + "</html>"; 
		myLabel.setText(currentText); 
		this.add(myLabel); 
	}
}