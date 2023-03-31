package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.repository.DogRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
@Service
public class TelegramBotUpdateListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdateListener(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Autowired
    private final DogRepository dogRepository;



    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
       try{
           updates
            .forEach(update -> {
                logger.info("Handles update: {}", update);
                if(update.callbackQuery() != null){
                    CallbackQuery callbackQuery = update.callbackQuery();
                    Lond chatId = callbackQuery().message().chat().id();
                    String data = callbackQuery.data();
                    switch (data){
                        case "Кнопка1":
                            sendMessage(chatId, "message");
                            break;
                        case "Кнопка2":
                            sendMessage(chatId, "message");
                            break;
                    }
                }


                Message message = update.message();
                Long chatId = message.chat().id();
                String text = message.text();



                if("/start".equals(text)){
                    SendMessage sendMessage = new SendMessage(chatId,  """
                    Привет! Мы приют для собак из города N
                    У нас ты можешь взять питомца и узнать как за ним ухаживать!
                     """);
                    InlineKeyboardButton button1 = new InlineKeyboardButton("Кнопка1");
                    button1.callbackData("Кнопка1");
                    InlineKeyboardButton button2 = new InlineKeyboardButton("Кнопка2");
                    button1.callbackData("Кнопка2");
                    Keyboard keyboard = new InlineKeyboardMarkup(button1, button2);
                    sendMessage.replyMarkup(keyboard);
                    telegramBot.execute(sendMessage);





                }
            }
       });
       }
}
