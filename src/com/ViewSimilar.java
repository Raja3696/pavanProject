package com;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.Font;
public class ViewSimilar extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	int length = 0;
	String hop;
public ViewSimilar(){
	super("View Similar Record");
	
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int row_no,int column_no){
			return false;
		}
	};
	tab = new JTable(dtm);
	tab.getTableHeader().setFont(new Font("Courier New",Font.BOLD,14));
	tab.setFont(new Font("Courier New",Font.BOLD,13));
	tab.setRowHeight(30);
	jsp = new JScrollPane(tab);
	dtm.addColumn("Source Patient ID");
	dtm.addColumn("Target Patient ID");
	dtm.addColumn("Similarity Score");
	dtm.addColumn("Value Level");
	dtm.addColumn("Source Disease Name");
	dtm.addColumn("Target Disease Name");
	dtm.addColumn("Contact No");
	dtm.addColumn("Email ID");
	dtm.addColumn("Zip Code");
	getContentPane().add(jsp);
	
}
}