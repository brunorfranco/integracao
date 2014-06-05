package bibliotecaGoogleTaskQueue.bibliotecaGoogleTaskQueue;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class GoogleQueueSender {

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

		GetMethod method = new GetMethod(
				"http://1-dot-bruno-teste-queue.appspot.com/criamensagem?msg="+message);

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
	
}
