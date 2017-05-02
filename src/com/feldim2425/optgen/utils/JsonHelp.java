package com.feldim2425.optgen.utils;

import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class JsonHelp {
	public static JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
	    JsonObjectBuilder job = Json.createObjectBuilder();

	    for (Entry<String, JsonValue> entry : jo.entrySet()) {
	        job.add(entry.getKey(), entry.getValue());
	    }

	    return job;
	}
}
