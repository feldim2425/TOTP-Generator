package com.feldim2425.OTPGen.codegen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

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
import com.feldim2425.OTPGen.ui.CodeEditUI;
import com.feldim2425.OTPGen.ui.MainUI;
import com.feldim2425.OTPGen.ui.ShowQRDialog;

//	TODO: Remove the Index and resort other Codes.
public class CodeEntry extends JPanel implements ComponentListener, ActionListener {
	
	private JLabel label;
	private String issuer;
	private String user;
	private JLabel label_1;
	private JProgressBar progressBar;
	private String secret;
	private JSeparator separator;
	private JButton btnRemove;
	private ArrayList<String> taglist = new ArrayList<String>();
	private int index=-1;

	public CodeEntry(String secret,String issuer, String user, List<String> tags) {
		this(secret, issuer,user);
		taglist.addAll(tags);
		
	}
	
	public CodeEntry(String secret,String issuer, String user, String tag) {
		this(secret,issuer,user);
		String[] tags = tag.split(";");
		for(int i=0;i<tags.length;i++){
			taglist.add(tags[i]);
		}
	}
	
	public CodeEntry(String secret,String issuer, String user, String tag, int index) {
		this(secret,issuer,user,tag);
		this.index = index;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public CodeEntry(String secret,String issuer, String user) {
		index = (index < 0) ? CodeFactory.getClist().size() : index;
		
		setBackground(new Color(204, 204, 255));
		this.secret=secret;
		this.user=user;
		this.issuer=issuer;
		setLayout(null);
		
		label = new JLabel("New label");
		label.setFont(new Font("Dialog", Font.BOLD, 13));
		label.setBounds(12, 12, 260, 15);
		label.setText(issuer+" - "+user);
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
		add(progressBar);
		
		separator = new JSeparator();
		separator.setBounds(0, 85, 326, 2);
		add(separator);
		
		btnRemove = new JButton("-");
		btnRemove.setForeground(Color.RED);
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 14));
		btnRemove.setBounds(211, 28, 40, 20);
		add(btnRemove);
		btnRemove.addActionListener(this);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(211, 56, 61, 20);
		add(btnEdit);
		btnEdit.addActionListener(this);
		
		
		addComponentListener(this);
		
		btnQR = new JButton("QR");
		btnQR.setToolTipText("Not impemented yet");
		//btnQR.setBackground(Color.GRAY);
		//btnQR.setEnabled(false);
		btnQR.setBounds(271, 56, 55, 20);
		add(btnQR);
		btnQR.addActionListener(this);
		
		btnCopy = new JButton("Copy");
		btnCopy.setBounds(251, 28, 75, 20);
		add(btnCopy);
		generate();
		btnCopy.addActionListener(this);
		
		update((int) ((30D-CodeFactory.nextCodeCoutdown())*10D) , true);
	}
	
	synchronized public void update(int bar, boolean regen){
		progressBar.setValue(bar);
		
		if(regen){
			label_1.setForeground(Color.BLUE);
			generate();
		}
		if(bar<30){
			label_1.setForeground(Color.RED);
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
	private JButton btnEdit;
	private JButton btnQR;
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
		b.add("issuer", issuer);
		b.add("secret", secret);
		b.add("tags", tagString());
		b.add("index", index);
		return b.build();
	}
	
	public static CodeEntry fromJson(JsonObject obj) {
		int index = 0;
		if(!obj.containsKey("user") || !obj.get("user").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("issuer") || !obj.get("issuer").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("secret") || !obj.get("secret").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("tags") || !obj.get("tags").getValueType().equals(ValueType.STRING)) return null;
		if(!obj.containsKey("index") || !obj.get("index").getValueType().equals(ValueType.NUMBER)){
			CodeFactory.doLoadIndexResort();
			index = -1;
		}
		index = (index>-1) ? obj.getInt("index") : -1;
		
		return new CodeEntry(obj.getString("secret"),obj.getString("issuer"),obj.getString("user"),obj.getString("tags"),index);
	}
	
	private String tagString() {
		String s = "";
		int size = taglist.size();
		for(int i=0;i<size;i++){
			if(i!=0) s+=";";
			s+=taglist.get(i);
		}
		return s;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.btnRemove)){
			JFrame askframe=new JFrame();
			Object[] options = {"Yes", "No"};
			int n = JOptionPane.showOptionDialog(askframe,
					"Do you really want to delete "+issuer+" - "+user,
				    MainUI.STD_NAME+" : Delete ?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,     //do not use a custom Icon
				    options,  //the titles of buttons
				    options[1]); //default button title
			 askframe.dispose();
			if(n==0){
				removeIndex();
				CodeFactory.getClist().remove(this);
				CodeFactory.updateUI();
				SaveFile.saveAll(SaveFile.save);
			}
		}
		else if(e.getSource().equals(this.btnCopy)){
			StringSelection selection = new StringSelection(this.label_1.getText());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection,selection);
		}
		else if(e.getSource().equals(this.btnEdit)){
			CodeEditUI.start(this);
		}
		else if(e.getSource().equals(this.btnQR)){
			ShowQRDialog.start(this);
		}
	}
	
	private void removeIndex(){
		ArrayList<Integer> tempsort = new ArrayList<Integer>();
		int s = CodeFactory.getClist().size();
		for(int i=0;i<s;i++){
			tempsort.add(CodeFactory.getSort().get(i));
		}
		tempsort.remove(index);
		CodeFactory.getSort().clear();
		for(int i=0;i<s-1;i++){
			CodeFactory.getSort().put(i, tempsort.get(i));
			CodeFactory.getClist().get(tempsort.get(i)).setIndex(i);
		}
	}
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
		this.label.setText(issuer+" - "+user);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
		this.label.setText(issuer+" - "+user);
	}

	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public ArrayList<String> getTaglist() {
		return taglist;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}