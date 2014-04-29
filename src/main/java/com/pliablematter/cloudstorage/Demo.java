package com.pliablematter.cloudstorage;

import java.util.List;

public class Demo {

	public static void main(String[] args) throws Exception {
		
//		CloudStorage.createBucket("bucket-bruno");
		
		CloudStorage.uploadFile("bucket-bruno", "C:\\Users\\Bruno\\Documents\\Faculdade\\Integracao\\testtext2.txt");
		
		CloudStorage.downloadFile("bucket-bruno", "testtext2.txt", "C:\\Users\\Bruno\\Documents\\Faculdade");
		
		List<String> buckets = CloudStorage.listBuckets();
		
		List<String> files = CloudStorage.listBucket("bucket-bruno");

	}
}
