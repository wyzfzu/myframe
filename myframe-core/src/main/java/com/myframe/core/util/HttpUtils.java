
package com.myframe.core.util;

import com.google.common.base.Preconditions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Http工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class HttpUtils {
	private static final Logger logger = LogUtils.get();

    public static enum Method {
        GET, POST
    }
    // https默认端口
    private static final int DEFAULT_HTTPS_PORT = 443;

    private static final HttpClient httpClient = HttpClients.createDefault();

    private static int httpsPort = DEFAULT_HTTPS_PORT;

    public static void setHttpsPost(int port) {
        Preconditions.checkArgument(port > 0, "端口不能小于0！");
        httpsPort = port;
    }

    public static int getHttpsPort() {
        return httpsPort;
    }

    public static String get(final String uri) {
         return getResponseText(getClient(), uri);
    }

    public static InputStream getStream(final String uri) {
         return getStream(uri, Method.GET);
    }

    public static InputStream getStream(final String uri, Method method) {
        return getResponseStream(getClient(), uri, method);
    }

    public static String post(final String uri, final Map<String, String> params) {
         return doPost(getClient(), uri, params);
    }

    public static String postJson(final String uri, String param) {
        return doPost(getClient(), uri, param);
    }

    ///////////////////////////////// 私有方法 ////////////////////////////

    private static HttpClient getClient() {
        return httpClient;
    }

    private static String getText(HttpResponse resp) {
        if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            logger.error("请求数据返回不正确：" + resp.getStatusLine().getReasonPhrase());
            return "";
        }

        HttpEntity entity = resp.getEntity();
        String respContent = "";
        if (entity != null) {
            try {
                respContent = EntityUtils.toString(entity, Encoding.UTF8);
                EntityUtils.consume(entity);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return respContent;
    }

	private static String getResponseText(HttpClient client, final String uri) {
        HttpGet get = new HttpGet(uri);
        try {
			return getText(client.execute(get));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
            get.releaseConnection();
        }
	}

	private static InputStream getResponseStream(HttpClient client, final String uri, Method method) {
        HttpRequestBase request = null;

        try {
            if (method == Method.GET) {
                request = new HttpGet(uri);
            } else if (method == Method.POST) {
                request = new HttpPost(uri);
            } else {
                throw new RuntimeException("Unknown Method : " + method);
            }
            HttpResponse resp = client.execute(request);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				logger.error("请求数据返回不正确：" + resp.getStatusLine().getReasonPhrase());
				return null;
			}

			HttpEntity entity = resp.getEntity();
			InputStream respStream = null;
			if (entity != null) {
				respStream = entity.getContent();
			}
			byte[] bytes = StreamUtils.toByteArray(respStream);
			byte[] retBytes = Arrays.copyOf(bytes, bytes.length);
			EntityUtils.consume(entity);
            StreamUtils.closeQuietly(respStream);
			return new ByteArrayInputStream(retBytes);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
	}

	private static HttpEntity wrapParams(final Map<String, String> params) {
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		if (CollectUtils.isNotEmpty(params)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		return new UrlEncodedFormEntity(nameValuePair, Encoding.UTF8);
	}

	private static String doPost(HttpClient client, final String uri, Map<String, String> params) {
        return doPost(client, uri, wrapParams(params));
    }

    private static String doPost(HttpClient client, final String uri, String param) {
        return doPost(client, uri, new StringEntity(param, ContentType.APPLICATION_JSON));
    }

    private static String doPost(HttpClient client, final String uri, HttpEntity entity) {
        HttpPost post = new HttpPost(uri);
        try {
            post.setEntity(entity);
            return getText(client.execute(post));
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }
    }

}
