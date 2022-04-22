package com.bot.core.okhttp;

import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class OkHttpClientFactory {

    public static OkHttpClient create() {
        X509TrustManager trustManager = new TrustAllTrustManager();
        SSLSocketFactory sslSocketFactory;
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            sslSocketFactory = null;
        }

        if (sslSocketFactory == null)
            throw new AssertionError("SSLSocketFactory create fail");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .build();

        return client;
    }

    private OkHttpClientFactory() {
        super();
    }

}