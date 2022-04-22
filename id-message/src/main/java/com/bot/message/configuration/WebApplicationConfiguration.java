package com.bot.message.configuration;

import com.linecorp.bot.client.*;
import com.linecorp.bot.spring.boot.LineBotProperties;
import com.bot.core.okhttp.AllowAllHostnameVerifier;
import com.bot.core.okhttp.TrustAllTrustManager;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 其他雜七雜八的 bean 設定
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(LineBotProperties.class)
public class WebApplicationConfiguration {

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    private final LineBotProperties lineBotProperties;

    @Bean
    public String profileName() {
        String profile = "";
        String[] profiles = activeProfiles.split(",");
        if (profiles.length > 0) {
            profile = profiles[0];
        }
        Log.info("profileName : '{}'", profile);
        return profile;
    }

    public WebApplicationConfiguration(LineBotProperties lineBotProperties) {
        this.lineBotProperties = lineBotProperties;
    }

    @Bean
    public LineMessagingClient lineMessagingClient(
            final ChannelTokenSupplier channelTokenSupplier) {

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

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier());

        return LineMessagingClient
                .builder(channelTokenSupplier)
                .okHttpClientBuilder(httpClientBuilder, true)
                .apiEndPoint(lineBotProperties.getApiEndPoint())
                .blobEndPoint(lineBotProperties.getBlobEndPoint())
                .connectTimeout(lineBotProperties.getConnectTimeout())
                .readTimeout(lineBotProperties.getReadTimeout())
                .writeTimeout(lineBotProperties.getWriteTimeout())
                .build();
    }

    @Bean
    public RetryableLineMessagingClient retryableLineMessagingClient(
            final ChannelTokenSupplier channelTokenSupplier) {

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

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier());

        return RetryableLineMessagingClient
                .builder(channelTokenSupplier)
                .okHttpClientBuilder(httpClientBuilder, true)
                .apiEndPoint(lineBotProperties.getApiEndPoint())
                .blobEndPoint(lineBotProperties.getBlobEndPoint())
                .connectTimeout(lineBotProperties.getConnectTimeout())
                .readTimeout(lineBotProperties.getReadTimeout())
                .writeTimeout(lineBotProperties.getWriteTimeout())
                .build();
    }

    @Bean
    public LineBlobClient lineBlobClient(
            final ChannelTokenSupplier channelTokenSupplier) {

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

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier());

        return LineBlobClient
                .builder(channelTokenSupplier)
                .okHttpClientBuilder(httpClientBuilder, true)
                .apiEndPoint(lineBotProperties.getBlobEndPoint())
                .connectTimeout(lineBotProperties.getConnectTimeout())
                .readTimeout(lineBotProperties.getReadTimeout())
                .writeTimeout(lineBotProperties.getWriteTimeout())
                .build();
    }

    @Bean
    public ManageAudienceBlobClient manageAudienceBlobClient(
            final ChannelTokenSupplier channelTokenSupplier) {

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

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier());

        return ManageAudienceBlobClient
                .builder()
                .okHttpClientBuilder(httpClientBuilder, true)
                .channelTokenSupplier(channelTokenSupplier)
                .apiEndPoint(lineBotProperties.getBlobEndPoint())
                .connectTimeout(lineBotProperties.getConnectTimeout())
                .readTimeout(lineBotProperties.getReadTimeout())
                .writeTimeout(lineBotProperties.getWriteTimeout())
                .build();
    }

    @Bean
    public ManageAudienceClient manageAudienceClient(
            final ChannelTokenSupplier channelTokenSupplier) {

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

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new AllowAllHostnameVerifier());

        return ManageAudienceClient
                .builder()
                .okHttpClientBuilder(httpClientBuilder, true)
                .channelTokenSupplier(channelTokenSupplier)
                .apiEndPoint(lineBotProperties.getApiEndPoint())
                .connectTimeout(lineBotProperties.getConnectTimeout())
                .readTimeout(lineBotProperties.getReadTimeout())
                .writeTimeout(lineBotProperties.getWriteTimeout())
                .build();
    }

}