package projetoIntegracao.projetoIntegracao;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;


public class DownloadBucket {

	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
 
		DownloadBucket http = new DownloadBucket();
 
		downloadFileFromBucket("teste", "teste"); 
//		testUpload();
	}	
	
	// HTTP GET request
		private void sendGet() throws Exception {
			
			String url = "http://storage.googleapis.com/bucket-bruno/arquivoTeste.txt";
	 
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
	 
		}
		
		// HTTP POST request
		private void sendPost() throws Exception {
	 
			String url = "https://www.googleapis.com/upload/storage/v1/b/bucket-bruno/o?uploadType=application/octet-stream&name=testeUpload.txt";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	 
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			String urlParameters = "name=testeUpload2";
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
	 
		}
		
		public static void downloadFileFromBucket(String stringUrl, String pathLocal) throws IOException {  
			URL somefile = new URL("http://storage.googleapis.com/bucket-bruno/lala.txt"); // passar a stringUrl;
//			URL somefile = new URL("https://console.developers.google.com/m/cloudstorage/b/bucket-bruno/o/lala?authuser=0");
			
			ReadableByteChannel rbc = Channels.newChannel(somefile.openStream());
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Bruno\\lala.txt"); // passar o pathLocal;
			long start = System.currentTimeMillis();
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		} 
		
		public static void testUpload() throws Exception {
			HttpClient client = new HttpClient();
		    client.getParams().setParameter("http.useragent", "Test Client");

		    BufferedReader br = null;
		    
		    File input = new File("lala.txt");

		    PostMethod method = new PostMethod("https://www.googleapis.com/upload/storage/v1/b/bucket-bruno/o?uploadType=media&name=lala.txt");
		    method.addParameter("uploadType", "media");
		    method.addParameter("name", "lala.txt");
		    method.setRequestEntity(new InputStreamRequestEntity(
	                new FileInputStream(input), input.length()));
//		    method.setRequestHeader("Content-type", "image/png; charset=ISO-8859-1");
		    method.setRequestHeader("Content-type", "application/octet-stream");
		    
		    try{
		      int returnCode = client.executeMethod(method);

		      if(returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
		        System.err.println("The Post method is not implemented by this URI");
		        method.getResponseBodyAsString();
		      } else {
		        br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
		        String readLine;
		        while(((readLine = br.readLine()) != null)) {
		          System.err.println(readLine);
		      }
		      }
		    } catch (Exception e) {
		      System.err.println(e);
		    } finally {
		      method.releaseConnection();
		      if(br != null) try { br.close(); } catch (Exception fe) {}
		    }

		 }
		
}
