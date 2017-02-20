package ylc.tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by robertpicyu on 2017/2/20.
 */
public class HttpClientManager {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private static HttpClient httpClient = null;

    /**
     * httpClient的可配置内容：
     * 1、连接池：
     *    最大连接数
     *    每个路由对应的最大连接
     * 2、超时：
     *     等待连接池的时间
     *     请求建立等待延时
     *     request等待延时
     *     连接结束后的存活时间（keep alive时间）
     * 3、连接重用
     *     连接重用的条件
     */
    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();

        httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).setKeepAliveStrategy(
                new DefaultConnectionKeepAliveStrategy() {
                    @Override
                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                        long keepalive = super.getKeepAliveDuration(response, context);
                        if (keepalive == -1) {
                            keepalive = 5000;
                        }
                        return keepalive;
                    }
                }).setConnectionReuseStrategy(new DefaultConnectionReuseStrategy() {
                    @Override
                    public boolean keepAlive(final HttpResponse response, final HttpContext context) {
                        boolean keekAlive = false;
                        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                            keekAlive = super.keepAlive(response, context);
                        }
                        return keekAlive;
                    }
                }
        ).build();
    }

    public static void main(String[] args){
        String postUrl = "";
        HttpPost request = new HttpPost(postUrl);
        request.addHeader("headerXXX","valueXXX");

        try {
            StringEntity myEntity = new StringEntity("entry",  Charset.forName("UTF-8"));
            request.setEntity(myEntity);
            HttpResponse response = httpClient.execute(request);

            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (status == HttpStatus.SC_OK) {
                EntityUtils.toString(entity, Charset.forName("UTF-8"));
            } else {
                String reason = null;
                if (entity != null) {
                    reason = EntityUtils.toString(entity, Charset.forName("UTF-8"));
                } else {
                }
                if (HttpStatus.SC_UNPROCESSABLE_ENTITY == status) {
                } else {
                }
            }
        } catch (IOException e) {
        } catch (RuntimeException e) {
        } finally {
            request.abort();
        }
    }
}
