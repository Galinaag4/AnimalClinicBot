package com.example.animalclinicbot.service;

import com.example.animalclinicbot.configuration.TelegramBotConfiguration;
import com.example.animalclinicbot.model.PersonDog;

import com.example.animalclinicbot.repository.PersonDogRepository;

import com.vdurmont.emoji.EmojiParser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private PersonDogRepository personDogRepository;
    final TelegramBotConfiguration botConfiguration;

    static final String HELP_TEXT = "Этот бот поможет ответить на ваши вопросы о том, что нужно знать," +
            " чтобы забрать животное из приюта.\n\n" +
            "Вы можете использовать эти комманды из меню слева или ввеcти команду:\n\n" +
            "Нажми /start чтобы увидеть сообщение приветствие\n\n" +
            "Нажми /info чтобы увидеть информацию о приюте\n\n" +
            "Нажми /volunteer позвать волонтера на помощь";

    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";


    public TelegramBot(TelegramBotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "сообщение приветсвие"));
        listofCommands.add(new BotCommand("/info", "информация о боте"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
        }
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.contains("/send")) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var personsDog = personDogRepository.findAll();
                for (PersonDog person : personsDog) {
                    prepareAndSendMessage(person.getChatId(), textToSend);
                }
            } else {

                switch (messageText) {
                    case "/start":

                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                    case "/info":

                        prepareAndSendMessage(chatId, HELP_TEXT);
                        break;

                    default:

                        prepareAndSendMessage(chatId, "Sorry, command was not recognized");

                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(YES_BUTTON)) {
                String text = "You pressed YES button";
                executeEditMessageText(text, chatId, messageId);
            } else if (callbackData.equals(NO_BUTTON)) {
                String text = "You pressed NO button";
                executeEditMessageText(text, chatId, messageId);
            }
        }


    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет, " + name + ", я расскажу тебе о приюте!" + " :blush:");


        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("позвать волонтера");
// второй ряд кнопок
//        keyboardRows.add(row);
//
//        row = new KeyboardRow();
//
//        row.add("");
//
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }


    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

}
