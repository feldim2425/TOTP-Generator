package com.feldim2425.OTPGen.codegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue.ValueType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import org.apache.commons.codec.binary.Base32;

import com.feldim2425.OTPGen.SaveFile;
import com.feldim2425.OTPGen.ui.MainUI;

public class CodeEntry extends JPanel implements ComponentListener, ActionListener {
	
	private JLabel label;
	private String company;
	private String user;
	private JLabel label_1;
	private JProgressBar progressBar;
	private String secret;
	private long nextCodeGen;
	private JSeparator separator;
	JButton btnRemove;
	
	public CodeEntry(String secret,String company, String user) {
		setBackground(new Color(204, 204, 255));
		this.secret=secret;
		this.user=user;
		this.company=company;
		setLayout(null);
		
		label = new JLabel("New label");
		label.setFont(new Font("Dialog", Font.BOLD, 13));
		label.setBounds(12, 12, 260, 15);
		label.setText(company+" - "+user);
		add(label);
		
		label_1 = new JLabel("000000");
		label_1.setFont(new Font("Dialog", Font.BOLD, 30));
		label_1.setForeground(Color.BLUE);
		label_1.setBounds(12, 28, 133, 33);
		add(label_1);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.DARK_GRAY);
		progressBar.setBounds(12, 62, 133, 14);
		progressBar.setMaximum(300);
		progressBar.setValue((int) ((30D-CodeFactory.nextCodeCoutdown())*10D));
		add(progressBar);
		
		separator = new JSeparator();
		separator.setBounds(0, 85, 280, 2);
		add(separator);
		
		btnRemove = new JButton("-");
		btnRemove.setForeground(Color.RED);
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRemove.setBounds(211, 28, 40, 20);
		add(btnRemove);
		btnRemove.addActionListener(this);
		
		btnNewButton = new JButton("Edit");
		btnNewButton.setBounds(211, 56, 61, 20);
		add(btnNewButton);
		
		
		addComponentListener(this);
		
		btnNewButton_1 = new JButton("QR");
		btnNewButton_1.setBounds(271, 56, 55, 20);
		add(btnNewButton_1);
		
		btnCopy = new JButton("Copy");
		btnCopy.setBounds(251, 28, 75, 20);
		add(btnCopy);
		generate();
		btnCopy.addActionListener(this);
	}
	
	public void update(int bar){
		progressBar.setValue(bar);
		if(System.currentTimeMillis()>=nextCodeGen){
			nextCodeGen = System.currentTimeMillis()+(long)(30-CodeFactory.nextCodeCoutdown());
			generate();
		}
	}
	
	private void generate(){
		Base32 code = new Base32();
		byte[] decodedKey = code.decode(secret);
		label_1.setText(String.format("%06d", TOTP.generateTOTP(decodedKey, CodeFactory.getTime(), 6,"HmacSHA1")));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnCopy;

	@Override
	public void componentResized(ComponentEvent e) {
		separator.setSize(getWidth(), 2);
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

	public JsonObject toJson() {
		JsonObjectBuilder b = Json.createObjectBuilder();
		b.add("user", user);
		b.add("company", company);
		b.add("secret", secret);
		b.add("tags", "");
		return b.build();
	}
	
	public static CodeEntry fromJson(JsonObject obj) {
		if(!obj.containsKey("user") && !obj.get("user").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("company") && !obj.get("company").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("secret") && !obj.get("secret").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("tags") && !obj.get("tags").getValueType().equals(ValueType.STRING)) return null;
		
		return new CodeEntry(obj.getString("secret"),obj.getString("company"),obj.getString("user"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.btnRemove)){
			JFrame askframe=new JFrame();
			Object[] options = {"Yes", "No"};
			int n = JOptionPane.showOptionDialog(askframe,
					"Do you really want to delete "+company+" - "+user,
				    MainUI.STD_NAME+" : Delete ?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,     //do not use a custom Icon
				    options,  //the titles of buttons
				    options[1]); //default button title
			 askframe.dispose();
			if(n==0){
				CodeFactory.clist.remove(this);
				CodeFactory.updateUI();
				SaveFile.saveAll(SaveFile.save);
			}
		}
		else if(e.getSource().equals(this.btnCopy)){
			StringSelection selection = new StringSelection(this.label_1.getText());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,selection);
		}
	}
}