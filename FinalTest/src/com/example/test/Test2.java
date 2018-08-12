package com.example.test;

import org.junit.Test;

public class Test2 {

	@Test
	public void testFinal(){
		Dinosaur dinosaur = new Dinosaur();
		int j = dinosaur.i++;
		System.out.println(dinosaur.i);
		System.out.println(j);
	}
}

final class  Dinosaur{
	int i = 8;
}
