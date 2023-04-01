package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.repository.DogRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

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
    public int process(List<Update> list) {
        return 0;
    }
}