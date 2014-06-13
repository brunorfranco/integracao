package bibliotecaPullTask.bibliotecaPullTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class PullQueue {

	public static Boolean send(String message) throws Exception {
		
		if(message == null || message.isEmpty()){
			System.out.println("Entre com uma mensagem valida.");
			return false;
		}
		
		long tamanhoMaximoMensagem = 64 *1000;
		if(message.length() > tamanhoMaximoMensagem){
			System.out.println("Entre com uma mensagem valida.");
			return false;
		}
		
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");

		BufferedReader br = null;

		PostMethod method = new PostMethod(
				"http://1-dot-pullqueuebruno.appspot.com/criamensagem");
		method.addParameter(new NameValuePair("msg", message));

		try {
			int returnCode = client.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err
						.println("The Post method is not implemented by this URI");
				method.getResponseBodyAsString();
				return false;
			} else {
				br = new BufferedReader(new InputStreamReader(
						method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					System.err.println(readLine);
				}
				return true;
			}
		} catch (Exception e) {
			System.err.println(e);
			return false;
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
		}
	}
	
	public static String receive(String message) throws Exception {
		
		if(message == null || message.isEmpty()){
			System.out.println("Entre com uma mensagem valida.");
			return null;
		}
		
		long tamanhoMaximoMensagem = 64 *1000;
		if(message.length() > tamanhoMaximoMensagem){
			System.out.println("Entre com uma mensagem valida.");
			return null;
		}
		
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");

		BufferedReader br = null;

		PostMethod method = new PostMethod(
				"http://1-dot-pullqueuebruno.appspot.com/consomemensagem");
		method.addParameter(new NameValuePair("msg", message));

		try {
			int returnCode = client.executeMethod(method);
			

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err
						.println("The Post method is not implemented by this URI");
				method.getResponseBodyAsString();
				return null;
			} else {
				br = new BufferedReader(new InputStreamReader(
						method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					System.err.println(readLine);
				}
				return readLine;
			}
		} catch (Exception e) {
			System.err.println(e);
			return null;
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
		}

	}
	
}
