package com.bruno.cloudstorage;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Scanner scanner =new Scanner(System.in);
		
		System.out.println("Bem vindo ao sistema de Upload e Download do Bruno");
		System.out.println("Digite 1 para criar um novo bucket e 2 para utilizar o bucket padrão: ");
		
		Integer x = scanner.nextInt();
		while(x != 1 && x != 2){
			System.out.println("Entrada inválida.");
			System.out.println("Digite 1 para criar um novo bucket e 2 para utilizar o bucket padrão: ");
			x = scanner.nextInt();
		}
		
		String bucketName = null;
		if(x == 1){
			System.out.println("Digite o nome do novo bucket: ");
			bucketName = scanner.nextLine();
			while(bucketName == null || bucketName.isEmpty()){
				System.out.println("Digite um nome válido para o novo bucket: ");
				bucketName = scanner.nextLine();
			}
			
			try {
				GerenciadorDaNuvem.criaBucket(bucketName);
			} catch (Exception e) {
				System.out.println("Ocorreu um erro na criação do bucket, talvez já existe um bucket com esse nome.");
				System.out.println("Iremos utilizar o bucket padrão.");
			}
			
		} else if(x == 2){
			//bucket-bruno ja gerado, ira utilizar ele
			bucketName = "bucket-bruno";
		}
		
		String filePath = null;
		System.out.println("Para realizar upload digite o endereço com o nome do arquivo: ");
		filePath = scanner.nextLine();
		
		while(filePath == null || filePath.isEmpty()){
			System.out.println("Digite um endereço válido: ");
			filePath = scanner.nextLine();
		}
		
		GerenciadorDaNuvem.uploadArquivo(bucketName, filePath);
		
		String fileDownload = null;
		System.out.println("Para realizar download digite o nome do arquivo: ");
		fileDownload = scanner.nextLine();
		
		while(fileDownload == null || fileDownload.isEmpty()){
			System.out.println("Digite um endereço válido: ");
			fileDownload = scanner.nextLine();
		}
		
		String newPathFile = null;
		System.out.println("Digite o caminho onde deseja realizar o download: ");
		newPathFile = scanner.nextLine();
		
		while(newPathFile == null || newPathFile.isEmpty()){
			System.out.println("Digite um caminho válido: ");
			newPathFile = scanner.nextLine();
		}
		
		GerenciadorDaNuvem.downloadArquivo(bucketName, fileDownload, newPathFile);
		
		System.out.println("Download realizado com sucesso. ");
		System.out.println("Confira o arquivo na pasta escolhida! ");
		scanner.close();
	}
}
