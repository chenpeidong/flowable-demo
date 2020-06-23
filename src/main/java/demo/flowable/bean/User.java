package demo.flowable.bean;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chenpeidong
 * @date 2020/6/19 3:23 下午
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class User {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
}
