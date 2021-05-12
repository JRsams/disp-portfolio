package org.camunda.test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;


public class TestOrderOutcome {

	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();

	@Test
	@Deployment(resources = {"orderProcess.bpmn"})
	public void orderSuccessful (){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderResult", "succsess");
		// Given we create a new process instance
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
		
		assertThat(processInstance).isStarted();
		assertThat(processInstance).isWaitingAt("orderSuccess");
	}
	
	@Test
	@Deployment(resources = {"orderProcess.bpmn"})
	public void orderFailureEnd (){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderResult", "failure");
		variables.put("shouldRestock", false);
		// Given we create a new process instance
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
		
		assertThat(processInstance).isStarted();
		assertThat(processInstance).isWaitingAt("outOfStock");
		complete(task(processInstance));
		// Complete the request reorder task reach the order failed end event
		complete(task(processInstance));
		// Check that the end event has been reached
		assertThat(processInstance).isEnded();
	}
	
	@Test
	@Deployment(resources = {"orderProcess.bpmn"})
	public void orderErrorEnd (){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderResult", "error");
		variables.put("shouldReorder", false);
		// Given we create a new process instance
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
		
		assertThat(processInstance).isStarted();
		assertThat(processInstance).isWaitingAt("orderError");
		// Complete the check order error task
		complete(task(processInstance));
		// Complete the request reorder task reach the order error end event
		complete(task(processInstance));
		// Check that the end event has been reached
		assertThat(processInstance).isEnded();
	}
	
	@Test
	@Deployment(resources = {"orderProcess.bpmn"})
	public void orderErrorLoopback (){
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderResult", "error");
		variables.put("shouldReorder", true);
		// Given we create a new process instance
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
		
		assertThat(processInstance).isStarted();
		assertThat(processInstance).isWaitingAt("orderError");
		// Complete the check order error task
		complete(task(processInstance));
		// Complete the request reorder task reach the enter order task
		complete(task(processInstance));
		// Check that the token has returned to enter order
		assertThat(processInstance).isWaitingAt("EnterOrderTask");
	}

}
