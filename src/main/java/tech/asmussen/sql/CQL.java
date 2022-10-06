package tech.asmussen.sql;

import java.io.IOException;
import java.util.Scanner;

public class CQL {
	
	public static final  String AUTHOR = "Bastian Asmussen";
	public static final String VERSION = "1.0.0";
	public static final String LICENSE = "MIT";
	
	private static boolean isExiting = false;
	
	public static void main(String[] args) {
		
		clearScreen();
		
		Scanner scanner = new Scanner(System.in);
		
		while (!isExiting) {
			
			System.out.print("CQL> ");
			String input = scanner.nextLine();
			
			handleCommand(input);
		}
		
		scanner.close();
		
		System.out.println("Exiting...");
	}
	
	public static void handleCommand(String input) {
		
		if (input.isEmpty()) return;
		
		switch (input) {
			
			case "help" -> printHelp();
			case "docs" -> printDocs();
			case "clear" -> clearScreen();
			case "exit" -> isExiting = true;
			case "ascii" -> ASCII.printArt();
			default -> System.out.println("Unknown command, type 'help' for help.");
		}
	}
	
	public static void clearScreen() {
		
		final String os = System.getProperty("os.name");
		
		try {
			
			if (os.contains("Windows")) {
				
				new ProcessBuilder("cmd", "/c", "cls")
						.inheritIO()
						.start()
						.waitFor();
				
			} else {
				
				new ProcessBuilder("clear")
						.inheritIO()
						.start()
						.waitFor();
			}
			
		} catch (IOException | InterruptedException e) {
			
			System.out.println("Failed to clear screen! (" + e.getMessage() + ")");
		}
	}
	
	public static void printHelp() {
		
		System.out.println("┌─ CQL - Custom Query Language");
		System.out.println("├─── Author:  " + AUTHOR);
		System.out.println("├─── Version: " + VERSION);
		System.out.println("├─── License: " + LICENSE);
		System.out.println("│");
		System.out.println("├─ Commands");
		System.out.println("├─── 'help'  Print this message.");
		System.out.println("├─── 'docs'  Get a link to the documentation.");
		System.out.println("├─── 'clear' Clear the screen.");
		System.out.println("└─── 'exit'  Exit the program.");
	}
	
	public static void printDocs() {
		
		final String link = "https://github.com/BastianAsmussen/CQL/src/main/resources/docs/index.html";
		
		System.out.println("The CQL documentation can be found at '" + link + "'!");
	}
}
