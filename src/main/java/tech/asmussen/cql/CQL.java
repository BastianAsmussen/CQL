package tech.asmussen.cql;

import tech.asmussen.cql.misc.ASCII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class CQL {
	
	public static final String AUTHOR = "Bastian Asmussen";
	public static final String VERSION = "1.0.0";
	public static final String LICENSE = "MIT";
	
	public static final Database[] DATABASES = new Database[0];
	
	private static boolean isExiting = false;
	
	
	public static void main(String[] args) {
		
		clearScreen();
		
		checkVersion(false);
		
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
			case "check" -> checkVersion(true);
			case "clear" -> clearScreen();
			case "exit" -> isExiting = true;
			case "ascii" -> ASCII.printArt();
			default -> System.out.println("Unknown command, type 'help' for help.");
		}
	}
	
	public static void checkVersion(boolean returnIfNone) {
		
		String newVersion = VERSION;
		
		try {
			
			URL url = new URL("https://raw.githubusercontent.com/BastianAsmussen/CQL/main/VERSION.txt");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				
				newVersion = line;
			}
			
		} catch (IOException e) {
			
			System.out.println("Failed to check for updates!");
		}
		
		if (!newVersion.equals(VERSION)) {
			
			System.out.println("Version " + newVersion + " of CQL is out! (You're running version " + VERSION + ")");
			System.out.println("You can update to the newest version here: https://github.com/BastianAsmussen/CQL/releases/tag/" + newVersion);
			
		} else {
			
			if (returnIfNone)
				System.out.println("No updates were found!");
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
		System.out.println("├─── 'check' Check for updates.");
		System.out.println("├─── 'clear' Clear the screen.");
		System.out.println("└─── 'exit'  Exit the program.");
	}
	
	public static void printDocs() {
		
		final String link = "https://github.com/BastianAsmussen/CQL/src/main/resources/docs/index.html";
		
		System.out.println("The CQL documentation can be found at '" + link + "'!");
	}
}
