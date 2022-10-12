package tech.asmussen.cql.structure;

import tech.asmussen.cql.CQL;
import tech.asmussen.cql.exceptions.ExistingDatabaseException;
import tech.asmussen.cql.exceptions.ExistingServerException;

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
	
	public Database getDatabase(String name) throws ExistingDatabaseException {
		
		for (Database database : databases) {
			
			if (database.getName().equals(name)) {
				
				return database;
			}
		}
		
		throw new ExistingDatabaseException("No database with the name '" + name + "' exists in this server!");
	}
	
	public void addDatabase(Database database) throws ExistingDatabaseException {
		
		for (Database db : databases) {
			
			if (db.getName().equals(database.getName())) {
				
				throw new ExistingDatabaseException("The database '" + database.getName() + "' already exists!");
			}
		}
		
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
		
		for (Database database : databases) {
			
			database.drop();
		}
	}
	
	public void dropDatabase(Database database) {
		
		for (Table table : database.getTables()) {
			
			table.drop();
		}
		
		removeDatabase(database);
	}
}
