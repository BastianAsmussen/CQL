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
	
	public void createTable(String name, String[] columns) throws ExistingTableException {
		
		for (Table table : tables) {
			
			if (table.getName().equals(name)) {
				
				throw new ExistingTableException("The table '" + table.getName() + "' already exists!");
			}
		}
		
		Table table = new Table(this, name, columns);
		
		Table[] newTables = new Table[tables.length + 1];
		
		System.arraycopy(tables, 0, newTables, 0, tables.length);
		
		newTables[tables.length] = table;
		
		tables = newTables;
	}
	
	public void dropTable(String name) throws NoSuchTableException {
		
		for (Table table : tables) {
			
			if (table.getName().equals(name)) {
				
				Table[] newTables = new Table[tables.length - 1];
				
				int index = 0;
				
				for (Table value : tables) {
					
					if (!value.getName().equals(name)) {
						
						newTables[index++] = value;
					}
				}
				
				tables = newTables;
				
				return;
			}
		}
		
		throw new NoSuchTableException("No table with the name '" + name + "' exists in this database!");
	}
}
