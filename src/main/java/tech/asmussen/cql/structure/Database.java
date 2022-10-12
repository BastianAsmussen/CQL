package tech.asmussen.cql.structure;

import tech.asmussen.cql.exceptions.ExistingTableException;
import tech.asmussen.cql.exceptions.NoSuchTableException;

public class Database {
	
	private final Server server;
	private final String name;
	
	private Table[] tables;
	
	public Database(Server server, String name) {
		
		this.server = server;
		this.name = name;
		
		tables = new Table[0];
	}
	
	public String getName() {
		
		return name;
	}
	
	public Server getServer() {
		
		return server;
	}
	
	public Table[] getTables() {
		
		return tables;
	}
	
	public Table getTable(String name) throws NoSuchTableException {
		
		for (Table table : tables) {
			
			if (table.getName().equals(name)) {
				
				return table;
			}
		}
		
		throw new NoSuchTableException("No table with the name '" + name + "' exists in this database!");
	}
	
	public void addTable(Table table) throws ExistingTableException {
		
		for (Table value : tables) {
			
			if (value.getName().equals(table.getName())) {
				
				throw new ExistingTableException("The table '" + table.getName() + "' already exists!");
			}
		}
		
		Table[] newTables = new Table[tables.length + 1];
		
		System.arraycopy(tables, 0, newTables, 0, tables.length);
		
		newTables[tables.length] = table;
		
		tables = newTables;
	}
	
	public void removeTable(Table table) {
		
		Table[] newTables = new Table[tables.length - 1];
		
		int index = 0;
		for (Table value : tables) {
			
			if (value != table) newTables[index++] = value;
		}
		
		tables = newTables;
	}
	
	public void drop() {
		
		server.dropDatabase(this);
	}
}
