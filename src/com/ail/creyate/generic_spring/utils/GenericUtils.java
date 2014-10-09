package com.ail.creyate.generic_spring.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class GenericUtils {
	public static String getRequestParameter(HttpServletRequest request, String key, String defaultValue) {
		String value = request.getParameter(key);
		if(value == null || value == "null") {
			return defaultValue;
		}
		return value;
	}
	
	public static String sendHTTPRequest(String url, Map<String, String> headers, String urlParameters, RequestMethod method) throws Exception {
		 
		
		URL obj = new URL(url);
		//if(isHTTPs) {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//} else {
			//HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//}
 
		//add reuqest header
		con.setRequestMethod(method.toString());
		if(headers != null) {
			for(Map.Entry<String, String> entry : headers.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		
		/*con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Host", "accounts.google.com");
		con.setRequestProperty("Content-Length", "\""+urlParameters.toString().length()+"\"");*/
 
 
		// Send request
		con.setDoOutput(true);
		con.connect();
		if(urlParameters != null) {
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
		}
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending '"+method.toString()+"' request to URL : " + url);
		System.out.println(method.toString()+" parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		return response.toString();
 
	}
	
	public static Boolean isEmailAllowed(String email) {
		String allowedLogins = Resources.getSystemVariable("allowed_logins");
		if(allowedLogins != null) {
			String[] logins = allowedLogins.split(",");
			if(email != null) {
				for(int i = 0; i < logins.length; i++) {
					logins[i] = logins[i].trim();
					if(logins[i].equalsIgnoreCase(email)) {
						return true;
					}
				}
			}
		}
		return (email != null && email.endsWith("@arvindinternet.com"));
	}
	
	public static Boolean isLoginValid(HttpServletRequest request) {
		return (request.getSession() != null) && (request.getSession().getAttribute("email") != null);
	}
	
	public static String getCSVString(String values){
		values.replaceAll("\\s+", "");
		String[] tokens = values.split(",");
		
		StringBuffer csv = new StringBuffer();
		for(String token: tokens){
			token = token.trim();
			if(csv.length() == 0){
				csv.append('\'').append(token).append('\'');
			}
			else{
				csv.append(',').append('\'').append(token).append('\'');
			}
		}
		return csv.toString();
	}
	/** Read the object from Base64 string. */
	public static Object getBeanfromSerializedString(String s) throws IOException, ClassNotFoundException {
		try{
			byte[] data = Base64.decodeBase64(s);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o = ois.readObject();
			ois.close();
			return o;
		}
		catch(Exception e)
		{
			s=s.substring(0,s.length()-1);
			byte[] data = Base64.decodeBase64(s);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o = ois.readObject();
			ois.close();
			return o;
		}
	}  
	
	
	public static boolean isJSONValid(String test)
	{
	    try 
	    {
	        new JSONObject(test);
	    } 
	    catch(JSONException ex) 
	    {
	        try 
	        {
	            new JSONArray(test);
	        } 
	        catch(JSONException e) 
	        {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static int getDataType(String test)
	{
	    try 
	    {
	        new JSONObject(test);
	        return 1;
	    } 
	    catch(JSONException ex) 
	    {
	        try 
	        {
	            new JSONArray(test);
	            return 2;
	        } 
	        catch(JSONException e) 
	        {
	            return 0;
	        }
	    }
	}

	/** Write the object to a Base64 string. */
	public static String setBeantoSerializedString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return new String(Base64.encodeBase64(baos.toByteArray()));
	}
	
	public static String getColorForProductionReadyStatus(Constants.PRODUCTION_READY_STATUS prodStatus) {
		String prodStatusName = prodStatus.getName();
		List<String> colors = Resources.getMenuItems("all", prodStatusName);
		return colors != null ? colors.get(0) : null;
	}
	
	public static String getCategory(String garment,String lifestyle){
		String[] garmentTypeSplit = garment.split("-");
		if(garmentTypeSplit.length == 2){
			if(lifestyle != null && lifestyle.length() > 0){
				return garmentTypeSplit[0] + "-" + lifestyle + "-" + garmentTypeSplit[1];
			}
		}
		return garment;
	}
	
	public static String processSpecialCharacters(String data){
		data = data.replaceAll("\"", "'");
		/*data = data.replaceAll("`", "&#96;");
		data = data.replaceAll("'", "&#39;"); */
		
		//data = data.replaceAll("'", "e_quot");
		 
		data = data.replace("\r", " ").replace("é", "e").replace("á", "a").replace("í","i").replace("ó","o").replace("ñ", "n").replace("ü","u").replace("ú", "u").replace("à", "a").replace("è", "e").replace("ì","i").replace("Ò","o").replace("Ù", "u").trim();
		data = data.replace("–", "-").replace("’", "'").replace("‘", "'").replace("\n", " ");
		
		return data;
	}
	
	public static  void setRoles(HttpSession session, String email, String role){
		List<String> emailIds = Resources.getSystemVariableList(role);
		if(emailIds != null && emailIds.contains(email)){
			session.setAttribute(role, true);
			return;
		}
		session.setAttribute(role, false);
	}
	
	public static String convertFromCamelCaseToDisplayFormat(String text){
		text = text.replaceAll("([A-Z][a-z]+)", " $1");
		text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
		return text;
	}
	
	public static String convertToFormattedString(String str, int type ){
		if(type == 0) {
			return str;
		}
		if(type == 1) {
			try {
				JSONObject jsonObj = new JSONObject(str);
				if(jsonObj.length() == 0){
					return "";
				}
				StringBuffer strBuffer = new StringBuffer();
				Iterator<String>keys = jsonObj.keys();
				strBuffer.append("<table id='changedFieldsCell'><thead><tr><th>Field Name</th><th> Old value </th><th>New value</th></tr></thead>");
				while(keys.hasNext()){
					String key = keys.next();
					Object value = jsonObj.get(key);
					if(value instanceof JSONObject){
						if(((JSONObject) value).has("new_value") && ((JSONObject) value).has("old_value")){
							String oldVal = ((JSONObject) value).getString("old_value");
							String newVal = ((JSONObject) value).getString("new_value");
							strBuffer.append("<tr>");
							strBuffer.append("<td>").append(convertFromCamelCaseToDisplayFormat(key)).append("</td><td>").append(oldVal).append("</td><td>").append(newVal);
							strBuffer.append("</tr>");
						}
					}
				}
				strBuffer.append("</table>");
				return strBuffer.toString();
			} catch (JSONException e) {
				return "";
			}
		}
		if(type == 2){
			StringBuffer strBuffer = new StringBuffer("");
			
			try {
				JSONArray jsonArray = new JSONArray(str);
				for (int i = 0; i < jsonArray.length(); i++){
					strBuffer.append(jsonArray.get(i)).append("<br>");
				}
				return strBuffer.toString();
			} catch (JSONException e) {
				return "";
			}
			
		}
		return "";
	} 
	
	public static String sortCommaSeparatedStrings(String commaSeparatedString) {
		if(commaSeparatedString.equals(""))
			return null;
		String[] arrayOfStrings = commaSeparatedString.split(",");
		Arrays.sort(arrayOfStrings);
		return StringUtils.join(arrayOfStrings,",");
	}
	
	public static<T> List<T> getBeansFromSerializableList(List<Serializable> serList) {
		if(serList == null) return null;
		List<T> beans = new ArrayList<T>();
		for(int i = 0; i < serList.size(); i++) {
			beans.add((T)serList.get(i));
		}
		return beans;
	}
}
