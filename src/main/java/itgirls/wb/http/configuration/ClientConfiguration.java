package itgirls.wb.http.configuration;

import itgirls.wb.http.client.GeoLocatorClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;


import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfiguration {

    private static final Timeout timeout = Timeout.ofMilliseconds(200L);
    @Bean
    public HttpClientBuilder httpClientBuilder() {
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(200, TimeUnit.MILLISECONDS)
                .setSocketTimeout(200, TimeUnit.MILLISECONDS)
                .build();

        //RequestConfig requestConfig = RequestConfig.custom()
                //.setConnectTimeout(connectTimeout)
                //.setSocketTimeout(socketTimeout)
                //.build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        //return HttpClients.custom().setDefaultRequestConfig(requestConfig);
        return HttpClients.custom().setConnectionManager(cm);
    }

    @Bean
    public RestTemplate restOperations(
            @Value("${timeout.connect:500}") int connectionTimeout,
            @Value("${timeout.read:1000}") int readTimeout,
            HttpClientBuilder httpClientBuilder
    ) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        //requestFactory.setReadTimeout(readTimeout); //deprecated - можно ли без него???
        requestFactory.setHttpClient(httpClientBuilder.build()); //c httpclient:4.5.14 ругался в этом месте на Closable httpClient

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;

    }

    @Bean
    public GeoLocatorClient geoLocatorClient(
            @Qualifier("restOperations") RestTemplate restTemplate,
            @Value("${urlGeoCoder}") String url
            //@Value("{$urlGeoCoder}")String url
    ) {
        return new GeoLocatorClient(restTemplate, url);
    }
}
