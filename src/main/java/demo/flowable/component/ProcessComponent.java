package demo.flowable.component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.flowable.bean.ProcessDefinitionVO;
import demo.flowable.bean.ProcessTaskVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenpeidong
 * @date 2020/6/19 3:46 下午
 */
@Slf4j
@Component
public class ProcessComponent {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngine processEngine;


    public List<ProcessDefinitionVO> listProcess() {
        return repositoryService.createProcessDefinitionQuery()
                .list()
                .stream().map(p -> ProcessDefinitionVO.of(p.getId(), p.getKey(), p.getName()))
                .collect(Collectors.toList());
    }


    public String startProcess(String processKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
        log.info("启动流程:{}", instance.getId());
        return instance.getId();
    }

    public List<ProcessTaskVO> getProcessApprovalList(Integer userId) {
        return taskService.createTaskQuery()
                .taskAssignee(userId + "")
                .list()
                .stream()
                .map(t -> ProcessTaskVO.of(t.getId(), t.getName(), t.getAssignee(), t.getProcessInstanceId()))
                .collect(Collectors.toList());
    }

    public void getDiagrams(String processId, HttpServletResponse response) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration conf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = conf.getProcessDiagramGenerator();
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, conf.getActivityFontName(), conf.getLabelFontName(), conf.getAnnotationFontName(), diagramGenerator.getClass().getClassLoader(), 1.0)) {
            try (OutputStream out = response.getOutputStream()) {
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approvalProcess(Integer userId, String key, boolean approved) {
        log.info("approvalProcess. userId:{}, key:{}, approved:{}", userId, key, approved);
        Map<String, Object> params = new HashMap<String, Object>() {{
            this.put(key, approved);
        }};
        taskService.complete(key, params);
    }
}
