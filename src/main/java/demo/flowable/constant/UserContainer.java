package demo.flowable.constant;

import java.util.HashMap;
import java.util.Map;

import demo.flowable.bean.User;

/**
 * @author chenpeidong
 * @date 2020/6/19 3:26 下午
 */
public class UserContainer {

    public static User get(Integer userId) {
        return USER_MAP.get(userId);
    }

    private static final Map<Integer, User> USER_MAP = new HashMap<Integer, User>() {{
        this.put(1, User.of(1, "CEO"));
        this.put(2, User.of(2, "直属领导"));
        this.put(3, User.of(3, "HR"));
        this.put(4, User.of(4, "张三"));
    }};

}
