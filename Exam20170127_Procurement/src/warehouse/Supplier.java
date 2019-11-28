package warehouse;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Supplier {
	private String code, name;
	private TreeMap<String,Product> products = new TreeMap<>();	
	
	public Supplier(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCodice(){
		return code;
	}

	public String getNome(){
		return name;
	}
	
	public void newSupply(Product product){
		products.put(product.getCode(), product);
		product.addSupplier(this);
	}
	
	public List<Product> supplies(){
		return products.values().stream().sorted((x,y)->x.getDescription().compareTo(y.getDescription())).collect(Collectors.toList());
	}
}
