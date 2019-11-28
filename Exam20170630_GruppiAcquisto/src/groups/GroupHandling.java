package groups;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class GroupHandling {
	TreeMap<String, Product> products= new TreeMap<>();
	TreeMap<String, Supplier> suppliers= new TreeMap<>();
	TreeMap<String, Group> groups = new TreeMap<String, Group>();
	TreeSet<Bid> bids = new TreeSet<>((x,y)->x.getSupplierName().compareTo(y.getSupplierName()));
//R1	
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(products.containsKey(productTypeName))throw new GroupHandlingException();
		TreeMap<String, Supplier> shadowSuppliers= new TreeMap<>();
		for (String string : supplierNames) {
			if(suppliers.containsKey(string))shadowSuppliers.put(string, suppliers.get(string));
			else {
				Supplier sup =new Supplier(string);
				suppliers.put(string, sup);
				shadowSuppliers.put(string, sup);
			}
		}
		Product product= new Product(productTypeName,shadowSuppliers);
		products.put(productTypeName,product);
		for (Supplier supplier : shadowSuppliers.values()) {
			supplier.addProduct(product);
		}
	}
	
	public List<String> getProductTypes (String supplierName) {
		return products.values().stream().filter(x->x.getSuppliers().containsKey(supplierName)).collect(Collectors.mapping(x->x.getName(),toList()));
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(groups.containsKey(name))throw new GroupHandlingException("duplicated group");
		if(!products.containsKey(productName)) throw new GroupHandlingException("product not existent"); 
		@SuppressWarnings("serial")
		TreeSet<String> shadowClients = new TreeSet<String>() {{
			this.addAll(Arrays.asList(customerNames));
		}};
		groups.put(name, new Group(name,shadowClients, productName));
	}
	
	public List<String> getGroups (String customerName) {
        return groups.entrySet().stream().filter(x->x.getValue().getClients().contains(customerName)).collect(mapping(x->x.getKey(), toList()));
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		Group group = groups.get(groupName);
		for (String string : supplierNames) {
			if(!suppliers.containsKey(string))throw new GroupHandlingException("no such suppliers");
			for (Supplier sup : suppliers.entrySet().stream().filter(x->x.getKey().equals(string)).collect(mapping(x->x.getValue(), toList()))) {
				if(!sup.getProducts().containsKey(group.getProduct()))throw new GroupHandlingException("not the right product");
				group.addSupplier(sup);
			}
		}
		
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		Supplier sup = groups.get(groupName).getSuppliers().get(supplierName);
		if(sup==null)throw new GroupHandlingException("not existent supplier");
		bids.add(new Bid(groupName, supplierName, price));
		sup.setnBids(sup.getNBids()+1);
	}
	
	public String getBids (String groupName) {
        return bids.stream().filter(x->x.getGroupName().equals(groupName)).sorted((x,y)->x.getPrice()-y.getPrice()).map(x->x.toString()).reduce((x,y)-> x.concat(",").concat(y)).get();
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		try {
		groups.get(groupName).setVote(customerName, supplierName);
		}catch(GroupHandlingException e) {
			throw e;
		}
	}
	
	public String  getVotes (String groupName) {
        return groups.get(groupName).getVote().entrySet().stream().collect(groupingBy(x->x.getValue(),counting())).entrySet().stream().sorted((x,y)->x.getKey().compareTo(y.getKey())).map(x-> x.getKey() + ":" + x.getValue()).reduce((x,y)->x+","+y).get();
	}
	
	public String getWinningBid (String groupName) {
		if(bids.stream().noneMatch(x->x.getGroupName().equals(groupName)))
        	return null;
		return groups.get(groupName).getVote().entrySet().stream().collect(groupingBy(x->x.getValue(),counting())).entrySet().stream().sorted((x,y)->x.getKey().compareTo(y.getKey()))
				.sorted((x,y)->{
					Bid bid1=null;
					Bid bid2=null;
					for (Bid bid : bids) {
						if(bid.getSupplierName().equals(x.getKey())) bid1=bid;
					}
					for (Bid bid : bids) {
						if(bid.getSupplierName().equals(y.getKey())) bid2=bid;
					}
					return bid1.getPrice()-bid2.getPrice();
				})
				.map(x-> x.getKey() + ":" + x.getValue()).findFirst().get();
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
        return bids.stream().collect(toMap(x->groups.get(x.getGroupName()).getProduct(), x->x.getPrice(),(x,y)-> {
			if(x>y)
				return x;
			else 
				return y;
				}, TreeMap::new));
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
		
        return suppliers.values().stream()
        		.filter(supplier -> supplier.getNBids() > 0)
        		.collect(groupingBy(Supplier::getNBids, () -> new TreeMap<Integer, List<String>>(reverseOrder()),
        			mapping(Supplier::getName,toList())));
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
        return groups.values().stream().collect(toMap(x->x.getProduct(), x->x.getClientNumber(),(x,y)->x+y,TreeMap::new));
	}
	
}
