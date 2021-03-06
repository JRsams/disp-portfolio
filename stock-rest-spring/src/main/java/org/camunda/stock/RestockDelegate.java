package org.camunda.stock;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class RestockDelegate implements JavaDelegate {

    RestOperations restTemplate;

    public RestockDelegate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
    
	@Override
    public void execute(DelegateExecution execution) throws Exception {

        //access process variable
        String name = execution.getVariable("item").toString();
        int quantity = (((Long) execution.getVariable("quantity")).intValue());
        
        Order item = new Order(name, quantity);

        //call REST service to attempt order
        Response response = (restTemplate.postForObject("http://localhost:8080/stock/restock", item , Response.class));
        
        //set response variables
        execution.setVariable("restockMessage", response.getMessage());
        execution.setVariable("restockResult", response.getResult());
	}
}
