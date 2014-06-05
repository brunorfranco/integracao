package projetoIntegracao.projetoIntegracao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import projetoCriptografia.Criptografia;


public class DownloadBucket {

	public static void main(String[] args) throws Exception {
		
		
		Criptografia crypto = new Criptografia();
		String apiKeyEncrypted =crypto.encryptedValue;
		String apiKey = crypto.decrypt(apiKeyEncrypted);
		
		String nomeArquivoComExtensao = "";
		String pathParaDownload = "";
		
		if(args != null && args.length > 1){
			pathParaDownload = args[0];
			nomeArquivoComExtensao = args[1];
		}
		
		String stringUrl = "http://storage.googleapis.com/bucket-bruno/"+nomeArquivoComExtensao+"?key="+apiKey;
		
		//exemplo
		//String pathParaDownload = "C:\\Users\\Bruno\\Desktop\\arquivoTeste.txt";
		//String nomeArquivoComExtensao = "arquivoTeste.txt";
		
		downloadFileFromBucket(stringUrl, pathParaDownload); 
	}	
		
	public static void downloadFileFromBucket(String stringUrl, String pathParaDownload) throws IOException {  
		URL somefile = new URL(stringUrl); 
		
		ReadableByteChannel rbc = null;
		try {
			rbc = Channels.newChannel(somefile.openStream());
		} catch (IOException e) {
			System.err.println("Ocorreu um erro, verifique a permissão do arquivo no bucket, deve estar habilitado para usuários autenticados.");
			return;
		}
		FileOutputStream fos = new FileOutputStream(pathParaDownload);
		long start = System.currentTimeMillis();
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		long end = System.currentTimeMillis();
		System.out.println("Tempo de download: ");
		System.out.println(end-start);
		fos.close();
	} 
		
}
