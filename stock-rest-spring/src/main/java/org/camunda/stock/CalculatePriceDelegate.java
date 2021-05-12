package org.camunda.stock;

import java.math.BigDecimal;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class CalculatePriceDelegate implements JavaDelegate {

	RestOperations restTemplate;

	public CalculatePriceDelegate(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		//access process variable
		String name = execution.getVariable("item").toString();
		int quantity = (((Long) execution.getVariable("quantity")).intValue());

		Order item = new Order(name, quantity);

		//call REST service to attempt order
		Response response = (restTemplate.postForObject("http://localhost:8080/stock/price", item , Response.class));
		//example.substring(example.lastIndexOf("/") + 1));
		String message = response.getMessage();
		//Remove '£' to allow for manipulation as BigDecimal
		String price = message.substring(message.lastIndexOf("£") +1);
		//Convert to BigDecimal
		BigDecimal decimalPrice = new BigDecimal(price);
		//calculate price multiplied by quantity
		BigDecimal calculateOrder = decimalPrice.multiply(new BigDecimal(item.getQuantity())); 
		//Convert back to String and add currency symbol
		String finalPrice = "£" + calculateOrder.toString();
        //set response variables
        execution.setVariable("orderTotal", finalPrice);
	}
}

