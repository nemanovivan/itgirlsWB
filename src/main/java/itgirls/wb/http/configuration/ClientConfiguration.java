package itgirls.wb.http.configuration;


import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.client.WeatherClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
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
    /**
     * в методе httpClientBuilder() определяем конфигурацию HttpClientBuilder,
     * который используется для конфигурирования RestTemplate
     *
     * @return HttpClientBuilder c заданной конфигурацией
     */
    @Bean
    public HttpClientBuilder httpClientBuilder() {
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(200, TimeUnit.MILLISECONDS)
                .setSocketTimeout(200, TimeUnit.MILLISECONDS)
                .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        return HttpClients.custom().setConnectionManager(cm);
    }

    /**
     * в методе restOperations() определяем конфигурацию RestTemplate,
     * который используется для отправки запросов в geoLocatorClient() и weatherClient()
     *
     * @param connectionTimeout определяет таймаут подключения
     * @param httpClientBuilder определяет используемый HttpClientBuilder
     * @return RestTemplate c заданной конфигурацией
     */
    @Bean
    public RestTemplate restOperations(
            @Value("${timeout.connect:500}") int connectionTimeout,
            HttpClientBuilder httpClientBuilder
    ) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setHttpClient(httpClientBuilder.build());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    /**
     * в методе geoLocatorClient() определяем какой url и restTemplate используется в GeoLocatorClient
     *
     * @param restTemplate формируем в restOperations()
     * @param url          берем из application.properties
     * @return GeoLocatorClient
     */
    @Bean
    public GeoLocatorClient geoLocatorClient(
            @Qualifier("restOperations") RestTemplate restTemplate,
            @Value("${urlGeoCoder}") String url
    ) {
        return new GeoLocatorClient(restTemplate, url);
    }

    /**
     * в методе weatherClient() определяем какой url и restTemplate используется в WeatherClient
     *
     * @param restTemplate формируем в restOperations()
     * @param url          берем из application.properties
     * @return WeatherClient
     */

    @Bean
    public WeatherClient weatherClient(
            @Qualifier("restOperations") RestTemplate restTemplate,
            @Value("${url}") String url
    ) {
        return new WeatherClient(restTemplate, url);
    }
}

