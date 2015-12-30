package com.feldim2425.OTPGen.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeEntry;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.event.SortuiHandler;

public class SortUI extends JDialog {

	private static final long serialVersionUID = -6951898857853752290L;
	private final JPanel contentPanel = new JPanel();
	
	public JButton cancelButton;
	public JButton okButton;
	public JButton btnUp;
	public JButton btnDown;
	public JList<String> list;
	
	public HashMap<Integer,Integer> tempsort = new HashMap<Integer,Integer>();
	
	private SortuiHandler handler = new SortuiHandler(this);

	/**
	 * Launch the application.
	 */
	public static void start() {
		try {
			if (MainUI.isEditing() || SaveFile.save==null)
				return;
			MainUI.setEditing(true);
			SortUI dialog = new SortUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SortUI() {
		setResizable(false);
		addWindowListener(handler);
		setBounds(100, 100, 463, 328);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, list, 11, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, list, 11, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, list, -11, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, list, 346, SpringLayout.WEST, contentPanel);
		contentPanel.add(list);
		initList();
		
		btnUp = new JButton("↑");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnUp, 7, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnUp, 48, SpringLayout.EAST, list);
		sl_contentPanel.putConstraint(SpringLayout.EAST, btnUp, -11, SpringLayout.EAST, contentPanel);
		contentPanel.add(btnUp);
		btnUp.addActionListener(handler);
		
		btnDown = new JButton("↓");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, btnDown, 5, SpringLayout.SOUTH, btnUp);
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnDown, 0, SpringLayout.WEST, btnUp);
		sl_contentPanel.putConstraint(SpringLayout.EAST, btnDown, -11, SpringLayout.EAST, contentPanel);
		contentPanel.add(btnDown);
		btnDown.addActionListener(handler);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(handler);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(handler);
	}
	
	public void initList(){
		int s = CodeFactory.getClist().size();
		for(int i=0;i<s;i++){
			tempsort.put(i, CodeFactory.getSort().get(i));
		}
		updateList();
	}
	
	public void updateList(){
		int s = CodeFactory.getClist().size();
		DefaultListModel<String> lcodes= new DefaultListModel<String>();
		for(int i=0;i<s;i++){
			CodeEntry c = CodeFactory.getClist().get(tempsort.get(i));
			lcodes.addElement(c.getIssuer()+" / "+c.getUser());
		}
		list.setModel(lcodes);
	}
}
