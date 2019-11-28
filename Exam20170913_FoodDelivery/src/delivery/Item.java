package delivery;

public class Item {
	private String description, category;
	private int prepTime;
	private double price;
	
	public Item(String description, String category, int prepTime, double price) {
		super();
		this.description = description;
		this.category = category;
		this.prepTime = prepTime;
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "["+category+"] "+ description + " : " + String.format("%.2f", price);
	}
	
	
}
