package com.zz.bms.utils.base.data;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class User  implements Serializable{

    private String name;
    private int code ;
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", addr='" + addr + '\'' +
                '}';
    }
}
