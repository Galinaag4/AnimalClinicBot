package com.example.animalclinicbot.service;

import com.example.animalclinicbot.configuration.TelegramBotConfiguration;
import com.example.animalclinicbot.constants.BotMessageEnum;
import com.example.animalclinicbot.constants.ButtonNameEnum;
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

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private PersonDogRepository personDogRepository;
    final TelegramBotConfiguration botConfiguration;


    public TelegramBot(TelegramBotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "сообщение приветсвие"));
        listofCommands.add(new BotCommand("/info", "информация о приюте"));
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

                        prepareAndSendMessage(chatId,BotMessageEnum.INFO_SHELTER.getMessage());
                        break;

                    default:

                        prepareAndSendMessage(chatId, "Извините, команда не поддреживается!");

                }
            }
        }

            }

    private void startCommandReceived(long chatId, String name) {
        String answer = BotMessageEnum.HELP_MESSAGE.getMessage();


        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(new KeyboardButton(ButtonNameEnum.CALL_VOLUNTEER_BUTTON.getButtonName()));
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
