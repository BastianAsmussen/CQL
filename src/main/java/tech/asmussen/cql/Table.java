package tech.asmussen.cql;

import tech.asmussen.cql.algorithms.BinarySearch;
import tech.asmussen.cql.exceptions.DataFormatException;
import tech.asmussen.cql.exceptions.NoResultException;

import java.util.Arrays;
import java.util.LinkedList;

public class Table {
	private final String name;
	
	private final String[] columns;
	private final Data[][] data;
	
	private boolean isDataSorted;
	
	public Table(String name, String[] columns) {
		
		this.name = name;
		
		this.columns = columns;
		this.data = new Data[columns.length][0];
		
		isDataSorted = false;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String[] getColumns() {
		
		return columns;
	}
	
	public Object[] getValues(int index) {
		
		LinkedList<Object> values = new LinkedList<>();
		
		for (int i = 0; i < data[index].length; i++) {
			
			values.add(data[index][i].getValue());
		}
		
		return values.toArray();
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
