package com.zz.bms.util.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
public class RequestHeader {

    /**
     * 判断是否微信
     * @param req
     * @return
     */
    public static boolean isWx(HttpServletRequest req){


        String userAgent = req.getHeader("user-agent").toLowerCase();
        if(userAgent.indexOf("micromessenger")>-1){
            //微信客户端
            return true;
        }else{
            return false;
        }

    }
}
