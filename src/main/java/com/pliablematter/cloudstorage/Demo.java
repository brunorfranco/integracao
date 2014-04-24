package com.pliablematter.cloudstorage;

import java.util.List;

public class Demo {

	public static void main(String[] args) throws Exception {
		
		CloudStorage.createBucket("my-bucket");
		
		CloudStorage.uploadFile("my-bucket", "C:\\Users\\Bruno\\Documents\\Faculdade\\Integracao\\some-text.txt");
		
		CloudStorage.downloadFile("my-bucket", "some-text.txt", "C:\\Users\\Bruno\\Documents\\Faculdade\\Integracao\\downloads");
		
		List<String> buckets = CloudStorage.listBuckets();
		
		List<String> files = CloudStorage.listBucket("my-bucket");

	}
}
