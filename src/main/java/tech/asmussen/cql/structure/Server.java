package tech.asmussen.cql.structure;

import tech.asmussen.cql.CQL;
import tech.asmussen.cql.exceptions.ExistingDatabaseException;
import tech.asmussen.cql.exceptions.ExistingServerException;
import tech.asmussen.cql.exceptions.NoSuchDatabaseException;

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
	
	public void setName(String name) throws ExistingServerException {
		
		// If any server has the same name, throw an exception.
		for (Server server : CQL.getServers()) {
			
			if (server.getName().equals(name)) {
				
				throw new ExistingServerException("A server with the name '" + name + "' already exists!");
			}
		}
		
		this.name = name;
	}
	
	public Database[] getDatabases() {
		
		return databases;
	}
	
	public Database getDatabase(String name) throws NoSuchDatabaseException {
		
		for (Database database : databases) {
			
			if (database.getName().equals(name)) {
				
				return database;
			}
		}
		
		throw new NoSuchDatabaseException("No database with the name '" + name + "' exists in this server!");
	}
	
	public void createDatabase(String name) throws ExistingDatabaseException {
		
		for (Database database : databases) {
			
			if (database.getName().equals(name)) {
				
				throw new ExistingDatabaseException("The database '" + name + "' already exists!");
			}
		}
		
		Database database = new Database(this, name);
		
		databases = Arrays.copyOf(databases, databases.length + 1);
		databases[databases.length - 1] = database;
	}
	
	public void dropDatabase(String name) throws NoSuchDatabaseException {
		
		for (Database db : databases) {
			
			if (db.getName().equals(name)) {
				
				Database[] newDatabases = new Database[databases.length - 1];
				
				int index = 0;
				for (Database database : databases) {
					
					if (!database.getName().equals(name)) {
						
						newDatabases[index++] = database;
					}
				}
				
				databases = newDatabases;
				
				return;
			}
		}
		
		throw new NoSuchDatabaseException("No database with the name '" + name + "' exists in this server!");
	}
	
	public void dropDatabases() {
		
		for (Database database : databases) {
			
			try {
				
				dropDatabase(database.getName());
				
			} catch (NoSuchDatabaseException ignored) { }
		}
	}
}
