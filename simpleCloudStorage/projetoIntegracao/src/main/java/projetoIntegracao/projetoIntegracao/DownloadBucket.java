package projetoIntegracao.projetoIntegracao;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class DownloadBucket {

	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
 
		DownloadBucket http = new DownloadBucket();
 
		testUpload();
//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendPost();
//		gravaArquivoDeURL("http://storage.googleapis.com/bucket-bruno/arquivoTeste.txt", "C:\\Users\\Bruno\\testeDownload");
		
//		URL somefile = new URL("http://storage.googleapis.com/bucket-bruno/arquivoTeste.txt");
//		ReadableByteChannel rbc = Channels.newChannel(somefile.openStream());
//		FileOutputStream fos = new FileOutputStream("C:\\Users\\Bruno\\teste2.txt");
//		long start = System.currentTimeMillis();
//		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
//		long end = System.currentTimeMillis();
//		System.out.println(end-start);
 
 
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
		
		public void downloadFileFromBucket(String stringUrl, String pathLocal) throws IOException {  
			URL somefile = new URL("http://storage.googleapis.com/bucket-bruno/arquivoTeste.txt"); // passar a stringUrl;
			ReadableByteChannel rbc = Channels.newChannel(somefile.openStream());
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Bruno\\teste2.txt"); // passar o pathLocal;
			long start = System.currentTimeMillis();
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			long end = System.currentTimeMillis();
			System.out.println(end-start);
		} 
		
		public void uploadFileToBucket(){
			
		}
		
		public static void testUpload() throws Exception {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("https://www.googleapis.com/upload/storage/v1/b/bucket-bruno/o");

		    MultipartEntity reqEntity = new MultipartEntity(
		        HttpMultipartMode.BROWSER_COMPATIBLE);

		    reqEntity.addPart("string_field",
		        new StringBody("field value"));

		    FileBody bin = new FileBody(
		        new File("test.png"));
		    reqEntity.addPart("attachment_field", bin );

		    httppost.setEntity(reqEntity);

		    System.out.println("executing request " + httppost.getRequestLine());
		    HttpResponse response = httpclient.execute(httppost);
		    HttpEntity resEntity = response.getEntity();

		    if (resEntity != null) {
		        String page = EntityUtils.toString(resEntity);
		        System.out.println("PAGE :" + page);
		    }
		}
}
