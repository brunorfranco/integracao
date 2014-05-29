package projetoIntegracao.projetoIntegracao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class DownloadBucket {

	public static void main(String[] args) throws Exception {
		
		String apiKey = "AIzaSyC-WNR8-sdx4B1w8-DB_Md90CLF20tKRBQ";
 
		String stringUrl = "http://storage.googleapis.com/bucket-bruno/teste2.txt?key="+apiKey;
		String pathParaDownload = "C:\\Users\\Bruno\\teste2.txt";
		
		downloadFileFromBucket(stringUrl, pathParaDownload); 
	}	
		
	public static void downloadFileFromBucket(String stringUrl, String pathParaDownload) throws IOException {  
		URL somefile = new URL(stringUrl); 
//			URL somefile = new URL("https://console.developers.google.com/m/cloudstorage/b/bucket-bruno/o/lala?authuser=0");
		
		ReadableByteChannel rbc = Channels.newChannel(somefile.openStream());
		FileOutputStream fos = new FileOutputStream(pathParaDownload);
		long start = System.currentTimeMillis();
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	} 
		
}
