package com.wang.crud.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    private int Code;
    private String msg;
private Map<String,Object> extend=new HashMap<>();

public static Msg success(){
    Msg result = new Msg();
    result.setCode(100);
    result.setMsg("处理成功");
    return result;
}

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败");
        return result;
    }
public Msg add(String key,Object value){
          this.getExtend().put(key,value);
          return this;
}

    public int getCode() {
        return this.Code;
    }

    public void setCode(final int code) {
        this.Code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return this.extend;
    }

    public void setExtend(final Map<String, Object> extend) {
        this.extend = extend;
    }
}
