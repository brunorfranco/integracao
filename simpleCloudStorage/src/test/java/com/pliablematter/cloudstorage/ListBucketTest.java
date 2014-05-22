package com.pliablematter.cloudstorage;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bruno.cloudstorage.GerenciadorDaNuvem;

public class ListBucketTest {
	
	private String bucketName = null;
	
	@Before
	public void before() throws Exception {
		
		bucketName = "simple-cloud-storage-" + UUID.randomUUID().toString();
		GerenciadorDaNuvem.criaBucket(bucketName);
		GerenciadorDaNuvem.uploadArquivo(bucketName, "src/test/resources/test.txt");
	}

	@Test
	public void testListBucket() throws Exception {
		
		List<String> files = GerenciadorDaNuvem.listBucket(bucketName);
		Assert.assertTrue(files.contains("test.txt"));
	}
	
	@After
	public void after() throws Exception {
		
		GerenciadorDaNuvem.deleteArquivo(bucketName, "test.txt");
		GerenciadorDaNuvem.deletaBucket(bucketName);
	}
}