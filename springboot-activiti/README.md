# springboot-activiti-demo  
![Activiti logo](images/activiti-logo.png)  
参考[Spring Boot与Activiti集成实战](http://wiselyman.iteye.com/blog/2285223)<br/>

说明:只是一个演示项目.
## 1. 流程图的设计(join.bpmn20.xml)
```xml
<?xml version='1.0' encoding='UTF-8'?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
             xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI"
             typeLanguage="http://www.w3.org/2001/XMLSchema" expressionLanguage="http://www.w3.org/1999/XPath"
             targetNamespace="http://www.activiti.org/test">
	<process id="joinProcess" name="Join process" isExecutable="true">
		<startEvent id="startevent1" name="Start">
			<extensionElements>
				<activiti:formProperty id="personId" name="person id" type="long"
				                       required="true"></activiti:formProperty>
				<activiti:formProperty id="compId" name="company Id" type="long"
				                       required="true"></activiti:formProperty>
			</extensionElements>
		</startEvent>
		<endEvent id="endevent1" name="End"></endEvent>
		<userTask id="ApprovalTask" name="Approval Task"
		          activiti:candidateUsers="${joinService.findUsers(execution)}" isForCompensation="true">
			<extensionElements>
				<activiti:formProperty id="joinApproved" name="Join Approved" type="enum">
					<activiti:value id="true" name="Approve"></activiti:value>
					<activiti:value id="false" name="Reject"></activiti:value>
				</activiti:formProperty>
			</extensionElements>
		</userTask>
		<sequenceFlow id="flow1" sourceRef="startevent1" targetRef="ApprovalTask"></sequenceFlow>
		<serviceTask id="AutoTask" name="Auto Task"
		             activiti:expression="${joinService.joinGroup(execution)}"></serviceTask>
		<sequenceFlow id="flow2" sourceRef="ApprovalTask" targetRef="AutoTask"></sequenceFlow>
		<sequenceFlow id="flow3" sourceRef="AutoTask" targetRef="endevent1"></sequenceFlow>
	</process>
</definitions>
```
##2. 程序的主入口

```
import com.louie.learning.springboot.dao.CompRepository;
import com.louie.learning.springboot.dao.PersonRepository;
import com.louie.learning.springboot.model.Comp;
import com.louie.learning.springboot.model.Person;
import com.louie.learning.springboot.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("com.louie.learning.springboot")
@EnableJpaRepositories("com.louie.learning.springboot.dao")
@EntityScan("com.louie.learning.springboot.model")
public class ActivitiApplication {
	@Autowired
	private CompRepository compRepository;
	@Autowired
	private PersonRepository personRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
	
	//初始化模拟数据
	@Bean
	public CommandLineRunner init(final ActivitiService myService) {
		return new CommandLineRunner() {
			public void run(String...strings)throws Exception {
				if (personRepository.findAll().size() == 0) {
					personRepository.save(new Person("wtr"));
					personRepository.save(new Person("wyf"));
					personRepository.save(new Person("admin"));
				}
				if (compRepository.findAll().size() == 0) {
					Comp group = new Comp("great company");
					compRepository.save(group);
					Person admin = personRepository.findByPersonName("admin");
					Person wtr = personRepository.findByPersonName("wtr");
					admin.setComp(group); wtr.setComp(group);
					personRepository.save(admin); personRepository.save(wtr);
				}
			}
		} ;
	}
}
```
## 3. 流程的执行过程


```
package com.louie.learning.springboot.controller;


import com.louie.learning.springboot.service.ActivitiService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;  

@RestController
public class MyRestController {
	@Autowired
	private ActivitiService myService;
	
	//开启流程实例
	@RequestMapping(value = "/process/{personId}/{compId}", method = RequestMethod.GET)
	public void startProcessInstance(@PathVariable Long personId, @PathVariable Long compId) {
		myService.startProcess(personId, compId);
	}
	
	//获取当前人的任务
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
		List<Task> tasks = myService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName()));
		}
		return dtos;
	}
	
	//完成任务
	@RequestMapping(value = "/complete/{joinApproved}/{taskId}", method = RequestMethod.GET)
	public String complete(@PathVariable Boolean joinApproved, @PathVariable String taskId) {
		myService.completeTasks(joinApproved, taskId);
		return "ok";
	}
	
	//Task的dto
	static class TaskRepresentation
	
	{
		private String id;
		private String name;
		
		public TaskRepresentation(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
}
```

## 4. 流程的执行过程
要加入的公司id为1，申请加入的人的id为2:访问

http://localhost:8080/process/2/1

查看数据库表(ACT_RU_TASK ACT_RU_IDENTITYLINK)的变化

http://localhost:8080/tasks?assignee=admin 查看admin用户的任务

访问http://localhost:8080/complete/true/10 完成任务，true为同意(可以选择false)



