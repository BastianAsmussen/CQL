package tech.asmussen.cql;

import tech.asmussen.cql.exceptions.CQLInstallException;

import java.io.File;

public class Install {
	
	private final File root;
	
	public Install() {
		
		File rootFile;
		
		try {
			
			rootFile = defineRoot();
			
		} catch (CQLInstallException e) {
			
			rootFile = null;
			
			System.out.println("Failed to install CQL, exiting...");
			System.out.println("Error message: " + e.getMessage());
			
			System.exit(1);
		}
		
		root = rootFile;
	}
	
	public File getRoot() {
		
		return root;
	}
	
	private static File defineRoot() throws CQLInstallException {
		
		final String os = System.getProperty("os.name").toUpperCase();
		
		String directory;
		
		if (os.contains("WIN")) { // Windows:
			
			directory = System.getenv("AppData");
			
		} else { // Linux or Mac:
			
			directory = System.getProperty("user.home") + "/Library/Application Support";
		}
		
		directory += "/CQL";
		
		File file = new File(directory);
		
		if (!file.exists()) {
			
			if (file.mkdirs()) {
				
				System.out.println("Installed CQL in '" + file.getAbsolutePath() + "'!");
				
			} else {
				
				throw new CQLInstallException("Couldn't create the directory '" + file.getAbsolutePath() + "'!");
			}
		}
		
		return file;
	}
}
