package org.camunda.stock;

public class Order {
	private String name;
	private int quantity;
	
	public Order() {}

	public Order(String name, int orderQuantity) {
		this.name = name;
		this.quantity = orderQuantity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int orderQuantity) {
		this.quantity = orderQuantity;
	}

	@Override
	public String toString() {
		return String.format("Item [name=%s, quantity=%s]", name, quantity);
	}
}
