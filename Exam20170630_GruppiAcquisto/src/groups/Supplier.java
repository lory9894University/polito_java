package groups;

import java.util.TreeMap;

public class Supplier {
	private String name;
	private TreeMap<String,Product> products = new TreeMap<>();
	private int nBids;

	public Supplier(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeMap<String, Product> getProducts() {
		return products;
	}

	public void setProducts(TreeMap<String, Product> products) {
		this.products = products;
	}
	public void addProduct(Product product) {
		this.products.put(product.getName(), product);
	}

	public int getNBids() {
		return nBids;
	}

	public void setnBids(int nBids) {
		this.nBids = nBids;
	}
	
	
}
