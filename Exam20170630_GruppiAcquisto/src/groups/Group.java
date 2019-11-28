package groups;

import java.util.TreeMap;
import java.util.TreeSet;

public class Group {
	private String name;
	private TreeSet<String> clients= new TreeSet<>();
	private TreeMap<String,Supplier> suppliers = new TreeMap<>();
	private TreeMap<String,String> vote = new TreeMap<>();
	private String product;
	
	public Group(String name, TreeSet<String> clients,String product) {
		super();
		this.name = name;
		this.clients = clients;
		this.product=product;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TreeSet<String> getClients() {
		return clients;
	}
	public void setClients(TreeSet<String> clients) {
		this.clients = clients;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void addSupplier(Supplier supplier) {
		suppliers.put(supplier.getName(),supplier);
	}
	public TreeMap<String, Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(TreeMap<String, Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	public void setVote(String client, String supplier) throws GroupHandlingException{
		if(!clients.contains(client)||!suppliers.containsKey(supplier)) throw new GroupHandlingException();
		
		vote.put(client, supplier);
	}
	public TreeMap<String, String> getVote() {
		return vote;
	}
	public Long getClientNumber() {
		return (long)clients.size();
	}
	
}
