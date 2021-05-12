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
		complete(task(processInstance));
		assertThat(processInstance).isEnded();
	}
	
//	@Test
//	@Deployment(resources = {"orderProcess.bpmn"})
//	public void orderFailure (){
//		
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("orderResult", "failure");
//		// Given we create a new process instance
//		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
//				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
//		
//		assertThat(processInstance).isStarted();
//		assertThat(processInstance).isWaitingAt("outOfStock");
//		complete(task(processInstance));
//		assertThat(processInstance).isEnded();
//	}
//	
//	@Test
//	@Deployment(resources = {"orderProcess.bpmn"})
//	public void orderError (){
//		
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("orderResult", "error");
//		// Given we create a new process instance
//		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey(
//				"orderProcess").startAfterActivity("CallRESTServiceTask").setVariables(variables).execute();
//		
//		assertThat(processInstance).isStarted();
//		assertThat(processInstance).isWaitingAt("orderError");
//		complete(task(processInstance));
//		assertThat(processInstance).isEnded();
//	}

}
