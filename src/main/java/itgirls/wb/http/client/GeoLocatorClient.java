package itgirls.wb.http.client;

import itgirls.wb.http.dto.GeoLocatorDto;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import org.apache.hc.core5.net.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GeoLocatorClient {

    /**
     * клиент использует restTemplate, определенный в ClientConfiguration
     */
    private final RestTemplate restTemplate;
    /**
     * клиент использует url, определенный в ClientConfiguration
     */
    private final String url;

    /**
     * язык ответа определяется двузначным кодом языка_двузначным кодом страны
     */
    private final String language = "ru_RU";

    /**
     * константа, хранящая максимальное количество возвращаемых объектов
     */
    static final String AMOUNT_OF_RESULTS = "1";

    /**
     * API-ключ, полученный на сайте яндекса, хранится в application.properties
     */
    @Value("${apikeyGeoCoder}")
    private String apiKey;

    public GeoLocatorClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    /**
     * в методе getCoordinatesFrom() формируется uri,
     * по сформированному uri отправляетя get запрос
     *
     * @param address - адрес, указанный пользователем
     * @return GeoLocatorDto для заданного адресса
     */
    public GeoLocatorDto getCoordinatesFrom(String address) {
        //form parameters
        List<NameValuePair> arrayListOfParameters = new ArrayList<>();
        arrayListOfParameters.add(new BasicNameValuePair("apikey", apiKey));
        arrayListOfParameters.add(new BasicNameValuePair("geocode", address));
        arrayListOfParameters.add(new BasicNameValuePair("format", "json"));
        arrayListOfParameters.add(new BasicNameValuePair("results", AMOUNT_OF_RESULTS));
        arrayListOfParameters.add(new BasicNameValuePair("lang", language));

        URI uri = null;
        try {
            uri = new URIBuilder(new URI(url))
                    .addParameters(arrayListOfParameters)
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return restTemplate.getForObject(uri, GeoLocatorDto.class);
    }
}
