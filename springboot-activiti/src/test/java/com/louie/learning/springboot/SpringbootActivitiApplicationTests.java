package com.louie.learning.springboot;

import org.activiti.engine.*;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootActivitiApplicationTests {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Test
    public void contextLoads() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("apply", "admin");
        variables.put("approve", "admin");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variables);
		String pid=processInstance.getId();

		Task task= taskService.createTaskQuery().processInstanceId(pid).singleResult();
		taskService.complete(task.getId());

		Task task2=taskService.createTaskQuery().processInstanceId(pid).singleResult();
        variables.put("pass", false);
		taskService.complete(task2.getId(),variables);

		System.out.println(pid);
    }

}
