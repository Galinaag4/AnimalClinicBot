//package com.example.animalclinicbot.serviceTest;

import com.example.animalclinicbot.repository.PersonDogRepository;

import com.google.common.base.Optional;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.json.XMLTokener.entity;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@OpenAPIDefinition
//public class TelegramBotTest {
//    @Autowired
//    private TelegramBot telegramBot;
//    @Autowired
//    private PersonDogRepository personDogRepository;
//
//}
