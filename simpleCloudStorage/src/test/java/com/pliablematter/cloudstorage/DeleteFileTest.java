package com.pliablematter.cloudstorage;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bruno.cloudstorage.GerenciadorDaNuvem;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteFileTest {
	
	private String bucketName = null;
	
	@Before
	public void before() throws Exception {
		
		bucketName = "simple-cloud-storage-" + UUID.randomUUID().toString();
		GerenciadorDaNuvem.criaBucket(bucketName);
		GerenciadorDaNuvem.uploadArquivo(bucketName, "src/test/resources/test.txt");
	}

	@Test
	public void testDeleteFile() throws Exception {
		
		Assert.assertTrue(GerenciadorDaNuvem.listBucket(bucketName).contains("test.txt"));
		GerenciadorDaNuvem.deleteArquivo(bucketName, "test.txt");
		Assert.assertFalse(GerenciadorDaNuvem.listBucket(bucketName).contains("test.txt"));
	}
	
	@After
	public void after() throws Exception {
		
		try {
			GerenciadorDaNuvem.deleteArquivo(bucketName, "test.txt");
		} catch (GoogleJsonResponseException e) {
			// This will usually throw an exception because test.txt has already been deleted.
			// Just ignore it
		}
		GerenciadorDaNuvem.deletaBucket(bucketName);
	}
}
