package com.feldim2425.OTPGen.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.codegen.CodeEntry;
import com.feldim2425.OTPGen.ui.event.CodeEditHandler;

public class CodeEditUI extends JDialog {
	private static final long serialVersionUID = -5300476317075574477L;
	
	private final JPanel contentPanel = new JPanel();
	public JTextField txtIssuer;
	public JTextField txtUser;
	public JPasswordField pwSecret;
	public JList<String> listTags;
	public JCheckBox ckbxShow;
	public JButton okButton;
	public JButton cancelButton;
	public JButton btnCp;
	
	private CodeEditHandler handler = new CodeEditHandler(this);
	
	private CodeEntry code;
	
	public static void start(CodeEntry entry) {
		if(MainUI.isEditing() || SaveFile.save==null) return; //Return if there is already a edit Dialog
		MainUI.setEditing(true);
		try {
			CodeEditUI dialog = new CodeEditUI(entry);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param entry
	 */
	public CodeEditUI(CodeEntry entry) {
		super(MainUI.window.frame);
		setResizable(false);
		int posX = 100;
		int posY = 100;
		if(MainUI.window!=null){
			Point p = MainUI.window.frame.getLocationOnScreen();
			posX = p.x;
			posY = p.y;
		}
		
		String issuer = (entry!=null)? entry.getIssuer() : "";
		String user = (entry!=null)? entry.getUser()       : "";
		String secret = (entry!=null)? entry.getSecret()   : "";
		code = entry;
		
		setTitle(MainUI.STD_NAME+" : "+((entry==null)?"Add":"Edit")+" Code");
		addWindowListener(handler);
		setBounds(posX, posY, 450, 225);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, contentPanel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, contentPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, contentPanel, 450, SpringLayout.WEST, getContentPane());
		getContentPane().setLayout(springLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		listTags = new JList<String>();
		listTags.setBounds(12, 12, 130, 141);
		contentPanel.add(listTags);
		DefaultListModel<String> model = new DefaultListModel<String>();
		int s = SaveFile.getTagList().size();
		for(int i=0;i<s;i++){
			model.addElement(SaveFile.getTagList().get(i).getName());
		}
		listTags.setModel(model);
		listTags.setSelectionModel(new DefaultListSelectionModel() 
		{
			private static final long serialVersionUID = -250401812106309106L;

			@Override
		    public void setSelectionInterval(int i0, int i1) 
		    {
		        if(listTags.isSelectedIndex(i0)) 
		        	listTags.removeSelectionInterval(i0, i1);
		        else 
		        	listTags.addSelectionInterval(i0, i1);
		    }
		});
		selectTags();

		txtIssuer = new JTextField();
		txtIssuer.setBounds(271, 11, 167, 19);
		contentPanel.add(txtIssuer);
		txtIssuer.setColumns(10);
		txtIssuer.setText(issuer);

		txtUser = new JTextField();
		txtUser.setBounds(271, 42, 167, 19);
		contentPanel.add(txtUser);
		txtUser.setColumns(10);
		txtUser.setText(user);

		JLabel lblt1 = new JLabel("Issuer:");
		lblt1.setBounds(160, 13, 70, 15);
		contentPanel.add(lblt1);

		JLabel lblt2 = new JLabel("Username:");
		lblt2.setBounds(160, 44, 93, 15);
		contentPanel.add(lblt2);

		JLabel lblt3 = new JLabel("Secret:");
		lblt3.setBounds(160, 101, 70, 15);
		contentPanel.add(lblt3);

		pwSecret = new JPasswordField();
		pwSecret.setBounds(271, 99, 167, 19);
		pwSecret.setEchoChar('•');
		contentPanel.add(pwSecret);
		pwSecret.setText(secret);

		ckbxShow = new JCheckBox("Show Secret");
		ckbxShow.setBounds(160, 125, 157, 23);
		contentPanel.add(ckbxShow);
		ckbxShow.addActionListener(handler);
		
		btnCp = new JButton("Copy Secret");
		btnCp.setEnabled(false);
		btnCp.setToolTipText("enable \"Show Secret\" to copy the secret");
		btnCp.setFont(new Font("Dialog", Font.BOLD, 9));
		btnCp.setBounds(345, 130, 93, 15);
		contentPanel.add(btnCp);
		btnCp.addActionListener(handler);

		JPanel buttonPane = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, contentPanel, -6, SpringLayout.NORTH, buttonPane);
		springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, buttonPane, 0, SpringLayout.EAST, getContentPane());
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		okButton = new JButton("Save");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(handler);

		cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(handler);
	}
	
	public CodeEntry getEntry(){
		return this.code;
	}
	
	private void selectTags(){
		if(code!=null){
			DefaultListModel<String> model = (DefaultListModel<String>) listTags.getModel();
			int s = code.getTaglist().size();
			ArrayList<Integer> select = new ArrayList<Integer>();
			for(int i=0;i<s;i++){
				int index = model.indexOf(code.getTaglist().get(i));
				if(index>=0) select.add(index);
			}
			s=select.size();
			int[] sel = new int[s];
			for(int i=0;i<s;i++){
				sel[i] = select.get(i);
			}
			listTags.setSelectedIndices(sel);
		}
	}
}
