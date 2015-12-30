package com.feldim2425.OTPGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.feldim2425.OTPGen.codegen.CodeEntry;
import com.feldim2425.OTPGen.codegen.CodeFactory;
import com.feldim2425.OTPGen.ui.MainUI;
import com.feldim2425.OTPGen.utils.EntryTag;
import com.feldim2425.OTPGen.utils.JsonHelp;


public class SaveFile {
	
	private static ArrayList<EntryTag> tags = new ArrayList<EntryTag>();
	public static File save=null;
	public static boolean encrypt;
	private static boolean isOpen = false;
	
	public static void saveDefaultFile(File f){
		if(f==null) return;
		isOpen=true;
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		
			JsonGenerator t = Json.createGenerator(out);
			
			JsonObjectBuilder ob = Json.createObjectBuilder();
			JsonArrayBuilder tagob = Json.createArrayBuilder();
			JsonArrayBuilder arr = Json.createArrayBuilder();
			t.writeStartObject();
			
			ob.add("encrypted", false);
			ob.add("tags", tagob);
			
			t.write("head", ob.build());
			t.write("totp", arr.build());
			t.writeEnd();
			t.close();
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		isOpen=false;
	}
	
	public static boolean saveAll(File f){
		if(f==null) return false;
		isOpen=true;
		OutputStream out = null;
		InputStream in = null;
		try {
			in = new FileInputStream(f);
		
			JsonReader read = Json.createReader(in);
			
			JsonObject json = read.readObject();
			
			read.close();
			in.close();
			if(!json.containsKey("head") || !json.get("head").getValueType().equals(ValueType.OBJECT)){
				isOpen=false;
				return false;
			}
			
			
			JsonArrayBuilder arrb1 = Json.createArrayBuilder();
			int size = tags.size();
			for(int i=0;i<size;i++){
				arrb1.add(tags.get(i).toJson());
			}
			
			JsonArrayBuilder arrb2 = Json.createArrayBuilder();
			size = CodeFactory.getClist().size();
			for(int i=0;i<size;i++){
				arrb2.add(CodeFactory.getClist().get(i).toJson());
			}
			
			out = new FileOutputStream(f);
			JsonObjectBuilder t = Json.createObjectBuilder();
			t.add("tags", arrb1.build());
			t.add("encrypted", encrypt);
			
			JsonObjectBuilder t2 = JsonHelp.jsonObjectToBuilder(json);
			t2.add("head", t.build());
			t2.add("totp", arrb2.build());
			
			JsonWriter wr = Json.createWriter(out);
			wr.write(t2.build());
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isOpen=false;
		return true;
	}
	
	public static boolean read(File f){
		resetData();
		InputStream in;
		try {
			in = new FileInputStream(f); 
			
			JsonReader read = Json.createReader(in);
			
			JsonObject json = read.readObject();
			
			if(!json.containsKey("head") || !json.get("head").getValueType().equals(ValueType.OBJECT)) return false;
			JsonObject obj1 = json.getJsonObject("head");
			if(!obj1.containsKey("tags") || !obj1.get("tags").getValueType().equals(ValueType.ARRAY)) return false;
			JsonArray arr1 = obj1.getJsonArray("tags");
			if(!obj1.containsKey("encrypted") || 
					(!obj1.get("encrypted").getValueType().equals(ValueType.TRUE) &&  !obj1.get("encrypted").getValueType().equals(ValueType.FALSE)))
				return false;
			encrypt = obj1.getBoolean("encrypted");
			if(!json.containsKey("totp") || !json.get("totp").getValueType().equals(ValueType.ARRAY)) return false;
			JsonArray arr2 = json.getJsonArray("totp");
			
			int s = arr1.size();
			for(int i=0;i<s;i++){
				EntryTag tag = EntryTag.fromJson(arr1.getJsonObject(i));
				if(tag!=null){
					tags.add(tag);
				}
			}
			
			int s2 = arr2.size();
			for(int i=0;i<s2;i++){
				CodeEntry code = CodeEntry.fromJson(arr2.getJsonObject(i));
				if(code!=null){
					CodeFactory.addEntry(code);
				}
			}
			if(Main.doneInit) CodeFactory.updateData();
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private static void resetData() {
		tags.clear();
		CodeFactory.getClist().clear();
		CodeFactory.getSort().clear();
		CodeFactory.getVisible().clear();
		if(Main.doneInit){
			MainUI.window.selectView("#");
			CodeFactory.updateUI();
		}
	}

	public static boolean selectSave(){
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
		FileFilter filter = new FileFilter(){
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith(".otpgen");
			}

			@Override
			public String getDescription() {
				return "Savefile [*.otpgen]";
			}
		};
		
		dialog.setAcceptAllFileFilterUsed(false);
		dialog.setFileFilter(filter);
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		dialog.showDialog(null, "Create / Select");
		if(dialog.getSelectedFile()!=null){
			try {
				File f = dialog.getSelectedFile();
				if(!dialog.getSelectedFile().exists()){
					f = (!dialog.getSelectedFile().getName().endsWith(".otpgen"))? 
							new File(dialog.getSelectedFile().getAbsolutePath()+".otpgen") : f;
					
					f.createNewFile();
					saveDefaultFile(f);
				}
				isOpen=true;
				if(!read(f)){
					isOpen=false;
					return false;
				}
				isOpen=false;
				save = f;
				if(MainUI.window != null){
					MainUI.window.reinitTags("#");
					MainUI.window.setFileWarning(false);
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return Main.doneInit;
	}
	
	public static List<EntryTag> getTagList(){
		return tags;
	}
	
	public static boolean curruptedFile(){
		do{
			JFrame askframe=new JFrame();
			Object[] options = {"Create/Select File", "Exit"};
			int n = JOptionPane.showOptionDialog(askframe,
				"File currupted! Select/Create another File?",
				MainUI.STD_NAME+" : Currupted File",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]);
			askframe.dispose();
			if(n==1){
				Main.exit(false);
				return false;
			}
		}while(!selectSave());
		return true;
	}

	public static void closeFile() {
		if(!isOpen){
			resetData();
			save = null;
			if(MainUI.window!=null)
				MainUI.window.setFileWarning(true);
		}
	}
}
