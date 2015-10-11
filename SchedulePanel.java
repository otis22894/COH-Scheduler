import java.util.*;
import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*; 

public class SchedulePanel extends JPanel{
	
	private JLabel[] weeklyLabels; 
	private JLabel mondayLabel;
	private JLabel tuesdayLabel; 
	private JLabel wednesdayLabel; 
	private JLabel thursdayLabel; 
	private JLabel fridayLabel;
	private JLabel satrudayLabel; 
	private JLabel sundayLabel;  
	private ArrayList<ArrayList<Shift>> shitfList; 
	private static final String[] days = {
		"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"
	};

	public SchedulePanel(ArrayList<ArrayList<Shift>> list){
		weeklyLabels = new JLabel[7]; 
		mondayLabel = new JLabel();
		tuesdayLabel = new JLabel(); 
		wednesdayLabel = new JLabel();  
		thursdayLabel = new JLabel();
		fridayLabel = new JLabel();
		satrudayLabel = new JLabel();
		sundayLabel = new JLabel();
		weeklyLabels[0] = mondayLabel; 
		weeklyLabels[1] = tuesdayLabel;
		weeklyLabels[2] = wednesdayLabel;
		weeklyLabels[3] = thursdayLabel;
		weeklyLabels[4] = fridayLabel;
		weeklyLabels[5] = satrudayLabel;
		weeklyLabels[6] = sundayLabel;

		this.shitfList = list;

		for(int i = 0; i<=6; i++){
			weeklyLabels[i].setText(weeklyLabels[i].getText() + days[i] + "<br />"); 
			for(int j = 0; j<shitfList.get(i).size();j++){
				if(shitfList.get(i).get(j) instanceof TeachingShift){
					weeklyLabels[i].setText(weeklyLabels[i].getText() + "<font color=\"orange\">" + 
					shitfList.get(i).get(j).getStart() +"-"+ shitfList.get(i).get(j).getEnd() + " " +
					shitfList.get(i).get(j).getLifeGuard().getName()+ "</font>" + "<br />"); 
				}else if(shitfList.get(i).get(j) instanceof PrivateLessonShift){
					weeklyLabels[i].setText(weeklyLabels[i].getText() + "<font color=\"blue\">" + 
					shitfList.get(i).get(j).getStart() +"-"+ shitfList.get(i).get(j).getEnd() + " " +  
					shitfList.get(i).get(j).getLifeGuard().getName()+ "</font>" + "<br />"); 
				}else if(shitfList.get(i).get(j) instanceof RequestOffShift){
					weeklyLabels[i].setText(weeklyLabels[i].getText() + "<font color=\"purple\">" + 
					shitfList.get(i).get(j).getStart() +"-"+ shitfList.get(i).get(j).getEnd() + " " +  
					shitfList.get(i).get(j).getLifeGuard().getName()+ "</font>" + "<br />"); 
				}else{
					weeklyLabels[i].setText(weeklyLabels[i].getText() +
					shitfList.get(i).get(j).getStart() +"-"+ shitfList.get(i).get(j).getEnd() + " " +  
					shitfList.get(i).get(j).getLifeGuard().getName()+ "<br />"); 
				}
			}
		}
		for(int i = 0; i <=6; i++){
			weeklyLabels[i].setText("<html>" + weeklyLabels[i].getText() + "</html>");
			this.add(weeklyLabels[i]); 
		}

	}

}