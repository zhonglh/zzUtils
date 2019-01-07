package com.zz.bms.util.httppool;

import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zz.bms.util.httppool.apache.ApacheMediaUploadRequestExecutor;
import com.zz.bms.util.httppool.jodd.JoddHttpMediaUploadRequestExecutor;
import com.zz.bms.util.httppool.okhttp.OkHttpMediaUploadRequestExecutor;

/**
 * 上传媒体文件请求执行器.
 * 请求的参数是File, 返回的结果是String
 * @author Administrator
 */
public abstract class MediaUploadRequestExecutor<H, P> implements RequestExecutor<JSONObject, File> {
  protected RequestHttp<H, P> requestHttp;

  public MediaUploadRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, File data, ResponseHandler<JSONObject> handler) throws RuntimeException, IOException {
    handler.handle(this.execute(uri, data));
  }

  public static RequestExecutor<JSONObject, File> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaUploadRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddHttpMediaUploadRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkHttpMediaUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
