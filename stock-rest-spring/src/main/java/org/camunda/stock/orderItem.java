package org.camunda.stock;

public class orderItem extends stockItem{
	private int orderQuantity;
	
	public orderItem() {}

	public orderItem(String name, int orderQuantity) {
		this.setName(name);
		this.orderQuantity = orderQuantity;
	}

	public int getOrderQuantity() {
		return this.orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	@Override
	public String toString() {
		return String.format("Item [name=%s, quantity=%s]", getName(), orderQuantity);
	}
}
