package delivery;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Delivery {

	public static enum OrderStatus {
		NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED
	}

	LinkedHashMap<Integer, Client> customers = new LinkedHashMap<>();
	LinkedHashMap<String, Item> menu = new LinkedHashMap<>();
	LinkedHashMap<Integer, Order> orders = new LinkedHashMap<>();

	/**
	 * Creates a new customer entry and returns the corresponding unique ID.
	 * 
	 * The ID for the first customer must be 1.
	 * 
	 * 
	 * @param name
	 *            name of the customer
	 * @param address
	 *            customer address
	 * @param phone
	 *            customer phone number
	 * @param email
	 *            customer email
	 * @return unique customer ID (positive integer)
	 */
	public int newCustomer(String name, String address, String phone, String email) throws DeliveryException {
		for (Client customer : customers.values()) {
			if (customer.getEmail().equals(email))
				throw new DeliveryException();
		}
		customers.put(customers.size() + 1, new Client(customers.size() + 1, name, address, email, phone));
		return customers.size();
	}

	/**
	 * Returns a string description of the customer in the form:
	 * 
	 * <pre>
	 * "NAME, ADDRESS, PHONE, EMAIL"
	 * </pre>
	 * 
	 * @param customerId
	 * @return customer description string
	 */
	public String customerInfo(int customerId) {
		return customers.get(customerId).toString();
	}

	/**
	 * Returns the list of customers, sorted by name
	 * 
	 */
	public List<String> listCustomers() {
		return customers.values().stream().sorted((x, y) -> x.getName().compareTo(y.getName())).map(x -> x.toString())
				.collect(Collectors.toList());
	}

	/**
	 * Add a new item to the delivery service menu
	 * 
	 * @param description
	 *            description of the item (e.g. "Pizza Margherita", "Bunet")
	 * @param price
	 *            price of the item (e.g. 5 EUR)
	 * @param category
	 *            category of the item (e.g. "Main dish", "Dessert")
	 * @param prepTime
	 *            estimate preparation time in minutes
	 */
	public void newMenuItem(String description, double price, String category, int prepTime) {
		menu.put(description, new Item(description, category, prepTime, price));
	}

	/**
	 * Search for items matching the search string. The items are sorted by category
	 * first and then by description.
	 * 
	 * The format of the items is:
	 * 
	 * <pre>
	 * "[CATEGORY] DESCRIPTION : PRICE"
	 * </pre>
	 * 
	 * @param search
	 *            search string
	 * @return list of matching items
	 */
	public List<String> findItem(String search) {
		return menu.values().stream().filter(x -> x.getDescription().toLowerCase().contains(search.toLowerCase()))
				.sorted((x, y) -> x.getDescription().compareTo(y.getDescription()))
				.sorted((x, y) -> x.getCategory().compareTo(y.getCategory())).map(x -> x.toString())
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new order for the given customer. Returns the unique id of the
	 * order, a progressive integer number starting at 1.
	 * 
	 * @param customerId
	 * @return order id
	 */
	public int newOrder(int customerId) {
		orders.put(orders.size() + 1, new Order(orders.size() + 1, customers.get(customerId)));
		return orders.size();
	}

	/**
	 * Add a new item to the order with the given quantity.
	 * 
	 * If the same item is already present in the order is adds the given quantity
	 * to the current quantity.
	 * 
	 * The method returns the final total quantity of the item in the order.
	 * 
	 * The item is found through the search string as in {@link #findItem}. If none
	 * or more than one item is matched, then an exception is thrown.
	 * 
	 * @param orderId
	 *            the id of the order
	 * @param search
	 *            the item search string
	 * @param qty
	 *            the quantity of the item to be added
	 * @return the final quantity of the item in the order
	 * @throws DeliveryException
	 *             in case of non unique match or invalid order ID
	 */
	public int addItem(int orderId, String search, int qty) throws DeliveryException {
		if (findItem(search).size() > 1 || findItem(search).size() == 0)
			throw new DeliveryException();
		Item add = menu.values().stream().filter(x -> x.getDescription().contains(search)).sorted((x, y) -> x.getDescription().compareTo(y.getDescription()))
				.sorted((x, y) -> x.getCategory().compareTo(y.getCategory())).findFirst().get();
		Order order = getOrderById(orderId);
		if (order.getContent().get(add) == null)
			order.addContent(add, qty);
		else
			order.addContent(add, order.getContent().get(add) + qty);
		return order.getContent().get(add);
	}

	/**
	 * Show the items of the order using the following format
	 * 
	 * <pre>
	 * "DESCRIPTION, QUANTITY"
	 * </pre>
	 * 
	 * @param orderId
	 *            the order ID
	 * @return the list of items in the order
	 * @throws DeliveryException
	 *             when the order ID in invalid
	 */
	public List<String> showOrder(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		return order.getOrderDescription();
	}

	/**
	 * Retrieves the total amount of the order.
	 * 
	 * @param orderId
	 *            the order ID
	 * @return the total amount of the order
	 * @throws DeliveryException
	 *             when the order ID in invalid
	 */
	public double totalOrder(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		return order.getContent().entrySet().stream().mapToDouble(x -> x.getKey().getPrice() * x.getValue()).sum();
	}

	/**
	 * Retrieves the status of an order
	 * 
	 * @param orderId
	 *            the order ID
	 * @return the current status of the order
	 * @throws DeliveryException
	 *             when the id is invalid
	 */
	public OrderStatus getStatus(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		return order.getStatus();
	}

	/**
	 * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
	 * 
	 * Returns the delivery time estimate computed as the sum of:
	 * <ul>
	 * <li>start-up delay (conventionally 5 min)
	 * <li>preparation time (max of all item preparation time)
	 * <li>transportation time (conventionally 15 min)
	 * </ul>
	 * 
	 * @param orderId
	 *            the order id
	 * @return delivery time estimate in minutes
	 * @throws DeliveryException
	 *             when order ID invalid to status not {@code NEW}
	 */
	public int confirm(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		if(order.getStatus() != OrderStatus.NEW) throw new DeliveryException();
		order.setStatus(OrderStatus.CONFIRMED);
		int prepTime=order.getContent().keySet().stream().map(x->x.getPrepTime()).sorted(Comparator.reverseOrder()).findFirst().get();
		return 5 + prepTime + 15;
	}

	/**
	 * Start the order preparation. The status goes from {@code CONFIRMED} to
	 * {@code PREPARATION}
	 * 
	 * Returns the delivery time estimate computed as the sum of:
	 * <ul>
	 * <li>preparation time (max of all item preparation time)
	 * <li>transportation time (conventionally 15 min)
	 * </ul>
	 * 
	 * @param orderId
	 *            the order id
	 * @return delivery time estimate in minutes
	 * @throws DeliveryException
	 *             when order ID invalid to status not {@code CONFIRMED}
	 */
	public int start(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		if(order.getStatus() != OrderStatus.CONFIRMED) throw new DeliveryException();
		order.setStatus(OrderStatus.PREPARATION);
		int prepTime=order.getContent().keySet().stream().map(x->x.getPrepTime()).sorted(Comparator.reverseOrder()).findFirst().get();
		return prepTime + 15 ;
	}

	/**
	 * Begins the order delivery. The status goes from {@code PREPARATION} to
	 * {@code ON_DELIVERY}
	 * 
	 * Returns the delivery time estimate computed as the sum of:
	 * <ul>
	 * <li>transportation time (conventionally 15 min)
	 * </ul>
	 * 
	 * @param orderId
	 *            the order id
	 * @return delivery time estimate in minutes
	 * @throws DeliveryException
	 *             when order ID invalid to status not {@code PREPARATION}
	 */
	public int deliver(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		if(order.getStatus() != OrderStatus.PREPARATION) throw new DeliveryException();
		order.setStatus(OrderStatus.ON_DELIVERY);
		return 15;
	}

	/**
	 * Complete the delivery. The status goes from {@code ON_DELIVERY} to
	 * {@code DELIVERED}
	 * 
	 * 
	 * @param orderId
	 *            the order id
	 * @return delivery time estimate in minutes
	 * @throws DeliveryException
	 *             when order ID invalid to status not {@code ON_DELIVERY}
	 */
	public void complete(int orderId) throws DeliveryException {
		Order order = getOrderById(orderId);
		if(order.getStatus() != OrderStatus.ON_DELIVERY) throw new DeliveryException();
		order.setStatus(OrderStatus.DELIVERED);
	}

	private Order getOrderById(int orderId) throws DeliveryException {
    	Order order = orders.get(orderId);
		if (order == null)
			throw new DeliveryException();
		return order;
	}

	/**
	 * Retrieves the total amount for all orders of a customer.
	 * 
	 * @param customerId
	 *            the customer ID
	 * @return total amount
	 */
	public double totalCustomer(int customerId) {
		orders.values().stream().filter(x-> x.getClient().getId()==customerId).mapToDouble(x-> {
			try {
				return totalOrder(x.getId());
			} catch (DeliveryException e) {
				e.printStackTrace();
				return 0;
			}
			}).forEach(System.out::print);
		return orders.values().stream().filter(x-> x.getClient().getId()==customerId).mapToDouble(x-> {
				try {
					return totalOrder(x.getId());
				} catch (DeliveryException e) {
					e.printStackTrace();
					return 0;
				}
				}).sum();
	}

	/**
	 * Computes the best customers by total amount of orders.
	 * 
	 * @return the classification
	 */
	public SortedMap<Double, List<String>> bestCustomers() {
		Map<String,Double> map = orders.values().stream().collect(Collectors.toMap((Order x)->x.getClient().toString(),(Order z)-> totalCustomer(z.getClient().getId()),(x,y)->{
			if(x.equals(y))
				return y.doubleValue();
			else
				return x.doubleValue();
		}));
		return map.entrySet().stream().collect(Collectors.groupingBy(x->x.getValue(),TreeMap::new, Collectors.mapping(x -> x.getKey(), Collectors.toList()) ) );
	}

	// NOT REQUIRED
	//
	// /**
	// * Computes the best items by total amount of orders.
	// *
	// * @return the classification
	// */
	// public List<String> bestItems(){
	// return null;
	// }
	//
	// /**
	// * Computes the most popular items by total quantity ordered.
	// *
	// * @return the classification
	// */
	// public List<String> popularItems(){
	// return null;
	// }

}
