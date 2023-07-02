package com.example.animalclinicbot.keyboard;

import com.example.animalclinicbot.constant.Button;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.example.animalclinicbot.constant.TypeOfShelter.CAT;
import static com.example.animalclinicbot.constant.TypeOfShelter.DOG;

/**
 * Класс меню бота и набор кнопок для меню .
 *
 * @author Anna Molodtsova
 */
@Component
public class KeyBoardService {

    private TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public KeyBoardService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    /**
     * Метод отображает меню, где выбирается приют.
     *
     * @param chatId
     */
    public void chooseMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                Button.CAT.getCommand(), Button.DOG.getCommand());
        sendResponseMenu(chatId, replyKeyboardMarkup, "Выберите приют в меню ниже.");
    }

    /**
     * Метод отображает главное меню приюта.
     *
     * @param chatId
     */
    public void shelterMainMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{Button.SHELTER_INFO_MENU.getCommand(), Button.HOW_ADOPT_PET_INFO.getCommand()},
                new String[]{Button.SEND_REPORT.getCommand(), Button.VOLUNTEER.getCommand()});
        sendResponseMenu(chatId, replyKeyboardMarkup, "Ниже представлено главное меню приюта. " +
                "Чтобы вернуться к выбору приюта, напишите команду /start");
    }

    /**
     * Метод отображает меню, с информацией о приюте.
     *
     * @param chatId
     */
    public void shelterInfoMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                Button.SHELTER_INFO.getCommand());
        replyKeyboardMarkup.addRow(new KeyboardButton(Button.VOLUNTEER.getCommand()),
                new KeyboardButton(Button.SEND_CONTACT.getCommand()).requestContact(true));
        replyKeyboardMarkup.addRow(Button.MAIN_MENU.getCommand());
        sendResponseMenu(chatId, replyKeyboardMarkup, "Вы можете получить информацию о приюте в меню.");
    }

    /**
     * Метод отображает меню, с информацией о том, как взять питомца из приюта.
     *
     * @param chatId
     */
    public void shelterInfoHowAdoptPetMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(Button.RECOMMENDATIONS_LIST.getCommand(),
                Button.DOCUMENTS_LIST.getCommand());
        replyKeyboardMarkup.addRow(new KeyboardButton(Button.VOLUNTEER.getCommand()),
                new KeyboardButton(Button.SEND_CONTACT.getCommand()).requestContact(true));
        replyKeyboardMarkup.addRow(Button.MAIN_MENU.getCommand());
        sendResponseMenu(chatId, replyKeyboardMarkup, "Информация о том, как взять животное из приюта");
    }

    /**
     * Метод принимает клавиатуру и текст, и отправляет ответ в чат по chatId.
     *
     * @param chatId
     * @param replyKeyboardMarkup
     * @param text
     */
    public void sendResponseMenu(long chatId, ReplyKeyboardMarkup replyKeyboardMarkup, String text) {
        SendMessage sendMessage = new SendMessage(
                chatId, text).replyMarkup(replyKeyboardMarkup.resizeKeyboard(true));
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
}
