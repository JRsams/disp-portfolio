package stockBPM.stockManager;

public class stockItem {
	private String name;
	private double price;
	private int quantity;
	
	public stockItem() {}
	
	public stockItem(String name, double price, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return String.format("Item [name=%s, price=%s, quantity=%s]", name, price, quantity);
	}
	

}
