package com.example.animalclinicbot.service;

import com.example.animalclinicbot.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс меню бота и набор кнопок для меню .
 *
 * @author Anna Molodtsova
 */
@Service
public class KeyBoardService {
    @Autowired
    private TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * Отображение главного меню .
     */
    public void chooseMenu(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано меню выбора ");
        String emoji_cat = EmojiParser.parseToUnicode(":cat:");
        String emoji_dog = EmojiParser.parseToUnicode(":dog:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(emoji_cat + " CAT"));
        replyKeyboardMarkup.addRow(new KeyboardButton(emoji_dog + " DOG"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Выберите, кого хотите приютить:");
    }

    /**
     * Меню вызова кнопок.
     *
     */
    public void sendMenuCat(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню кошки ");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о приюте для кошек"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять кошку из приюта"),
                new KeyboardButton("Прислать отчет о кошке"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Главное меню");
    }
    public void sendMenuDog(long chatId) {
        logger.info("Method sendMessage has been run: {}, {}", chatId, "Вызвано основное меню Собаки ");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о приюте для собак"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Как взять собаку из приюта"),
                new KeyboardButton("Прислать отчет о собаке"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Главное меню");
    }

    public void sendMenuInfoShelterCat(long chatId) {
        logger.info("Method sendMenuInfoShelterCat has been run: {}, {}", chatId, "Вызвали ~Информация о приюте для кошек~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(new KeyboardButton("Информация о приюте для кошек"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о приюте для кошек");
    }
    public void sendMenuInfoShelterDog(long chatId) {
        logger.info("Method sendMenuInfoShelterDog has been run: {}, {}", chatId, "Вызвали ~Информация о приюте для собак~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(new KeyboardButton("Информация о приюте для собак"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Информация о приюте для собак");
    }

    public void sendMenuTakeCat(long chatId) {
        logger.info("Method sendMenuTakeCat has been run: {}, {}", chatId, "вызвали ~Как взять кошку из приюта~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Содержание и уход за кошкой"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Как взять кошку из приюта");
    }
    public void sendMenuTakeDog(long chatId) {
        logger.info("Method sendMenuTakeDog has been run: {}, {}", chatId, "вызвали ~Как взять собаку из приюта~");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Содержание и уход за собакой"),
                new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать волонтера на помощь"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Как взять собаку из приюта");
    }

    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.info("code of error: {}", codeError);
            logger.info("description -: {}", description);
        }
    }
}
