import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 
import java.text.NumberFormat;

public class EmployeeView extends JPanel{

	private final JComboBox<String> comboBox; 
	private final EmployeeLists employeeList; 
	private final ArrayList<ArrayList<Shift>> shiftList; 
	private DefaultListModel<String> listModel; 
	private JList<String> list;
	private ArrayList<String> myList;  
	private JScrollPane listScroller;
	private JButton remove;  
	private NumberFormat numformat; 

	public EmployeeView(EmployeeLists employees,ArrayList<ArrayList<Shift>> shifts){

		employeeList = employees; 
		shiftList = shifts; 

		this.setLayout(new BorderLayout()); 
		numformat = NumberFormat.getCurrencyInstance();

		listModel = new DefaultListModel<String>(); 
		myList = employeeList.getGuardShifts(shiftList,employeeList.getGuard(employeeList.getGuardList()[0]));
		for(int j = 0; j<myList.size();j++){
			listModel.addElement(myList.get(j)); 
		}
		if(employeeList.getGuard(employeeList.getGuardList()[0]) instanceof WSI){
			listModel.addElement("<html><font color=\"red\">Employee Pay This Week: " + numformat.format(employeeList.getGuard(employeeList.getGuardList()[0]).getHours() * BudgetPanel.WSIPAY) + "</font></html>"); 
		}else{
			listModel.addElement("<html><font color=\"red\">Employee Pay This Week: " + numformat.format(employeeList.getGuard(employeeList.getGuardList()[0]).getHours() * BudgetPanel.LIFEGUARDPAY)+ "</font></html>"); 
		}
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80)); 

		comboBox = new JComboBox<String>(employeeList.getGuardList());
		comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				changeList(); 
			}
		});
		remove = new JButton("Remove Selected Shifts"); 
		remove.addActionListener(new ButtonListener()); 
		this.add(comboBox,BorderLayout.NORTH);  
		this.add(remove,BorderLayout.SOUTH);
		this.add(list,BorderLayout.CENTER); 


	}

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int[] shifts = list.getSelectedIndices(); 
			if(shifts.length != 0){
				int ndx = 0; 
				for(int i = 0; i<=6;i++){
					for(int j = 0; j<shiftList.get(i).size();j++){
						if(shiftList.get(i).get(j).getLifeGuard().getName().equals((String)comboBox.getSelectedItem())){
							for(int k = 0; k<shifts.length;k++){
								if(ndx==shifts[k]){
									if(shiftList.get(i).get(j) instanceof RequestOffShift == false){
										shiftList.get(i).get(j).getLifeGuard().giveHours(-1.0 * ((shiftList.get(i).get(j).getEnd() - shiftList.get(i).get(j).getStart())/100.0)); 
									}
									shiftList.get(i).remove(shiftList.get(i).get(j)); 
								}
							}
							ndx++; 
						}
					}
				}
				changeList(); 
			}else{
				JOptionPane.showMessageDialog(null,"Please select a shift to delete");
			}
		}
	}

	public void changeList(){
		this.remove(list); 
		for(int i = 0; i<employeeList.lifeGuardList.size(); i++){
			if(employeeList.lifeGuardList.get(i).getName().equals((String)comboBox.getSelectedItem())){
				listModel = new DefaultListModel<String>();
				myList = employeeList.getGuardShifts(shiftList,employeeList.lifeGuardList.get(i)); 
				for(int j = 0; j<myList.size();j++){
					listModel.addElement(myList.get(j)); 
				}
				if(employeeList.lifeGuardList.get(i) instanceof WSI){
					listModel.addElement("<html><font color=\"red\">Employee Pay This Week: " + numformat.format(employeeList.lifeGuardList.get(i).getHours() * 9.12)+ "</font></html>"); 
				}else{
					listModel.addElement("<html><font color=\"red\">Employee Pay This Week: " + numformat.format(employeeList.lifeGuardList.get(i).getHours() * 8.57)+ "</font></html>"); 
				}
				list = new JList<String>(listModel);
				list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				listScroller = new JScrollPane(list);
				listScroller.setPreferredSize(new Dimension(250, 80));  
			}
		}
		this.add(list); 
		repaint(); 
		revalidate(); 
	}	
}