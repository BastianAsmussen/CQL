package tech.asmussen.cql;

import tech.asmussen.cql.algorithms.BinarySearch;
import tech.asmussen.cql.exceptions.DataFormatException;
import tech.asmussen.cql.exceptions.NoResultException;

import java.util.Arrays;

public class Table {
	
	private final Database database;
	
	private String name;
	
	private String[] columns;
	private final Data[][] data;
	
	private boolean isDataSorted;
	
	public Table(Database database, String name, String[] columns) {
		
		this.database = database;
		this.name = name;
		
		this.columns = columns;
		this.data = new Data[columns.length][0];
		
		isDataSorted = false;
	}
	
	public Database getDatabase() {
		
		return database;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		
		for (Table table : database.getTables()) {
			
			if (table.getName().equals(name)) {
				
				throw new IllegalArgumentException("A table with the name '" + name + "' already exists!");
			}
		}
		
		this.name = name;
	}
	
	public String[] getColumns() {
		
		return columns;
	}
	
	public void setColumns(String[] columns) {
		
		// TODO: Resize the data array based on the new columns.
		
		this.columns = columns;
	}
	
	public Object[] getValues(int index) {
		
		return data[index];
	}
	
	public Object getValue(int index, int column) {
		
		return data[index][column].getValue();
	}
	
	public void insert(int index, Object[] data) throws DataFormatException {
		
		if (data.length != columns.length) {
			
			throw new DataFormatException("The amount of data doesn't match the amount of columns!");
		}
		
		for (int i = 0; i < columns.length; i++) {
			
			this.data[index][i] = new Data(data[i]);
		}
		
		// Assume that the data is now unsorted.
		isDataSorted = false;
	}
	
	public Object searchFor(Object value) throws NoResultException {
		
		if (!isDataSorted) sortData();
		
		int index = BinarySearch.binarySearch(Arrays.stream(data).toArray(), value);
		
		if (index == -1) throw new NoResultException("No result found!");
		
		return data[index];
	}
	
	private void sortData() {
		
		if (isDataSorted) return;
		
		Arrays.sort(data);
		
		isDataSorted = true;
	}
}
