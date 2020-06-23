package demo.flowable.bean;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author chenpeidong
 * @date 2020/6/19 5:37 下午
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class ProcessTaskVO {
    @NonNull
    private String id;
    @NonNull
    private String key;
    @NonNull
    private String assignee;
    @NonNull
    private String processId;
}
