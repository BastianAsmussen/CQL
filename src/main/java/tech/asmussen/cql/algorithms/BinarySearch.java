package tech.asmussen.cql.algorithms;

public class BinarySearch {
	
	/**
	 * Searches a sorted array for a value using the binary search algorithm.
	 *
	 * @param array The array to search.
	 * @param value The value to search for.
	 * @return The index of the value in the array, or -1 if the value is not in the array.
	 * @since 1.0.0
	 */
	public static int binarySearch(Object[] array, Object value) {
		
		int low = 0; // The lowest index of the array.
		int high = array.length - 1; // The highest index of the array.
		
		while (low <= high) { // While the lowest index is lower than or equal to the highest index:
			
			int mid = (low + high) / 2; // The middle index of the array.
			
			if (array[mid].equals(value)) return mid; // If the value at the middle index is the value we're looking for, return the middle index.
			else if (array[mid].hashCode() < value.hashCode()) low = mid + 1; // If the value at the middle index is lower than the value we're looking for, set the lowest index to the middle index plus one.
			else high = mid - 1; // If the value at the middle index is higher than the value we're looking for, set the highest index to the middle index minus one.
		}
		
		return -1; // If the value is not found, return -1.
	}
}
