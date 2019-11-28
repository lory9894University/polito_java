package delivery;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import delivery.Delivery.OrderStatus;

public class Order {
	private int id;
	private Client client;
	private LinkedHashMap<Item, Integer> content = new LinkedHashMap<>();
	private Delivery.OrderStatus status;
	
	public Order(int id, Client client) {
		super();
		status=OrderStatus.NEW;
		this.id = id;
		this.client = client;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LinkedHashMap<Item, Integer> getContent() {
		return content;
	}

	public void addContent(Item item, int qt) {
		content.put(item, qt);
	}
	
	public List<String> getOrderDescription(){
		return content.entrySet().stream().map(x-> x.getKey().getDescription() + x.getValue().toString()).collect(Collectors.toList());
	}

	public Delivery.OrderStatus getStatus() {
		return status;
	}

	public void setStatus(Delivery.OrderStatus status) {
		this.status = status;
	}
	
	
}
