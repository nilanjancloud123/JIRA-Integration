package RestPost;
import java.io.IOException;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomAutomationBot {
	
	private static Response restPost(String credential, String endpoint, String payload) {
		Response response=null;
		try {
	OkHttpClient client = new OkHttpClient().newBuilder().build();
	MediaType mediaType = MediaType.parse("application/json");
	RequestBody body = RequestBody.create(mediaType, payload);
	Request request = new Request.Builder()
			.url(endpoint)
			.method("POST", body)
			.header("Authorization",credential)
			.header("Content-Type", "Application/json")
			.build();
	response = client.newCall(request).execute();
	//String responseBody = response.body().string();
	
	
	
	}catch(Exception e) {
		System.out.println(response.code());
		
	}
	return response;
	
	
	}
	
	@SuppressWarnings("unchecked")
	public static String createCloudTesCycle(String Credentials, String ProjectKey,String Endpoint,String CycleName) throws IOException {
		Endpoint= Endpoint +"/testcycles";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", CycleName);
		jsonObject.put("projectKey", ProjectKey);
	
	   		//TODO -- call in before suite()
		
		Response response = restPost(Credentials, Endpoint, jsonObject.toString());
		String responsetest=response.body().string();
		Object obj=JSONValue.parse(responsetest); 
		JSONObject jsonObject1 = (JSONObject) obj; 
		String CycleID = (String) jsonObject1.get("key"); 
		return CycleID;
		 
		//return responsetest;
		
	}
	
	@SuppressWarnings("unchecked")
	public static String createDataCenterTestCycle(String user,String Pwd, String ProjectKey,String Endpoint,String CycleName) throws IOException {
		String Credentials=user+":"+Pwd;
		Credentials="Basic " + Base64.getEncoder().encodeToString(Credentials.getBytes());
		Endpoint= Endpoint +"/testrun";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", CycleName);
		jsonObject.put("projectKey", ProjectKey);
	
	   		//TODO -- call in before suite()
		
		Response response = restPost(Credentials, Endpoint, jsonObject.toString());
		String responsetest=response.body().string();
		Object obj=JSONValue.parse(responsetest); 
		JSONObject jsonObject1 = (JSONObject) obj; 
		String CycleID = (String) jsonObject1.get("key"); 
		return CycleID;
		 
		//return responsetest;
		
	}
	/*
	 * public static String testcase() { //TODO -- call in before method() return
	 * fetchResponse.getkey(); }
	 */
	  
	  @SuppressWarnings("unchecked")
	public static boolean testExecutionCloud(String Credentials, String ProjectKey,String Endpoint,String TestCaseKey,String CycleId,String Status,String Comment) { //TODO -- call in after moethod() //
		  	boolean status = false;
		  Endpoint= Endpoint +"/testexecutions";
		  	JSONObject jsonObject = new JSONObject();
			jsonObject.put("projectKey", ProjectKey);
			jsonObject.put("testCaseKey", TestCaseKey);
			jsonObject.put("testCycleKey", CycleId);
			jsonObject.put("statusName", Status);
			jsonObject.put("comment",Comment );	
			Response response=restPost(Credentials, Endpoint, jsonObject.toString());
			Integer code =response.code();
			if (code.equals(201))
			{
				status=true;
			}else {
				status=false;
			}
			return status;
	}
	  @SuppressWarnings("unchecked")
		public static boolean testExecutionDatacentre(String user,String Pwd,String ProjectKey,String Endpoint,String TestCaseKey,String CycleId,String Status,String Comment) { //TODO -- call in after moethod() //
		  		boolean status = false;
		  		String Credentials=user+":"+Pwd;
		  		Credentials="Basic " +Base64.getEncoder().encodeToString(Credentials.getBytes());
		  		Endpoint= Endpoint +"/testrun/"+CycleId+"/testcase/"+TestCaseKey+"/testresult";
		  		JSONObject jsonObject = new JSONObject();
		  		jsonObject.put("status", Status);
				jsonObject.put("comment",Comment);	
		  		Response response=restPost(Credentials, Endpoint, jsonObject.toString());
		  		
		  		Integer code =response.code();
				if (code.equals(201))
				{
					status=true;
				}else {
					status=false;
				}
				return status;
		}
	
}
