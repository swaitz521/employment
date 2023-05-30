package com.employment.employments.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private boolean success;
    private T data;

    //构造私有类，防止其它类创建对象
    private Result(){

    }
    //返回没有数据
    public static<T> Result<T> success(){
      Result<T> result=new Result<T>();
      result.setSuccess(true);
      result.setCode(ResultCode.SUCCESS);
      result.setMsg("执行成功");
      return result;
    }
    public static<T> Result<T> success(T data){
        Result<T> result=new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMsg("执行成功");
        result.setData(data);
        return result;
    }
//    public static<T> Result<T> success(T data,Throwable throwable){
//        Result<T> result=new Result<T>();
//        result.setSuccess(true);
//        result.setCode(ResultCode.SUCCESS);
//        result.setMsg(throwable.getMessage());
//        result.setData(data);
//        return result;
//    }
    public static<T> Result<T> error(){
        Result<T> result=new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMsg("执行失败");
        return result;
    }
    /**
     * 设置状态码
     * @param code
     * @return
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    /**
     * 设置返回消息
     * @param msg
     * @return
     */
    public Result<T> msg(String msg){
        this.setMsg(msg);
        return this;
    }
    //出现异常的时候调用
    public Result(Throwable e){
       this.msg=e.getMessage();
    }
    /**
     * 是否存在
     * @return
     */
    public static<T> Result<T> exist(){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMsg("执行成功");
        return result;
    }
    public Result<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }
//    public Result<> data(String key, Object value){
//        this.data.put(key, value);
//        return this;
//    }
//
//    public Result data(Map<String, Object> map){
//        this.setData(map);
//        return this;
//    }
}
