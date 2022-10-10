package tech.asmussen.util;

public class Search {
	
	public static int binarySearch(String[] array, String value) {
		
		int low = 0;
		int high = array.length - 1;
		
		while (low <= high) {
			
			int mid = (low + high) / 2;
			int comparison = array[mid].compareTo(value);
			
			if (comparison == 0) return mid;
			else if (comparison < 0) low = mid + 1;
			else high = mid - 1;
		}
		
		return -1;
	}
}
