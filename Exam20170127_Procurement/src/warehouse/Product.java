package warehouse;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Product {
	private String code, description;
	private int quantity;
	private TreeMap<String,Supplier> suppliers = new TreeMap<>();
	private TreeMap<String,Order> orders = new TreeMap<>();
	
	public Product(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public String getCode(){
		return code;
	}

	public String getDescription(){
		return description;
	}
	
	public void setQuantity(int quantity){
		this.quantity=quantity;
	}

	public void decreaseQuantity(){
		this.quantity--;
	}

	public int getQuantity(){
		return quantity;
	}

	public List<Supplier> suppliers(){
		return suppliers.values().stream().collect(Collectors.toList());
	}
	
	public void addSupplier(Supplier supplier) {
		suppliers.put(supplier.getCodice(), supplier);
	}

	public List<Order> pendingOrders(){
		return orders.values().stream().filter(x->!x.delivered()).sorted((x,y)->y.getQuantity()-x.getQuantity()).collect(Collectors.toList());
	}
	public void newOrder(Order order) {
		orders.put(order.getCode(), order);
	}
	public void increaseQuantity(int qt) {
		quantity+=qt;
	}

}
