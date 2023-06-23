package itgirls.wb.http.configuration;

import itgirls.wb.http.client.GeoLocatorClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;


import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    @Bean
    public HttpClientBuilder httpClientBuilder(
            @Value("${http.connect.timeout:100}") Timeout connectTimeout //type Timeout - не уверена, что верно написано
            //@Value("${http.socket.timeout:100}") Timeout socketTimeout - можно ли без него ??
    ) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout.)
                //.setSocketTimeout(socketTimeout) ???
                .build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig);
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
    ) {
        return new GeoLocatorClient(restTemplate, url);
    }
}
