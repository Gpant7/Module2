package Interview.test;

//import java.io.*;
//import org.apache.commons.httpclient.*; 
//import org.apache.commons.httpclient.methods.*;

//import org.apache.http.client.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class GetRestApi{
	public String demoGetRESTAPI() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try
	    {
	        //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
	        //Choice depends on type of method you will be invoking.
	        HttpGet getRequest = new HttpGet("https://randomuser.me/api/?results=2&format=xml&hfhgf=gfhg");
	        
	        
	         
	        //Set the API media type in http accept header
	        getRequest.addHeader("accept", "application/xml");
	          
	        //Send the request; It will immediately return the response in HttpResponse object
	        HttpResponse response = httpClient.execute(getRequest);
	         
	        //verify the valid error code first
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 200) 
	        {
	            throw new RuntimeException("Failed with HTTP error code : " + statusCode);
	        }
	         
	        //Now pull back the response object
	        HttpEntity httpEntity = response.getEntity();
	        String apiOutput = EntityUtils.toString(httpEntity);
	         
	        //Lets see what we got from API
	        System.out.println(apiOutput); //<user id="10"><firstName>demo</firstName><lastName>user</lastName></user>
	        return apiOutput;
	        //In realtime programming, you will need to convert this http response to some java object to re-use it.
	        //Lets see how to jaxb unmarshal the api response content
//	        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
//	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//	        User user = (User) jaxbUnmarshaller.unmarshal(new StringReader(apiOutput));
	         
	        //Verify the populated object
//	        System.out.println(user.getId());
//	        System.out.println(user.getFirstName());
//	        System.out.println(user.getLastName());
	    }
	    finally
	    {
	        //Important: Close the connect
	        httpClient.getConnectionManager().shutdown();
	    }
	}
}
