package com;

import com.database.CurrencyDAO;
import com.objects.Currency;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private CurrencyDAO currencyDAO;

    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setReplyMarkup(getKeyboard());
        switch (update.getMessage().getText()) {
            case "/start":
                message = getStartMessage(message);
                break;
            case "Show Goverla rate":
                message = getRates(message);
                break;
            default:
                message = getDefaultMessage(message);
                break;
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getStartMessage(SendMessage message) {
        message.setText("Hi, welcome to rate bot, choose action from keyboard.");
        return message;
    }

    private SendMessage getRates(SendMessage message) {
        StringBuilder messageText = new StringBuilder("Goverla rate for today: ");
        getCurrencyDAO().getAll().forEach(el -> messageText.append(System.lineSeparator()).append(el.toString()));
        message.setText(messageText.toString());
        return message;
    }

    private SendMessage getDefaultMessage(SendMessage message) {
        message.setText("Sorry, I didn't understand you. Please choose something from keyboard.");
        return message;
    }

    private ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Show Goverla rate");
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public CurrencyDAO getCurrencyDAO(){
        if (currencyDAO == null)
            currencyDAO = new CurrencyDAO();
        return currencyDAO;
    }

    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
