package demo.flowable.bean;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Response<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    private Response() {
        this.code = 200;
        this.message = "Success!";
    }

    private Response(T data) {
        this();
        this.data = data;
    }

    private Response(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T t) {
        return new Response<>(t);
    }

    public static <T> Response<T> error(int code, String message) {
        return new Response<>(code, message);
    }

    public static <T> Response<T> error() {
        return new Response<>(500, "服务器君开小差了,请稍后再试...");
    }

}
