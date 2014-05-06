package com.bruno.cloudstorage;

import java.util.List;

public class CodigoExemplo {

	public static void main(String[] args) throws Exception {
		
//		GerenciadorDaNuvem.createBucket("bucket-bruno");
		
//		GerenciadorDaNuvem.uploadArquivo("bucket-bruno", "C:\\Users\\Bruno\\Documents\\Faculdade\\Integracao\\ic_launcher-web.png");
		
		GerenciadorDaNuvem.downloadArquivo("bucket-bruno", "ic_launcher-web.png", "C:\\Users\\Bruno\\Documents\\Faculdade");
		
		List<String> buckets = GerenciadorDaNuvem.listBuckets();
		
		List<String> files = GerenciadorDaNuvem.listBucket("bucket-bruno");

	}
}
