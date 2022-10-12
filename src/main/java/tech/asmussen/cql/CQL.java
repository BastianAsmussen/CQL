package tech.asmussen.cql;

import tech.asmussen.cql.exceptions.NoSuchServerException;
import tech.asmussen.cql.misc.ASCII;
import tech.asmussen.cql.structure.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class CQL {
	
	public static final String AUTHOR = "Bastian Asmussen";
	public static final String VERSION = "1.0.0";
	public static final String LICENSE = "MIT";
	public static final File ROOT = new Install().getRoot();
	
	private static Server[] servers;
	
	private static boolean isExiting = false;
	
	public static void main(String[] args) {
		
		clearScreen();
		
		Thread backgroundThread = new Thread(() -> checkVersion(false)); // Check for updates in the background.
		
		backgroundThread.setDaemon(true);
		backgroundThread.start();
		
		initialize();
		
		Scanner scanner = new Scanner(System.in);
		
		while (!isExiting) {
			
			System.out.print("CQL> ");
			String input = scanner.nextLine();
			
			handleCommand(input);
		}
		
		scanner.close();
		
		System.out.println("Exiting...");
	}
	
	public static Server[] getServers() {
		
		return servers;
	}
	
	private static void initialize() {
		
		final long startTime = System.currentTimeMillis();
		
		System.out.println("Initializing CQL...");
		
		File[] serverFiles = new File(ROOT, "Servers").listFiles();
		
		if (serverFiles != null) {
			
			for (File server : serverFiles) {
				
				if (server.isDirectory()) {
					
					serverFiles = Arrays.copyOf(serverFiles, serverFiles.length + 1);
					serverFiles[serverFiles.length - 1] = new File(server.getName());
				}
			}
			
			servers = new Server[serverFiles.length];
			
			for (int i = 0; i < serverFiles.length; i++) {
				
				servers[i] = new Server(serverFiles[i].getName());
			}
			
		} else {
			
			servers = new Server[0];
		}
		
		System.out.println("Initialized in " + (System.currentTimeMillis() - startTime) + " ms!");
	}
	
	private static void handleCommand(String input) {
		
		if (input.isEmpty()) return;
		
		switch (input) {
			
			case "help" -> printHelp();
			case "docs" -> printDocs();
			case "update" -> checkVersion(true);
			case "clear" -> clearScreen();
			case "exit" -> isExiting = true;
			case "ascii" -> ASCII.printArt();
			case "confirm" -> {
				
				String serverName = new Server("Test").getName();
				
				try {
					
					writeChanges(serverName);
					
				} catch (NoSuchServerException e) {
					
					System.out.println("A server with the name '" + serverName + "' does not exist!");
				}
			}
			default -> System.out.println("Unknown command, type 'help' for help.");
		}
	}
	
	private static void writeChanges(String serverName) throws NoSuchServerException {
		
		final long startTime = System.currentTimeMillis();
		
		for (Server server : servers) {
			
			if (server.getName().equals(serverName)) {
				
				// server.writeChanges();
				
				System.out.println("Wrote changes to " + server.getName() + " in " + (System.currentTimeMillis() - startTime) + " ms!");
				
				return;
			}
		}
		
		throw new NoSuchServerException("A server with the name '" + serverName + "' doesn't exist!");
	}
	
	private static void printHelp() {
		
		final Server[] servers = getServers();
		
		final int serverCount = servers.length;
		final int databaseCount = Arrays.stream(servers)
										.mapToInt(server -> server.getDatabases().length)
										.sum();
		final int tableCount = Arrays.stream(servers)
									 .mapToInt(server -> Arrays.stream(server.getDatabases())
									 .mapToInt(database -> database.getTables().length).sum())
									 .sum();
		
		System.out.println("┌─ CQL - Custom Query Language");
		System.out.println("├─── Author:  " + AUTHOR);
		System.out.println("├─── License: " + LICENSE);
		System.out.println("├─── Version: " + VERSION);
		System.out.println("│");
		System.out.println("├─ Commands");
		System.out.println("├─── 'help'     Print this message.");
		System.out.println("├─── 'docs'     Get a link to the documentation.");
		System.out.println("├─── 'update'   Check for updates.");
		System.out.println("├─── 'clear'    Clear the screen.");
		System.out.println("├─── 'exit'     Exit the program.");
		System.out.println("├─── 'confirm'  Confirm the server, database and table changes.");
		System.out.println("├─── 'rollback' Cancel the server, database and table changes.");
		System.out.println("│");
		System.out.println("├─ Information");
		System.out.println("├─── Install Path:    " + ROOT.getAbsolutePath());
		System.out.println("├─── Total Servers:   " + serverCount);
		System.out.println("├─── Total Databases: " + databaseCount);
		System.out.println("└─── Total Tables:    " + tableCount);
	}
	
	private static void printDocs() {
		
		final String link = "https://github.com/BastianAsmussen/CQL/src/main/resources/docs/index.html";
		
		System.out.println("┌─ The CQL documentation can be found here:");
		System.out.println("└─── " + link);
	}
	
	private static void checkVersion(boolean isManualCheck) {
		
		String newVersion = VERSION;
		
		try {
			
			URL url = new URL("https://raw.githubusercontent.com/BastianAsmussen/CQL/main/VERSION.txt");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				
				newVersion = line;
			}
			
		} catch (IOException e) {
			
			if (isManualCheck) {
				
				System.out.println("Failed to check for updates!");
			}
		}
		
		if (!newVersion.equals(VERSION)) {
			
			System.out.println("Version " + newVersion + " of CQL is out! (You're running version " + VERSION + ")");
			System.out.println("You can update to the newest version here: https://github.com/BastianAsmussen/CQL/releases/tag/" + newVersion);
			
		} else {
			
			if (isManualCheck) {
				
				System.out.println("No updates were found!");
			}
		}
	}
	
	private static void clearScreen() {
		
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
}
