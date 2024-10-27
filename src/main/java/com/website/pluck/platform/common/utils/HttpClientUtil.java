package com.website.pluck.platform.common.utils;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpClientUtil {
    public HttpClientUtil() {
    }

    public static String get(String url, Map<String, String> urlParam, Map<String, String> header, boolean ssl) {
        return get(url, urlParam, header, "UTF-8", ssl);
    }

    public static String get(String url, Map<String, String> urlParams, Map<String, String> headers, String charSet, boolean ssl) {
        HttpGet httpGet = new HttpGet(charSet == null ? addParams(url, urlParams) : addParamsWithCharSet(url, urlParams, charSet));
        return getResponse(httpGet, charSet, headers, ssl);
    }

    public static String postJson(String url, Map<String, String> urlParams, Map<String, String> headers, String data, boolean ssl) {
        HttpPost httpPost = new HttpPost(addParams(url, urlParams));
        httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        return getResponse(httpPost, "UTF-8", headers, ssl);
    }

    public static String postForm(String url, Map<String, String> urlParams, Map<String, String> headers, Map<String, String> data, boolean ssl) {
        HttpPost httpPost = new HttpPost(addParams(url, urlParams));
        ContentType contentType = ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8);
        if (Objects.isNull(headers)) {
            headers = new HashMap();
        }

        ((Map) headers).put("Content-Type", contentType.toString());
        List<NameValuePair> list = new ArrayList();
        Iterator var8 = data.entrySet().iterator();

        while (var8.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var8.next();
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        if (!list.isEmpty()) {
            UrlEncodedFormEntity entity = null;

            try {
                entity = new UrlEncodedFormEntity(list, "UTF-8");
            } catch (UnsupportedEncodingException var10) {
                var10.printStackTrace();
            }

            httpPost.setEntity(entity);
        }

        return getResponse(httpPost, "UTF-8", headers, ssl);
    }

    private static String getResponse(HttpRequestBase httpRequestBase, String charSet, Map<String, String> headers, boolean ssl) {
        CloseableHttpClient httpClient = null;

        try {
            httpClient = ssl ? getHttpClient() : HttpClients.createDefault();
            httpRequestBase.setConfig(getRequestConfig());
            if (!headers.isEmpty()) {
                httpRequestBase.setHeaders(getHeaders(headers));
            }

            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                HttpEntity entity = response.getEntity();
                String res = EntityUtils.toString(entity, charSet);
                EntityUtils.consume(entity);
                String var9 = res;
                return var9;
            }
        } catch (Exception var20) {
            var20.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(httpClient)) {
                    httpClient.close();
                }
            } catch (IOException var19) {
                var19.printStackTrace();
            }

        }

        throw new RuntimeException("调用失败");
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(12000).setConnectionRequestTimeout(12000).setSocketTimeout(12000).build();
    }

    private static String addParams(String url, Map<String, String> params) {
        return addParamsWithCharSet(url, params, (String) null);
    }

    private static String addParamsWithCharSet(String url, Map<String, String> params, String charSet) {
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            try {
                Iterator var4 = params.entrySet().iterator();

                while (var4.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) var4.next();
                    sb.append("&").append((String) entry.getKey()).append("=");
                    sb.append(charSet == null ? (String) entry.getValue() : URLEncoder.encode((String) entry.getValue(), charSet));
                }

                if (!url.contains("?")) {
                    sb.deleteCharAt(0).insert(0, "?");
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            System.out.println(url + sb);
            return url + sb;
        } else {
            return url;
        }
    }

    public static CloseableHttpClient getHttpClient() {
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext context = null;

        try {
            context = SSLContext.getInstance("SSL");
            context.init((KeyManager[]) null, new TrustManager[]{trustManager}, (SecureRandom) null);
            return HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(context)).build();
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3.getMessage());
        }
    }

    public static Header[] getHeaders(Map<String, String> header) {
        if (header.isEmpty()) {
            return new Header[0];
        } else {
            List<Header> headers = new ArrayList();
            Iterator var2 = header.keySet().iterator();

            while (var2.hasNext()) {
                String key = (String) var2.next();
                headers.add(new BasicHeader(key, (String) header.get(key)));
            }

            return (Header[]) headers.toArray(new Header[0]);
        }
    }
}
