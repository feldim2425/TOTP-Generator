package com.feldim2425.OTPGen.ui;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.ui.event.TagHandler;
import javax.swing.JLabel;
import java.awt.Font;

public class TagUI extends JFrame {
	
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
	private JLabel lblUnderlinesAreAllowed;

	/**
	 * Launch the application.
	 */
	public static void start() {
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
		int posX = 100;
		int posY = 100;
		if(MainUI.window!=null){
			Point p = MainUI.window.frame.getLocationOnScreen();
			posX = p.x;
			posY = p.y;
		}
		
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
		chckbxShowInStandart.setBounds(161, 144, 200, 30);
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
		list.setBounds(10, 12, 145, 250);
		list.addListSelectionListener(handler);
		contentPane.add(list);
		
		btnRemTag = new JButton("Remove Tag");
		btnRemTag.setEnabled(false);
		btnRemTag.setBounds(308, 12, 122, 30);
		contentPane.add(btnRemTag);
		
		lblinfo1 = new JLabel("Max. 16 Characters");
		lblinfo1.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo1.setBounds(161, 88, 172, 15);
		contentPane.add(lblinfo1);
		
		lblinfo2 = new JLabel("No special characters like ?!+#-.,");
		lblinfo2.setFont(new Font("Dialog", Font.BOLD, 9));
		lblinfo2.setBounds(161, 100, 250, 15);
		contentPane.add(lblinfo2);
		
		lblUnderlinesAreAllowed = new JLabel("Underlines are allowed but not as 1. character");
		lblUnderlinesAreAllowed.setFont(new Font("Dialog", Font.BOLD, 9));
		lblUnderlinesAreAllowed.setBounds(161, 115, 278, 15);
		contentPane.add(lblUnderlinesAreAllowed);
		btnRemTag.addActionListener(handler);
		
		initList();
	}
	
	public void initList(){
		DefaultListModel<String> lmod= new DefaultListModel<String>();
		int s = SaveFile.getTagList().size();
		for(int i=0;i<s;i++){
			lmod.addElement(SaveFile.getTagList().get(i).getName());
		}
		list.setModel(lmod);
	}
}
