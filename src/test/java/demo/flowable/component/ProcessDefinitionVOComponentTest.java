package demo.flowable.component;

import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProcessDefinitionVOComponentTest {

    @Autowired
    private ProcessComponent processComponent;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void test() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        System.out.println(processDefinitions);
        for (ProcessDefinition definition : processDefinitions) {
            log.info("id:{}, key:{}, name:{}", definition.getId(), definition.getKey(), definition.getName());
        }
        System.out.println("----------------------");
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance instance : list) {
            log.info("{}", instance);
        }
        System.out.println("----------------------");
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("2").list();
        for (Task task : tasks) {
            log.info("id:{}, name:{}, assignee:{}", task.getId(), task.getName(), task.getAssignee());
        }

    }

    @Test
    public void test2() {
        taskService.complete("12503");
    }
}