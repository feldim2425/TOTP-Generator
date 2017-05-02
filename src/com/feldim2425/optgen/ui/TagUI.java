package com.feldim2425.optgen.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import com.feldim2425.optgen.SaveFile;
import com.feldim2425.optgen.ui.event.TagHandler;

public class TagUI extends JDialog {
	
	private static final long serialVersionUID = 941268257733743455L;
	
	private TagHandler handler = new TagHandler(this);
	
	private JPanel contentPane;
	public JTextField txtRename;
	public JButton btnCancel;
	
	public JList<String> list;
	public JButton btnOk;
	public JCheckBox chckbxShowInStandart;
	public JButton btnRename;
	public JButton btnNewTag;
	public JButton btnRemTag;
	private JLabel lblinfo1;
	private JLabel lblinfo2;
	private JLabel lblinfo3;
	private JLabel lblinfo4;

	/**
	 * Launch the application.
	 */
	public static void start() {
		if(MainUI.isEditing() || SaveFile.save==null) return;  //Return if there is already a edit Dialog
		MainUI.setEditing(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TagUI frame = new TagUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TagUI() {
		super(MainUI.window.frame);
		
		handler.initLists();
		
		int posX = 100;
		int posY = 100;
		if(MainUI.window!=null){
			Point p = MainUI.window.frame.getLocationOnScreen();
			posX = p.x;
			posY = p.y;
		}
		
		addWindowListener(handler);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(posX, posY, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle(MainUI.STD_NAME+" : Edit Tags");
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		btnNewTag = new JButton("New Tag");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnNewTag, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnNewTag, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnNewTag, 42, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnNewTag, 273, SpringLayout.WEST, contentPane);
		contentPane.add(btnNewTag);
		btnNewTag.addActionListener(handler);
		
		txtRename = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtRename, 58, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtRename, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtRename, 88, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtRename, 304, SpringLayout.WEST, contentPane);
		txtRename.setEnabled(false);
		txtRename.setText("TagName");
		contentPane.add(txtRename);
		txtRename.setColumns(10);
		
		btnRename = new JButton("Rename");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRename, 57, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRename, 316, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRename, 87, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRename, 430, SpringLayout.WEST, contentPane);
		btnRename.setEnabled(false);
		contentPane.add(btnRename);
		btnRename.addActionListener(handler);
		
		chckbxShowInStandart = new JCheckBox("Show in Stantard View");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chckbxShowInStandart, 158, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, chckbxShowInStandart, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, chckbxShowInStandart, 188, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, chckbxShowInStandart, 361, SpringLayout.WEST, contentPane);
		chckbxShowInStandart.setEnabled(false);
		contentPane.add(chckbxShowInStandart);
		chckbxShowInStandart.addActionListener(handler);
		
		btnOk = new JButton("OK");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnOk, 215, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnOk, 167, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnOk, 277, SpringLayout.WEST, contentPane);
		contentPane.add(btnOk);
		btnOk.addActionListener(handler);
		
		btnCancel = new JButton("Cancel");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 215, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCancel, 283, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, 393, SpringLayout.WEST, contentPane);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(handler);
		
		list = new JList<String>();
		sl_contentPane.putConstraint(SpringLayout.NORTH, list, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, list, 262, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, list, 155, SpringLayout.WEST, contentPane);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(handler);
		contentPane.add(list);
		
		btnRemTag = new JButton("Remove Tag");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnRemTag, 12, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnRemTag, 308, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnRemTag, 42, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnRemTag, 430, SpringLayout.WEST, contentPane);
		btnRemTag.setEnabled(false);
		contentPane.add(btnRemTag);
		
		lblinfo1 = new JLabel("Max. 16 Characters");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblinfo1, 90, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblinfo1, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblinfo1, 105, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblinfo1, 333, SpringLayout.WEST, contentPane);
		lblinfo1.setFont(new Font("Dialog", Font.BOLD, 9));
		contentPane.add(lblinfo1);
		
		lblinfo2 = new JLabel("No special characters like ?!+#-.,");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblinfo2, 105, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblinfo2, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblinfo2, 120, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblinfo2, 411, SpringLayout.WEST, contentPane);
		lblinfo2.setFont(new Font("Dialog", Font.BOLD, 9));
		contentPane.add(lblinfo2);
		
		lblinfo3 = new JLabel("Underlines are allowed but not as 1. character");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblinfo3, 120, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblinfo3, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblinfo3, 135, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblinfo3, 439, SpringLayout.WEST, contentPane);
		lblinfo3.setFont(new Font("Dialog", Font.BOLD, 9));
		contentPane.add(lblinfo3);
		
		lblinfo4 = new JLabel("Existing names are not allowed");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblinfo4, 135, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblinfo4, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblinfo4, 150, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblinfo4, 439, SpringLayout.WEST, contentPane);
		lblinfo4.setFont(new Font("Dialog", Font.BOLD, 9));
		contentPane.add(lblinfo4);
		
		btnRemTag.addActionListener(handler);
		
		initList();
	}
	
	public void initList(){
		DefaultListModel<String> lmod= new DefaultListModel<String>();
		int s = handler.tags.size();
		for(int i=0;i<s;i++){
			lmod.addElement(handler.tags.get(i).getName());
		}
		list.setModel(lmod);
	}
}
