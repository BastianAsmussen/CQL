package tech.asmussen.cql;

import tech.asmussen.util.Search;

import java.util.LinkedList;

public class Table {
	private final String name;
	
	
	private final String[] columns;
	private final LinkedList<String> data;
	
	private boolean isDataSorted;
	
	public Table(String name, String... columns) {
		
		this.name = name;
		this.columns = columns;
		
		data = new LinkedList<>();
		isDataSorted = false;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String[] getColumns() {
		
		return columns;
	}
	
	private void sortData() {
		
		if (isDataSorted) return;
		
		data.sort(String::compareTo);
		isDataSorted = true;
	}
	
	public void insert(String data) {
		
		this.data.add(data);
		isDataSorted = false; // Assume that the data is now unsorted.
	}
	
	public String searchFor(String value) {
		
		if (!isDataSorted) sortData();
		
		int index = Search.binarySearch(data.toArray(new String[0]), value);
		
		if (index == -1) return null;
		
		return data.get(index);
	}
}
