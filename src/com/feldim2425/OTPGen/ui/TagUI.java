package com.feldim2425.OTPGen.ui;

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
import javax.swing.border.EmptyBorder;

import com.feldim2425.OTPGen.ui.event.TagHandler;

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
		if(MainUI.isEditing()) return;  //Return if there is already a edit Dialog
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
		contentPane.setLayout(null);
		setTitle(MainUI.STD_NAME+" : Edit Tags");
		
		btnNewTag = new JButton("New Tag");
		btnNewTag.setBounds(161, 12, 112, 30);
		contentPane.add(btnNewTag);
		btnNewTag.addActionListener(handler);
		
		txtRename = new JTextField();
		txtRename.setEnabled(false);
		txtRename.setText("TagName");
		txtRename.setBounds(161, 58, 143, 30);
		contentPane.add(txtRename);
		txtRename.setColumns(10);
		
		btnRename = new JButton("Rename");
		btnRename.setEnabled(false);
		btnRename.setBounds(316, 57, 114, 30);
		contentPane.add(btnRename);
		btnRename.addActionListener(handler);
		
		chckbxShowInStandart = new JCheckBox("Show in Stantard View");
		chckbxShowInStandart.setEnabled(false);
		chckbxShowInStandart.setBounds(161, 158, 200, 30);
		contentPane.add(chckbxShowInStandart);
		chckbxShowInStandart.addActionListener(handler);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(167, 215, 110, 25);
		contentPane.add(btnOk);
		btnOk.addActionListener(handler);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(283, 215, 110, 25);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(handler);
		
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 12, 145, 250);
		list.addListSelectionListener(handler);
		contentPane.add(list);
		
		btnRemTag = new JButton("Remove Tag");
		btnRemTag.setEnabled(false);
		btnRemTag.setBounds(308, 12, 122, 30);
		contentPane.add(btnRemTag);
		
		lblinfo1 = new JLabel("Max. 16 Characters");
		lblinfo1.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo1.setBounds(161, 90, 172, 15);
		contentPane.add(lblinfo1);
		
		lblinfo2 = new JLabel("No special characters like ?!+#-.,");
		lblinfo2.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo2.setBounds(161, 105, 250, 15);
		contentPane.add(lblinfo2);
		
		lblinfo3 = new JLabel("Underlines are allowed but not as 1. character");
		lblinfo3.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo3.setBounds(161, 120, 278, 15);
		contentPane.add(lblinfo3);
		
		lblinfo4 = new JLabel("Existing names are not allowed");
		lblinfo4.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo4.setBounds(161, 135, 278, 15);
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
