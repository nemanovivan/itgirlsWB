package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public record WeatherDto(String date, Fact fact, Forecast forecast) {


    @Override
    public String toString() {
        return "Здравствуйте! Погода " + this.date + " в вашем регионе следующая: \n" +
                "температура: " + this.fact.temp + " градусов" + "\n" +
                "температура ощущается как: " + this.fact.tempFeelsLike + " градусов" + "\n" +
                "погодные условия: " + this.fact.condition + "\n" +
                "скорость ветра: " + this.fact.windSpeed + " м/c" + "\n" +
                "направление ветра: " + this.fact.windDir + "\n" +
                "порывы ветра: " + "до " + this.fact.windGust + " м/c" + "\n" +
                "влажность: " + this.fact.humidity + "%" + "\n" +
                "\n" +
                "средняя температура в первой половине дня: " + this.forecast.partsList.get(0).temp + "градусов" + "\n" +
                "средняя температура во второй половине дня: " + this.forecast.partsList.get(1).temp + "градусов" + "\n" +
                "\n" +
                "встречайте рассвет в " + this.forecast.sunrise + "\n" +
                "встречайте закат в " + this.forecast.sunset + "\n";
    }


    @JsonCreator
    public WeatherDto(
            @JsonProperty("now_dt") String date,
            @JsonProperty("fact") Fact fact,
            @JsonProperty("forecast") Forecast forecast
    ) {
        this.date = date;
        this.fact = fact;
        this.forecast = forecast;
    }

    public record Fact(int temp, int tempFeelsLike, String condition, int windSpeed, String windDir, float windGust,
                       int humidity) {
        @JsonCreator
        public Fact(
                @JsonProperty("temp") int temp,
                @JsonProperty("feels_like") int tempFeelsLike,
                @JsonProperty("condition") String condition,
                @JsonProperty("wind_speed") int windSpeed,
                @JsonProperty("wind_dir") String windDir,
                @JsonProperty("wind_gust") float windGust,
                @JsonProperty("humidity") int humidity
        ) {
            this.temp = temp;
            this.tempFeelsLike = tempFeelsLike;
            this.condition = condition;
            this.windSpeed = windSpeed;
            this.windDir = windDir;
            this.windGust = windGust;
            this.humidity = humidity;
        }
    }

    public record Forecast(int moonCode, String sunrise, String sunset, List<Parts> partsList) {
        @JsonCreator
        public Forecast(
                @JsonProperty("moon_code") int moonCode,
                @JsonProperty("sunrise") String sunrise,
                @JsonProperty("sunset") String sunset,
                @JsonProperty("parts") List<Parts> partsList
        ) {
            this.moonCode = moonCode;
            this.sunrise = sunrise;
            this.sunset = sunset;
            this.partsList = partsList;
        }

        public record Parts(String partName, int temp, int tempFeelsLike, String condition, int windSpeed,
                            String windDir, float windGust, int humidity) {
            @JsonCreator
            public Parts(
                    @JsonProperty("part_name") String partName,
                    @JsonProperty("temp_avg") int temp,
                    @JsonProperty("feels_like") int tempFeelsLike,
                    @JsonProperty("condition") String condition,
                    @JsonProperty("wind_speed") int windSpeed,
                    @JsonProperty("wind_dir") String windDir,
                    @JsonProperty("wind_gust") float windGust,
                    @JsonProperty("humidity") int humidity
            ) {
                this.partName = partName;
                this.temp = temp;
                this.tempFeelsLike = tempFeelsLike;
                this.condition = condition;
                this.windSpeed = windSpeed;
                this.windDir = windDir;
                this.windGust = windGust;
                this.humidity = humidity;
            }
        }
    }
}