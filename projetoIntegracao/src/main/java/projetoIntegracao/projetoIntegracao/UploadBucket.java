package projetoIntegracao.projetoIntegracao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

public class UploadBucket {

	public static void main(String[] args) throws Exception {
		String filePath = "C:\\Users\\Bruno\\teste2.txt";
//		String filePath = "C:\\Program Files\\DVD Maker";
		
		String fileName = "teste2.txt";
		uploadFileToBucket(filePath, fileName);
	}

	public static void uploadFileToBucket(String filePath, String fileName) throws Exception {
		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");

		BufferedReader br = null;

		File input = new File(filePath);
		long tamanhoEmBytesEquivalenteACemMegaBytes = 100 * 1048576;
		
		if(input.length() > tamanhoEmBytesEquivalenteACemMegaBytes){
			System.out.println("Tamanho do arquivo excede 100MB.");
			return;
		}

		PostMethod method = new PostMethod(
				"https://www.googleapis.com/upload/storage/v1/b/bucket-bruno/o?uploadType=media&name=" + fileName );
		method.addParameter("uploadType", "media");
		method.addParameter("name", fileName);
		method.setRequestEntity(new InputStreamRequestEntity(
				new FileInputStream(input), input.length()));
		// method.setRequestHeader("Content-type",
		// "image/png; charset=ISO-8859-1");
		method.setRequestHeader("Content-type", "application/octet-stream");

		try {
			int returnCode = client.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err
						.println("The Post method is not implemented by this URI");
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(
						method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					System.err.println(readLine);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
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
