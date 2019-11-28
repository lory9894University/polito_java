package groups;

import java.util.TreeMap;

public class Product {
	private String name;
	private TreeMap<String, Supplier> suppliers = new TreeMap<>();
	public Product(String name, TreeMap<String, Supplier>supplier) {
		super();
		this.name = name;
		this.suppliers=new TreeMap<>(supplier);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TreeMap<String, Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(TreeMap<String, Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
	

}
