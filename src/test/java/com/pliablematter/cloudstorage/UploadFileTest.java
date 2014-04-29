package com.pliablematter.cloudstorage;

import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UploadFileTest {
	
	private String bucketName = null;
	
	@Before
	public void before() throws Exception {
		
		bucketName = "simple-cloud-storage-" + UUID.randomUUID().toString();
		GerenciadorDaNuvem.criaBucket(bucketName);
	}

	@Test
	public void testUploadFile() throws Exception {
				
		GerenciadorDaNuvem.uploadArquivo(bucketName, "src/test/resources/test.txt");
		List<String> objects = GerenciadorDaNuvem.listBucket(bucketName);
		Assert.assertTrue(objects.contains("test.txt"));
	}
	
	@After
	public void after() throws Exception {
		
		GerenciadorDaNuvem.deleteArquivo(bucketName, "test.txt");
		GerenciadorDaNuvem.deletaBucket(bucketName);
	}
}