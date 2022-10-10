package tech.asmussen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ASCII {
	
	public static void printArt() {
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						Objects.requireNonNull(ASCII.class.getResourceAsStream("/misc/ASCII.txt"))
				)
		);
		
		try {
			
			String line;
			while ((line = reader.readLine()) != null) {
				
				System.out.println(line);
			}
			
			reader.close();
			
		} catch (IOException e) {
			
			System.out.println("Couldn't find the ASCII art! :(");
		}
	}
}
