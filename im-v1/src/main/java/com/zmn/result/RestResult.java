package com.zmn.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RestResult<T> {

    private boolean isSuccess;
    private int code;
    private String msg;
    private T data;

    public static <T> RestResult<T> success(){
        return new RestResult<>(true,200,"ok",null);
    }
    public static <T> RestResult<T> success(T data){
        return new RestResult<>(true,200,"ok",data);
    }
    public static<T> RestResult<T> fail(String msg){
        return new RestResult<>(false,400,msg,null);
    }
}
