package tech.asmussen.cql.structure;

public class Data {
	
	private Object value;
	
	public Data(Object value) {
		
		setValue(value);
	}
	
	public Object getValue() {
		
		return value;
	}
	
	public void setValue(Object value) {
		
		this.value = value;
	}
}
