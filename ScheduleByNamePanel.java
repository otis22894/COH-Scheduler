import java.util.*;
import javax.swing.*; 
import java.awt.event.*; 
import java.awt.*; 

public class ScheduleByNamePanel extends JPanel{

	private ArrayList<JLabel> labelList; 
	private ArrayList<ArrayList<Shift>> shitfList;
	private EmployeeLists emplist; 
	private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"}; 
	private String[] labels = new String[7]; 
	private GregorianCalendar startingDate; 

	public ScheduleByNamePanel(ArrayList<ArrayList<Shift>> shitfList,EmployeeLists emplist){
		this.setPreferredSize(new Dimension(800,emplist.lifeGuardList.size() * 25));
		this.shitfList = shitfList; 
		this.emplist = emplist; 
		this.setLayout(new GridLayout(0,1)); 
		labelList = new ArrayList<JLabel>(); 
		for(int i = 0; i<emplist.lifeGuardList.size();i++){
			JLabel currentLabel = new JLabel(); 
			currentLabel.setText(emplist.lifeGuardList.get(i).getName()); 
			labelList.add(currentLabel); 
		}
		startingDate = OpeningPanel.startDate; 
		for(int i = 0;i<7;i++){
			labels[i] = ((startingDate.get(startingDate.MONTH))+1) + "/" + ((startingDate.get(startingDate.DAY_OF_MONTH))) + "/" + startingDate.get(startingDate.YEAR);
			startingDate.add(Calendar.DATE,1);
		}
		for(int i = 0;i<7;i++){
			startingDate.add(Calendar.DATE,-1);
		} 
		repaint(); 
		revalidate(); 
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		for(int i= 0;i<emplist.lifeGuardList.size();i++){
			if((i%2)==0){
				g.setColor(Color.WHITE); 
			}else{
				g.setColor(Color.lightGray);
			}
			g.fillRect(0,(20*i)+35,825,25); 
		}

		for(int i = 0; i<=6;i++){
			for(int j = 0; j<shitfList.get(i).size();j++){
				Shift currentShift = shitfList.get(i).get(j); 
				String lifeGuardName = currentShift.getLifeGuard().getName();
				String[] names = emplist.getGuardList(); 
				for(int k = 0; k<names.length;k++){
					if (lifeGuardName.equals(names[k])){
						if(currentShift instanceof TeachingShift){
							g.setColor(Color.ORANGE);
							g.drawString(currentShift.getStart()+"-"+currentShift.getEnd(),(100*i)+150,(20*k)+50); 
						}else if(currentShift instanceof PrivateLessonShift){
							g.setColor(Color.BLUE); 
							g.drawString(currentShift.getStart()+"-"+currentShift.getEnd(),(100*i)+150,(20*k)+50); 
						}else if(currentShift instanceof RequestOffShift){
							g.setColor(Color.RED); 
							g.drawString("R-OFF",(100*i)+150,(20*k)+50);
							//g.drawString(currentShift.getStart()+"-"+currentShift.getEnd(),(100*i)+150,(20*k)+50); 
						}else{
							g.setColor(Color.BLACK);
							g.drawString(currentShift.getStart()+"-"+currentShift.getEnd(),(100*i)+150,(20*k)+50); 
						}
					}
				}
			}
		}

		for(int i = 0; i<7;i++){
			g.setColor(Color.BLACK); 
			g.drawString(days[i],(100*i)+150,20);
			g.drawString(labels[i],(100*i)+155,32);  
		}
		String[] list = emplist.getGuardList(); 
		for(int i = 0;i<emplist.lifeGuardList.size();i++){
			g.setColor(Color.RED);
			g.drawString(list[i],10,(20*i)+50);
			g.drawString(Double.toString(emplist.getGuard(list[i]).getHours()),830,(20*i)+50); 
		}
		revalidate(); 
	}
}