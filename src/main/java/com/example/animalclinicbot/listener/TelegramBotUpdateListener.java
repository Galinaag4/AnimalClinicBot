package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.repository.DogRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
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
        updates.forEach(update -> {
            String text = update.message().text();
            System.out.println(text);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
