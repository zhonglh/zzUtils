package com.zz.bms.util.httppool;

import java.io.File;
import java.io.IOException;

import com.zz.bms.util.httppool.apache.ApacheMediaDownloadRequestExecutor;
import com.zz.bms.util.httppool.jodd.JoddHttpMediaDownloadRequestExecutor;
import com.zz.bms.util.httppool.okhttp.OkHttpMediaDownloadRequestExecutor;

/**
 * 下载媒体文件请求执行器.
 * 请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 * @author Administrator
 */
public abstract class BaseMediaDownloadRequestExecutor<H, P> implements RequestExecutor<File, String> {
  protected RequestHttp<H, P> requestHttp;
  protected File tmpDirFile;

  public BaseMediaDownloadRequestExecutor(RequestHttp<H, P> requestHttp, File tmpDirFile) {
    this.requestHttp = requestHttp;
    this.tmpDirFile = tmpDirFile;
  }

  @Override
  public void execute(String uri, String data, ResponseHandler<File> handler) throws RuntimeException, IOException {
    handler.handle(this.execute(uri, data));
  }

  public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case JODD_HTTP:
        return new JoddHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      case OK_HTTP:
        return new OkHttpMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
      default:
        return null;
    }
  }

}
