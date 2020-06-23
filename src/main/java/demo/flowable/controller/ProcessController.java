package demo.flowable.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.flowable.bean.ProcessDefinitionVO;
import demo.flowable.bean.ProcessTaskVO;
import demo.flowable.bean.Response;
import demo.flowable.component.ProcessComponent;

/**
 * @author chenpeidong
 * @date 2020/6/19 3:32 下午
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessComponent processComponent;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/list")
    public Response getProcessList() {
        List<ProcessDefinitionVO> vos = processComponent.listProcess();
        return Response.success(vos);
    }

    @GetMapping("/start")
    public Response startProcess(@RequestParam String processKey) {
        String instanceId = processComponent.startProcess(processKey);
        return Response.success(instanceId);
    }

    @GetMapping("/approval/list")
    public Response getProcessApprovalList(@RequestParam Integer userId) {
        List<ProcessTaskVO> list = processComponent.getProcessApprovalList(userId);
        return Response.success(list);
    }

    @GetMapping("/approval")
    public Response approvalProcess(Integer userId, String key, boolean approved) {
        processComponent.approvalProcess(userId, key, approved);
        return Response.success();
    }

    @GetMapping("/user")
    public Response getUserProcessList() {
        return Response.success();
    }

    @RequestMapping("/diagrams")
    public void getDiagrams(@RequestParam String processId, HttpServletResponse response) {
        processComponent.getDiagrams(processId, response);
    }


}
