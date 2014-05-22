package com.pliablematter.cloudstorage;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bruno.cloudstorage.GerenciadorDaNuvem;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteBucketTest {
	
	String bucketName = null;
	
	@Before
	public void before() throws Exception {
		bucketName = "simple-cloud-storage-" + UUID.randomUUID().toString();
		GerenciadorDaNuvem.criaBucket(bucketName);
	}

	@Test
	public void testDeleteBucket() throws Exception {
		
		List<String> buckets = GerenciadorDaNuvem.listBuckets();
		Assert.assertTrue(buckets.contains(bucketName));
		
		GerenciadorDaNuvem.deletaBucket(bucketName);
		
		buckets = GerenciadorDaNuvem.listBuckets();
		Assert.assertFalse(buckets.contains(bucketName));
	}
	
	@After
	public void after() throws Exception {
		
		// Bucket should already be deleted, but try again just to make sure it's cleaned up
		try {
			GerenciadorDaNuvem.deletaBucket(bucketName);
		} catch (GoogleJsonResponseException e) {
			// Already deleted. Ignore.
		}
	}
}
