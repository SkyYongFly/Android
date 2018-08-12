package com.example.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	
	public static void main(String[] args) throws IOException {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket serverSocket;
				try {
					while(true){
					serverSocket = new ServerSocket(1234);
					Socket socket = serverSocket.accept();
					System.out.println(socket);
					socket.close();
					}
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}).start();
		
		
	}
	

}
