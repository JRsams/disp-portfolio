package org.camunda.stock;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class RestDelegate implements JavaDelegate {

    RestOperations restTemplate;

    public RestDelegate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

	@Override
    public void execute(DelegateExecution execution) throws Exception {

        //access process variable
        String name = execution.getVariable("item").toString();
        int quantity = (((Long) execution.getVariable("quantity")).intValue());
        
        orderItem item = new orderItem(name, quantity);

        //call REST service to attempt order
        Response response = (restTemplate.postForObject("http://localhost:8080/stock/order", item , Response.class));
        
        //set response variables
        execution.setVariable("orderMessage", response.getMessage());
        execution.setVariable("orderResult", response.getResult());
	}
}
