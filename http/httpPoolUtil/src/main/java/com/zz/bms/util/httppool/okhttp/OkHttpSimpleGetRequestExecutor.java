package com.zz.bms.util.httppool.okhttp;

import com.zz.bms.util.httppool.RequestHttp;
import com.zz.bms.util.httppool.SimpleGetRequestExecutor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Administrator
 */
public class OkHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public OkHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public String execute(String uri, String queryParam) throws RuntimeException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    //得到httpClient
    OkHttpClient client = requestHttp.getRequestHttpClient();
    Request request = new Request.Builder().url(uri).build();
    Response response = client.newCall(request).execute();
    String responseContent = response.body().string();

    return responseContent;
  }

}
