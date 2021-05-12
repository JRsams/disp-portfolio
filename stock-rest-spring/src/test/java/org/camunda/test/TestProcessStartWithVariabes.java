package org.camunda.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.processInstanceQuery;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;


public class TestProcessStartWithVariabes {

	@Rule
	public ProcessEngineRule rule = new ProcessEngineRule();
	
	@Test
	@Deployment(resources = {"orderProcess.bpmn"})
	public void processStart() {
		// Given we create a new process instance
		ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
						"orderProcess",
						withVariables("item", "trout", "quantity", 1, "orderResult", "succsess")); 
		assertThat(processInstance).isStarted();
		// Then it should be active
		assertThat(processInstance).isActive();
		// And it should be the only instance
		assertThat(processInstanceQuery().count()).isEqualTo(1);
		// Check that the process is waiting for the first form to be completed
		assertThat(processInstance).isWaitingAt("EnterOrderTask");
		// Check that the process started with the specified variables
		assertThat(processInstance).hasVariables("item","quantity","orderResult");
	}
}
