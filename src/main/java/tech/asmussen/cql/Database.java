package tech.asmussen.cql;

import tech.asmussen.cql.exceptions.ExistingTableException;

import java.util.ArrayList;

public class Database {
	
	private final String name;
	
	private ArrayList<Table> tables;
	
	public Database(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return name;
	}
	
	public Table[] getTables() {
		
		return tables.toArray(new Table[0]);
	}
	
	public void addTable(Table table) throws ExistingTableException {
		
		if (tables.contains(table)) {
			
			throw new ExistingTableException("The table '" + table + "' already exists in the database '" + getName() + "'!");
		}
		
		tables.add(table);
	}
}
