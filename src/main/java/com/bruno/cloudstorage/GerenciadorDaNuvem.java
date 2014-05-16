package com.bruno.cloudstorage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

public class GerenciadorDaNuvem {

	private static Properties properties;
	private static Storage storage;

	private static final String ID_PROJETO = "project.id";
	private static final String NOME_APLICACAO = "application.name";
	private static final String ID_CONTA = "account.id";
	private static final String CAMINHO_CHAVE_PRIVADA = "private.key.path";

	public static void uploadArquivo(String bucketName, String filePath)
			throws Exception {

		Storage storage = getStorage();

		StorageObject object = new StorageObject();
		object.setBucket(bucketName);

		File file = new File(filePath);

		InputStream stream = new FileInputStream(file);
		try {
			String contentType = URLConnection
					.guessContentTypeFromStream(stream);
			InputStreamContent content = new InputStreamContent(contentType,
					stream);

			Storage.Objects.Insert insert = storage.objects().insert(
					bucketName, null, content);
			insert.setName(file.getName());

			insert.execute();
		} finally {
			stream.close();
		}
	}
	
	public static void downloadArquivo(String bucketName, String fileName, String destinationDirectory) throws Exception {
		
		File directory = new File(destinationDirectory);
		Storage storage = getStorage();
		
		Storage.Objects.Get get = storage.objects().get(bucketName, fileName);
		FileOutputStream fos = new FileOutputStream (new File(directory.getAbsolutePath() + "/" + fileName)); 
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		get.getMediaHttpDownloader().setDirectDownloadEnabled(false);
		get.executeMediaAndDownloadTo(out);
		
		out.writeTo(fos);
	}

	public static void deleteArquivo(String bucketName, String fileName)
			throws Exception {

		Storage storage = getStorage();
		
		storage.objects().delete(bucketName, fileName).execute();
	}

	public static void criaBucket(String bucketName) throws Exception {

		Storage storage = getStorage();

		Bucket bucket = new Bucket();
		bucket.setName(bucketName);

		storage.buckets().insert(
				getProperties().getProperty(ID_PROJETO), bucket).execute();
	}
	
	public static void deletaBucket(String bucketName) throws Exception {

		Storage storage = getStorage();
		
		storage.buckets().delete(bucketName).execute();
	}
	
	public static List<String> listBucket(String bucketName) throws Exception {
		
		Storage storage = getStorage();
		
		List<String> list = new ArrayList<String>();
		
		List<StorageObject> objects = storage.objects().list(bucketName).execute().getItems();
		if(objects != null) {
			for(StorageObject o : objects) {
				list.add(o.getName());
			}
		}
		
		return list;
	}
	
	public static List<String> listBuckets() throws Exception {
		
		Storage storage = getStorage();
		
		List<String> list = new ArrayList<String>();
		
		List<Bucket> buckets = storage.buckets().list(getProperties().getProperty(ID_PROJETO)).execute().getItems();
		if(buckets != null) {
			for(Bucket b : buckets) {
				list.add(b.getName());
			}
		}
		
		return list;
	}

	private static Properties getProperties() throws Exception {

		if (properties == null) {
			properties = new Properties();
			InputStream stream = GerenciadorDaNuvem.class
					.getResourceAsStream("/bruno.properties");
			try {
				properties.load(stream);
			} catch (IOException e) {
				throw new RuntimeException(
						"cloudstorage.properties must be present in classpath",
						e);
			} finally {
				stream.close();
			}
		}
		return properties;
	}

	private static Storage getStorage() throws Exception {

		if (storage == null) {

			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			List<String> scopes = new ArrayList<String>();
			scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);

			Credential credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(jsonFactory)
					.setServiceAccountId(
							getProperties().getProperty(ID_CONTA))
					.setServiceAccountPrivateKeyFromP12File(
							new File(getProperties().getProperty(
									CAMINHO_CHAVE_PRIVADA)))
					.setServiceAccountScopes(scopes).build();

			storage = new Storage.Builder(httpTransport, jsonFactory,
					credential).setApplicationName(
					getProperties().getProperty(NOME_APLICACAO))
					.build();
		}

		return storage;
	}
}
