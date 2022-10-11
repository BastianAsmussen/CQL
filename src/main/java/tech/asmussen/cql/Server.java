package tech.asmussen.cql;

import java.util.Arrays;

public class Server {
	
	private String name;
	
	private Database[] databases;
	
	public Server(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		// If any server has the same name, throw an exception.
		for (Server server : CQL.getServers()) {
			
			if (server.getName().equals(name)) {
				
				throw new IllegalArgumentException("A server with the name '" + name + "' already exists!");
			}
		}
		
		this.name = name;
	}
	
	public Database[] getDatabases() {
		
		return databases;
	}
	
	public void addDatabase(Database database) {
		
		databases = Arrays.copyOf(databases, databases.length + 1);
		databases[databases.length - 1] = database;
	}
	
	public void removeDatabase(Database database) {
		
		Database[] newDatabases = new Database[databases.length - 1];
		
		int index = 0;
		for (Database db : databases) {
			
			if (db != database) newDatabases[index++] = db;
		}
		
		databases = newDatabases;
	}
	
	public void dropDatabases() {
		
		databases = new Database[0];
	}
}
