package itgirls.wb.telegrambot;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.client.WeatherClient;
import itgirls.wb.http.dto.WeatherDto;
import itgirls.wb.http.service.GeoLocatorService;

import java.util.List;
import java.util.regex.*;

import itgirls.wb.http.service.GeoLocatorServiceImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

import static org.springframework.core.io.buffer.DataBufferUtils.matcher;

@Component
//@AllArgsConstructor
//@RestController
public final class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    @Autowired
    private WeatherClient weatherClient;
    @Autowired
    private GeoLocatorServiceImpl geolocService;


    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        initKeyboard();
    }


    static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    static void initKeyboard()
    {
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        //Добавляем
        keyboardRow.add(new KeyboardButton("Погода по адресу"));
        keyboardRow.add(new KeyboardButton("Погода по координатам"));
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Привет, " + name + ", добро пожаловать в бот с погодой!" + "\n" +
                "Выберите, что вам нужно сделать";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {


        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else if (messageText.equals("Погода по координатам")) {
                sendMessage(chatId, "Введите координаты (широта и долгота через запятую)");
            } else if (messageText.equals("Погода по адресу")) {
                sendMessage(chatId, "Введите адрес в следующем формате: страна, город, улица, дом. " +
                        "Пример: Россия, Екатеринбург, Куйбышева, 143");
            }
            else if (messageText.contains(".")) {
                String numbers[] = messageText.split(",");
                float latitude = Float.parseFloat(numbers[0]);
                float longitude = Float.parseFloat(numbers[1]);
                WeatherDto weather = weatherClient.getWeather(latitude, longitude);
                sendMessage(chatId, String.valueOf(weather));
            } else {
                String[] address = messageText.split(", ");
                String country = address[0];
                String city = address[1];
                String street = address[2];
                String house = address[3];
                List<Float> coordList = geolocService.getCoordinates(country, city, street, house);
                float latitude = coordList.get(0);
                float longitude = coordList.get(1);
                WeatherDto weather = weatherClient.getWeather(latitude, longitude);
                sendMessage(chatId, String.valueOf(latitude));
            }


//            switch (messageText){
//                case "/start":
//                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
//                    break;
//                case "Погода по координатам":
//                    sendMessage(chatId, "Введите координаты (широта и долгота через запятую)");
//                    break;
//                case "Погода по адресу":
//                    sendMessage(chatId, "Введите адрес");
//                    break;
//                default:
//                    String numbers[] = messageText.split(",");
//                float latitude = Float.parseFloat(numbers[0]);
//                float longitude = Float.parseFloat(numbers[1]);
//                WeatherDto weather = weatherClient.getWeather(latitude, longitude);
//                sendMessage(chatId, String.valueOf(weather));
//                    }
            }

        }
        }

