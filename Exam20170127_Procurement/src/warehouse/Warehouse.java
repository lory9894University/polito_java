package warehouse;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Warehouse {
	private TreeMap<String,Product> products = new TreeMap<>();
	private TreeMap<String,Supplier> suppliers = new TreeMap<>();
	private TreeMap<String,Order> orders = new TreeMap<>();

	
	public Product newProduct(String code, String description){
		Product product = new Product(code, description);
		products.put(code, product);
		return product;
	}
	
	public Collection<Product> products(){
		return products.values();
	}

	public Product findProduct(String code){
		return products.get(code);
	}

	public Supplier newSupplier(String code, String name){
		Supplier supplier = new Supplier(code,name);
		suppliers.put(code, supplier);
		return supplier;
	}
	
	public Supplier findSupplier(String code){
		return suppliers.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		if(!supp.supplies().contains(prod))throw new InvalidSupplier();
		Order order = new Order(prod, quantity, supp,"ORD" + String.valueOf(orders.size()+1));
		orders.put(order.getCode(), order);
		prod.newOrder(order);
		return order;
	}

	public Order findOrder(String code){
		return orders.get(code);
	}
	
	public List<Order> pendingOrders(){
		return orders.values().stream().filter(x->!x.delivered()).sorted((x,y)->x.getProd().getDescription().compareTo(y.getProd().getDescription())).collect(Collectors.toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
		return orders.values().stream().collect(Collectors.groupingBy((Order x)->x.getProd().getCode(),Collectors.mapping((Order x)->x, Collectors.toList())));
	}
	
	public Map<String,Long> orderNBySupplier(){
	    return orders.values().stream().filter(x->x.delivered()).sorted((x,y)->x.getSupp().getNome().compareTo(y.getSupp().getNome()))
	    		.collect(Collectors.groupingBy(x->x.getSupp().getNome(),Collectors.counting()));
	}
	
	public List<String> countDeliveredByProduct(){
	    return orders.values().stream().filter(x->x.delivered()).collect(Collectors.groupingBy((Order x)->x.getProd().getCode(),Collectors.counting())).entrySet().stream().
	    	    sorted((x,y)->y.getValue().intValue()-x.getValue().intValue()).map(x->x.getKey() + " - " +x.getValue().toString()).collect(Collectors.toList());
	}
}
