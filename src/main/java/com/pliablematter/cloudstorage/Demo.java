package com.pliablematter.cloudstorage;

import java.util.List;

public class Demo {

	public static void main(String[] args) throws Exception {
		
//		CloudStorage.createBucket("my-bucket-test1");
		
		CloudStorage.uploadFile("my-bucket-test1", "C:\\Users\\Bruno\\Documents\\Faculdade\\Integracao\\testtext.txt");
		
		CloudStorage.downloadFile("my-bucket-test1", "testtext.txt", "C:\\Users\\Bruno\\Documents\\Faculdade");
		
		List<String> buckets = CloudStorage.listBuckets();
		
		List<String> files = CloudStorage.listBucket("my-bucket-test1");

	}
}
