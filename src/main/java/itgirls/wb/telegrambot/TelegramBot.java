package itgirls.wb.telegrambot;

import itgirls.wb.http.client.WeatherClient;
import itgirls.wb.http.dto.WeatherDto;
import itgirls.wb.http.service.GeoLocatorService;
import java.util.regex.*;
import lombok.AllArgsConstructor;
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
    private WeatherClient weatherClient;

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

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
//
//            if (Pattern.matches("\\W", messageText) == true) {
//                String numbers[] = messageText.split(",");
//                float latitude = Float.parseFloat(numbers[0]);
//                float longitude = Float.parseFloat(numbers[1]);
//                WeatherDto weather = weatherClient.getWeather(latitude, longitude);
//                sendMessage(chatId, String.valueOf(weather));
//            }
//            else if (messageText == "/start") {
//                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
//            }
//            else {
//                sendMessage(chatId, "Ой, что-то пошло не так, попробуйте ещё раз :(");
//            }


            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "Погода по координатам":
                    sendMessage(chatId, "Введите координаты (широта и долгота через запятую)");
                    break;
                case "Погода по адресу":
                    sendMessage(chatId, "Введите адрес");
                    break;
                default:
                    String numbers[] = messageText.split(",");
                float latitude = Float.parseFloat(numbers[0]);
                float longitude = Float.parseFloat(numbers[1]);
                WeatherDto weather = weatherClient.getWeather(latitude, longitude);
                sendMessage(chatId, String.valueOf(weather));
                    }
            }
        }

//    public WeatherDto getWeather(float lat, float lon) {
//        return weatherClient.getWeather(lat, lon);
//    }
//    public WeatherDto getWeatherTg(float lat, float lon) {
//        String numbers [] = messageText.split(",");
//        float latitude = Float.parseFloat(numbers[0]);
//        float longitude = Float.parseFloat(numbers[1]);
//    }

    }