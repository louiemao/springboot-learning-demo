package com.louie.learning.springboot;

import com.louie.learning.springboot.dao.CompRepository;
import com.louie.learning.springboot.dao.PersonRepository;
import com.louie.learning.springboot.model.Comp;
import com.louie.learning.springboot.model.Person;
import com.louie.learning.springboot.service.ActivitiService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan("com.louie.learning.springboot")
//@EnableJpaRepositories("com.louie.learning.springboot.dao")
//@EntityScan("com.louie.learning.springboot.model")
public class SpringbootActivitiApplication {
    @Autowired
    private CompRepository compRepository;
    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActivitiApplication.class, args);
    }


    //初始化模拟数据
    @Bean
    public CommandLineRunner init(final ActivitiService myService,
                                  final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {
        return new CommandLineRunner() {
            public void run(String... strings) throws Exception {
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
                    admin.setComp(group);
                    wtr.setComp(group);
                    personRepository.save(admin);
                    personRepository.save(wtr);
                }
                System.out.println("Number of process definitions : "
                        + repositoryService.createProcessDefinitionQuery().count());
                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
                runtimeService.startProcessInstanceByKey("oneTaskProcess");
                System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
            }
        };
    }

}
