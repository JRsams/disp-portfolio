package org.camunda.stock;

import java.util.ArrayList;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import net.minidev.json.JSONObject;

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
        //((Long) userService.getAttendanceList(currentUser)).intValue();

        //call REST service to attempt order
        String response = (restTemplate.postForObject("http://localhost:8080/stock/order", item ,String.class));
        System.out.println(response);
        execution.setVariable("orderResponse", response);

//        //access object in Java, store a new process variable
 //       if (response != null) execution.setVariable("email", response);
//
//        //serialize a java object into JSON and stored it in this way so Camunda knows it is JSON
//        ObjectValue adJson = Variables
//                .objectValue(response.getAd())
//                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
//                .create();
//        //add json object value as process variable
//        execution.setVariable("Ad", adJson);
    }
}
