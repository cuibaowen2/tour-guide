package model.basemodel;

import java.io.Serializable;

import common.Constants;

public class BaseModel<T> implements IModel, Serializable {

    /**
     * 自定义错误码
     */
    public String code;
    /**
     * 自定义业务状态码
     */
    public String bizcode;
    /**
     * 消息提示
     */
    public String msg;
    /**
     * 泛型实体类
     */
    public T data;

    public boolean isOk() {
        return bizcode.equals(Constants.BIZCODE_OK);
    }
}