package demo.flowable.bean;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author chenpeidong
 * @date 2020/6/19 4:33 下午
 */
@Data
@RequiredArgsConstructor(staticName = "of")
public class ProcessDefinitionVO {
    @NonNull
    private String id;
    @NonNull
    private String key;
    @NonNull
    private String name;
}
