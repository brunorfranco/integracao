package com.pliablematter.cloudstorage;


import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListBucketsTest {
	
	String bucketName = null;
	
	@Before
	public void before() throws Exception {
		bucketName = "simple-cloud-storage-" + UUID.randomUUID().toString();
		GerenciadorDaNuvem.criaBucket(bucketName);
	}

	@Test
	public void testListBuckets() throws Exception {
		List<String> buckets = GerenciadorDaNuvem.listBuckets();
		Assert.assertTrue(buckets.contains(bucketName));
	}
	
	@After
	public void after() throws Exception {
		GerenciadorDaNuvem.deletaBucket(bucketName);
	}
}
