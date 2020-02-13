package org.common.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * HTTP工具类
 *
 * @author nurhier
 */
@Slf4j
public class HttpUtils {
    private HttpUtils() {}

    /**
     * httpClient
     */
    private static CloseableHttpClient httpClient;
    private static PoolingHttpClientConnectionManager cm;
    private final static Object LOCK = new Object();
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(500);
        cm.setDefaultMaxPerRoute(20);
    }

    public static String httpGet(String uri, Map<String, String> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(uri);
        List<NameValuePair> paramList = new LinkedList<>();
        BasicNameValuePair nameValuePair;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            paramList.add(nameValuePair);
        }
        uriBuilder.setParameters(paramList);
        return httpGet(uriBuilder.build().toString());
    }

    public static String httpGet(String url) throws IOException {
        CloseableHttpClient client = getHttpClient();
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                response.close();
                request.abort();
                throw new RuntimeException("HTTP failure");
            }
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, UTF_8);
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("释放链接错误");
                }
            }
        }
    }

    private static synchronized CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
                                                       .setConnectTimeout(5000).setSocketTimeout(5000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();
            scheduler.scheduleAtFixedRate(new IdleConnectionMonitor(cm), (long) 10 * 1000,
                                          (long) 30 * 1000, TimeUnit.MILLISECONDS);
        }
        return httpClient;
    }

    private static final class IdleConnectionMonitor implements Runnable {
        PoolingHttpClientConnectionManager connectionManager;

        private IdleConnectionMonitor(PoolingHttpClientConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            if (connectionManager != null) {
                connectionManager.closeExpiredConnections();
                connectionManager.closeIdleConnections((long) 1000 * 10, TimeUnit.MILLISECONDS);
            }
            if (httpClient == null) {
                scheduler.shutdown();
            }
        }
    }
}
