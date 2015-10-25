package com.feldim2425.OTPGen.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue.ValueType;

public class EntryTag {
	
	private String name;
	private boolean stdview;
	
	public EntryTag(String name, boolean stdview){
		this.name = name;
		this.stdview = stdview;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStdview() {
		return stdview;
	}

	public void setStdview(boolean stdview) {
		this.stdview = stdview;
	}
	
	public EntryTag copy(){
		return new EntryTag(name,stdview);
	}
	
	public JsonObject toJson(){
		JsonObjectBuilder obj = Json.createObjectBuilder();
		obj.add("Name", this.name);
		obj.add("StdView", this.stdview);
		return obj.build();
	}
	
	public static EntryTag fromJson(JsonObject json){
		EntryTag tag = new EntryTag("",false);
		if(!json.containsKey("Name") || !json.containsKey("StdView"))
			return null;
		
		boolean valid = json.get("Name").getValueType().equals(ValueType.STRING) &&
				(json.get("StdView").getValueType().equals(ValueType.FALSE) ||
				json.get("StdView").getValueType().equals(ValueType.TRUE));
		
		if(!valid) return null;
		
		tag.setName(json.getString("Name"));
		tag.setStdview(json.getBoolean("StdView"));
		return tag;
	}
	
}
