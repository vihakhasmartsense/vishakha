package com.example.ronak.demonew.Util;

import org.json.JSONObject;

import java.util.Map;

public interface VolleyResponseListener {
	
	public Map<String, String> OnPreExecute();
	public void OnSuccessListener(JSONObject mJsonObject);
	public void OnErrorListener(String msg);
	

}
