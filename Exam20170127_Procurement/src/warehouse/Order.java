package warehouse;

public class Order {
	private Product prod;
	private int quantity;
	private Supplier supp;
	private String code;
	private boolean delivered;
	
	public Order(Product prod, int quantity, Supplier supp, String code) {
		super();
		this.prod = prod;
		this.quantity = quantity;
		this.supp = supp;
		this.code=code;
		this.delivered=false;
	}

	public String getCode(){
		return code;
	}
	
	public boolean delivered(){
		return delivered;
	}

	public void setDelivered() throws MultipleDelivery {
		if(delivered)throw new MultipleDelivery();
		delivered=true;
		prod.increaseQuantity(quantity);
	}
	
	public String toString(){
		return "Order " + code + " for " + String.valueOf(quantity) + " of " + prod.getCode() + 
				" : " + prod.getDescription() + " from " + supp.getNome();
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Supplier getSupp() {
		return supp;
	}

	public void setSupp(Supplier supp) {
		this.supp = supp;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
