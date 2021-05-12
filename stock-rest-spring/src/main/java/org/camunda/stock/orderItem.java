package org.camunda.stock;

public class orderItem extends stockItem{
	private int quantity;
	
	public orderItem() {}

	public orderItem(String name, int orderQuantity) {
		this.setName(name);
		this.quantity = orderQuantity;
	}

	public int getOrderQuantity() {
		return this.quantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.quantity = orderQuantity;
	}
	
	@Override
	public String toString() {
		return String.format("Item [name=%s, quantity=%s]", getName(), quantity);
	}
}
