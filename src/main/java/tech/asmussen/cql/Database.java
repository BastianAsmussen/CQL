package tech.asmussen.cql;

import tech.asmussen.cql.exceptions.ExistingTableException;

public class Database {
	
	private final Server server;
	private final String name;
	
	private Table[] tables;
	
	public Database(Server server, String name) {
		
		this.server = server;
		this.name = name;
		
		tables = new Table[0];
	}
	
	public Server getServer() {
		
		return server;
	}
	
	public String getName() {
		
		return name;
	}
	
	public Table[] getTables() {
		
		return tables;
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
}
