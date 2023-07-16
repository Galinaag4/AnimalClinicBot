package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.constant.TypeOfShelter;
import com.example.animalclinicbot.model.*;
import com.example.animalclinicbot.service.*;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {
    @Mock
    TelegramBot telegramBot;
    @Mock
    KeyBoardService keyBoardService;
    @Mock
    UserContextService userContextService;
    @Mock
    PersonCatService personCatService;
    @Mock
    PersonDogService personDogService;
    @Mock
    ReportService reportService;
    @Mock
    UserContext userContextMock;
    @Mock
    PersonDog personDogMock;
    @Mock
    Dog dogMock;
    @Mock
    PersonCat personCatMock;
    @Mock
    Cat catMock;
    @InjectMocks
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    public void handleStartCommandWithContextEmptyTest() throws URISyntaxException, IOException {
        Update update = getUpdate("/start");
        long chatId = update.message().chat().id();
        UserContext userContext = new UserContext();
        userContext.setChatId(chatId);

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.empty());
        when(userContextService.saveUserContext(userContext)).thenReturn(userContext);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Привет! Я могу показать информацию о приютах," +
                "как взять животное из приюта и принять отчет о питомце");
        verify(userContextService, times(1)).getByChatId(chatId);
        verify(userContextService, times(1)).saveUserContext(userContext);
        verify(keyBoardService, times(1)).chooseMenu(chatId);
    }

    @Test
    public void handleStartCommandWithContextExistsTest() throws URISyntaxException, IOException {
        Update update = getUpdate("/start");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(userContextService, times(1)).getByChatId(chatId);
        verify(keyBoardService, times(1)).chooseMenu(chatId);
        verify(userContextService, never()).saveUserContext(any(UserContext.class));
    }

    @Test
    public void handleCatShelterCommandTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Кошачий");
        long chatId = update.message().chat().id();


        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextService.saveUserContext(userContextMock)).thenReturn(userContextMock);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Вы выбрали кошачий приют.");
        verify(userContextService, times(2)).getByChatId(chatId);
        verify(userContextService, times(1)).saveUserContext(userContextMock);
        verify(keyBoardService, times(1)).shelterMainMenu(chatId);
    }

    @Test
    public void handleDogShelterCommandTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Собачий");
        long chatId = update.message().chat().id();


        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextService.saveUserContext(userContextMock)).thenReturn(userContextMock);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Вы выбрали собачий приют.");
        verify(userContextService, times(2)).getByChatId(chatId);
        verify(userContextService, times(1)).saveUserContext(userContextMock);
        verify(keyBoardService, times(1)).shelterMainMenu(chatId);
    }

    @Test
    public void handleMainMenuTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Главное меню");
        long chatId = update.message().chat().id();

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(keyBoardService, times(1)).shelterMainMenu(chatId);
    }

    @Test
    public void handleShelterInfoMenuTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Узнать информацию о приюте");
        long chatId = update.message().chat().id();

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(keyBoardService, times(1)).shelterInfoMenu(chatId);
    }

    @Test
    public void handleHowAdoptPetInfoMenuTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Как взять животного из приюта");
        long chatId = update.message().chat().id();

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(keyBoardService, times(0)).shelterInfoHowAdoptPetMenu(chatId);
    }

    @Test
    public void handleCatShelterInfoTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Общая информация");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Информация о кошачем приюте - ...
                Рекомендации о технике безопасности на территории кошачего приюта - ...
                Контактные данные охраны - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleDogShelterInfoTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Общая информация");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Информация о собачем приюте - ...
                Рекомендации о технике безопасности на территории собачего приюта - ...
                Контактные данные охраны - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleCatShelterAddressAndScheduleInfoTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Адрес и график работы приюта");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Адрес кошачего приюта - ...
                График работы - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleDogShelterAddressAndScheduleInfoTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Адрес и график работы приюта");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Адрес собачего приюта - ...
                График работы - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleCatShelterRecommendationListTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Список рекомендаций и советов");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Правила знакомства с животным - ...
                Список рекомендаций - ...
                Список причин отказа в выдаче животного - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleDogShelterRecommendationListTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Список рекомендаций и советов");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Правила знакомства с животным - ...
                Список рекомендаций - ...
                Советы кинолога по первичному общению с собакой - ...
                Рекомендации по проверенным кинологам для дальнейшего обращения к ним
                Список причин отказа в выдаче животного - ...
                """);
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleCatShelterDocumentsListTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Список необходимых документов");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Для взятия кота из приюта необходимы такие документы: ...");
        verify(userContextService, times(2)).getByChatId(chatId);
    }

    @Test
    public void handleDogShelterDocumentsListTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Список необходимых документов");
        long chatId = update.message().chat().id();

        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Для взятия собаки из приюта необходимы такие документы: ...");
        verify(userContextService, times(2)).getByChatId(chatId);
    }


    @Test
    public void handleVolunteerTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Связаться с волонтером");
        long chatId = update.message().chat().id();
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);
        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        ArgumentCaptor<ForwardMessage> forwardMessageArgumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(telegramBot, times(2)).execute(sendMessageArgumentCaptor.capture());
        verify(telegramBot, times(2)).execute(forwardMessageArgumentCaptor.capture());
        SendMessage actual = sendMessageArgumentCaptor.getAllValues().get(0);
        ForwardMessage actualForward = forwardMessageArgumentCaptor.getAllValues().get(1);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Мы передали ваше сообщение волонтеру. " +
                "Если у вас закрытый профиль отправьте контактные данные," +
                "с помощью кнопки в меню - Отправить контактные данные");

        Assertions.assertThat(actualForward.getParameters().get("from_chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actualForward.getParameters().get("message_id")).isEqualTo(1);
    }

    @Test
    public void handleSendReportTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Прислать отчет о питомце");
        long chatId = update.message().chat().id();

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("""
                Для отчета необходима фотография, рацион,
                самочувствие и изменение в поведении питомца.
                Загрузите фото, а в подписи к нему, скопируйте и заполните текст ниже.
                                                    
                Рацион: ваш текст;
                Самочувствие: ваш текст;
                Поведение: ваш текст;
                """);
    }

    @Test
    public void handleUnknownCommandTest() throws URISyntaxException, IOException {
        Update update = getUpdate("Как дела?");
        long chatId = update.message().chat().id();

        SendMessage actual = getSendMessage(update);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Неизвестная команда!");
    }

    @Test
    public void testCatShelterGetReport() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListener.class.getResource("report.json").toURI()));
        Update update = BotUtils.fromJson(json, Update.class);
        byte[] photo = Files.readAllBytes(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("image/cat.jpg").toURI()));
        long chatId = update.message().chat().id();
        String petName = "Барсик";
        Date lastMessage = new Date(update.message().date() * 1000);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);
        GetFileResponse getFileResponse = BotUtils.fromJson("""
                {
                "ok": true,
                "file": {
                    "file_id": "1"
                    }
                }
                """, GetFileResponse.class);
        List<String> captionMatcher = splitCaption(update.message().caption());
        String ration = captionMatcher.get(0);
        String health = captionMatcher.get(1);
        String behaviour = captionMatcher.get(2);
        Report report = new Report(
                chatId, petName, ration, health, behaviour, lastMessage, photo);

        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);
        when(telegramBot.execute(any(ForwardMessage.class))).thenReturn(sendResponse);
        when(telegramBot.execute(any(GetFile.class))).thenReturn(getFileResponse);
        when(telegramBot.getFileContent(any())).thenReturn(photo);
        when(reportService.getAll()).thenReturn(Collections.singletonList(report));
        when(userContextMock.getPersonCat()).thenReturn(personCatMock);
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);
        when(personCatMock.getCat()).thenReturn(catMock);
        when(catMock.getName()).thenReturn(petName);
        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(reportService, times(1)).uploadReport(chatId, petName, photo, ration,
                health, behaviour, lastMessage);
        ArgumentCaptor<GetFile> getFileArgumentCaptor = ArgumentCaptor.forClass(GetFile.class);
        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        ArgumentCaptor<ForwardMessage> forwardMessageArgumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(telegramBot, times(3)).execute(getFileArgumentCaptor.capture());
        verify(telegramBot, times(3)).execute(sendMessageArgumentCaptor.capture());
        verify(telegramBot, times(3)).execute(forwardMessageArgumentCaptor.capture());
        SendMessage actual = sendMessageArgumentCaptor.getAllValues().get(2);
        ForwardMessage actualForward = forwardMessageArgumentCaptor.getAllValues().get(1);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Ваш отчет принят!");

        Assertions.assertThat(actualForward.getParameters().get("from_chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actualForward.getParameters().get("message_id")).isEqualTo(2);
        verify(userContextService, times(1)).getByChatId(chatId);

    }

    @Test
    public void testDogShelterGetReport() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("report.json").toURI()));
        Update update = BotUtils.fromJson(json, Update.class);
        byte[] photo = Files.readAllBytes(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("image/dog.jpg").toURI()));
        long chatId = update.message().chat().id();
        String petName = "Жорик";
        Date lastMessage = new Date(update.message().date() * 1000);
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);
        GetFileResponse getFileResponse = BotUtils.fromJson("""
                {
                "ok": true,
                "file": {
                    "file_id": "1"
                    }
                }
                """, GetFileResponse.class);
        List<String> captionMatcher = splitCaption(update.message().caption());
        String ration = captionMatcher.get(0);
        String health = captionMatcher.get(1);
        String behaviour = captionMatcher.get(2);
        Report report = new Report(
                chatId, petName, ration, health, behaviour, lastMessage, photo);

        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);
        when(telegramBot.execute(any(ForwardMessage.class))).thenReturn(sendResponse);
        when(telegramBot.execute(any(GetFile.class))).thenReturn(getFileResponse);
        when(telegramBot.getFileContent(any())).thenReturn(photo);
        when(reportService.getAll()).thenReturn(Collections.singletonList(report));
        when(userContextMock.getPersonDog()).thenReturn(personDogMock);
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);
        when(personDogMock.getDog()).thenReturn(dogMock);
        when(dogMock.getName()).thenReturn(petName);
        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(reportService, times(1)).uploadReport(chatId, petName, photo, ration,
                health, behaviour, lastMessage);
        ArgumentCaptor<GetFile> getFileArgumentCaptor = ArgumentCaptor.forClass(GetFile.class);
        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        ArgumentCaptor<ForwardMessage> forwardMessageArgumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(telegramBot, times(3)).execute(getFileArgumentCaptor.capture());
        verify(telegramBot, times(3)).execute(sendMessageArgumentCaptor.capture());
        verify(telegramBot, times(3)).execute(forwardMessageArgumentCaptor.capture());
        SendMessage actual = sendMessageArgumentCaptor.getAllValues().get(2);
        ForwardMessage actualForward = forwardMessageArgumentCaptor.getAllValues().get(1);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Ваш отчет принят!");

        Assertions.assertThat(actualForward.getParameters().get("from_chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actualForward.getParameters().get("message_id")).isEqualTo(2);
        verify(userContextService, times(1)).getByChatId(chatId);

    }

    @Test
    public void testCatShelterGetContact() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("contact.json").toURI()));
        Update update = BotUtils.fromJson(json, Update.class);
        long chatId = update.message().chat().id();
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);


        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.CAT);
        when(userContextMock.getPersonCat()).thenReturn(personCatMock);
        when(personCatService.update(any(PersonCat.class))).thenReturn(personCatMock);
        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        ArgumentCaptor<ForwardMessage> forwardMessageArgumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(telegramBot, times(2)).execute(sendMessageArgumentCaptor.capture());
        verify(telegramBot, times(2)).execute(forwardMessageArgumentCaptor.capture());
        SendMessage actual = sendMessageArgumentCaptor.getAllValues().get(1);
        ForwardMessage actualForward = forwardMessageArgumentCaptor.getAllValues().get(0);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Мы получили ваши контактные данные");

        Assertions.assertThat(actualForward.getParameters().get("from_chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actualForward.getParameters().get("message_id")).isEqualTo(1);
        verify(userContextService, times(2)).getByChatId(chatId);
        verify(personCatService, times(1)).update(any(PersonCat.class));
        verify(personDogService, never()).save(any(PersonDog.class));
    }

    @Test
    public void testDogShelterGetContact() throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("contact.json").toURI()));
        Update update = BotUtils.fromJson(json, Update.class);
        long chatId = update.message().chat().id();
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);


        when(userContextService.getByChatId(chatId)).thenReturn(Optional.of(userContextMock));
        when(userContextMock.getTypeOfShelter()).thenReturn(TypeOfShelter.DOG);
        when(userContextMock.getPersonDog()).thenReturn(personDogMock);
        when(personDogService.save(any(PersonDog.class))).thenReturn(personDogMock);
        when(telegramBot.execute(any())).thenReturn(sendResponse);

        telegramBotUpdatesListener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> sendMessageArgumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        ArgumentCaptor<ForwardMessage> forwardMessageArgumentCaptor = ArgumentCaptor.forClass(ForwardMessage.class);
        verify(telegramBot, times(2)).execute(sendMessageArgumentCaptor.capture());
        verify(telegramBot, times(2)).execute(forwardMessageArgumentCaptor.capture());
        SendMessage actual = sendMessageArgumentCaptor.getAllValues().get(1);
        ForwardMessage actualForward = forwardMessageArgumentCaptor.getAllValues().get(0);

        Assertions.assertThat(actual.getParameters().get("chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actual.getParameters().get("text")).isEqualTo("Мы получили ваши контактные данные");

        Assertions.assertThat(actualForward.getParameters().get("from_chat_id"))
                .isEqualTo(chatId);
        Assertions.assertThat(actualForward.getParameters().get("message_id")).isEqualTo(1);
        verify(userContextService, times(2)).getByChatId(chatId);
        verify(personDogService, times(1)).save(any(PersonDog.class));
        verify(personCatService, never()).update(any(PersonCat.class));
    }

    private List<String> splitCaption(String caption) {
        Pattern pattern = Pattern.compile("(Рацион:)(\\s)(\\W+)(;)\n" +
                "(Самочувствие:)(\\s)(\\W+)(;)\n" +
                "(Поведение:)(\\s)(\\W+)(;)");
        Matcher matcher = pattern.matcher(caption);
        if (matcher.find()) {
            return new ArrayList<>(List.of(matcher.group(3), matcher.group(7), matcher.group(11)));
        } else {
            throw new IllegalArgumentException("Совпадения не найдены");
        }
    }

    private SendMessage getSendMessage(Update update) {
        SendResponse sendResponse = BotUtils.fromJson("""
                {
                    "ok":true
                }
                """, SendResponse.class);
        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdatesListener.process(Collections.singletonList(update));
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(telegramBot).execute(argumentCaptor.capture());
        reset(telegramBot);
        return argumentCaptor.getValue();
    }

    private Update getUpdate(String text) throws URISyntaxException, IOException {
        String json = Files.readString(
                Path.of(TelegramBotUpdatesListenerTest.class.getResource("update.json").toURI()));
        return BotUtils.fromJson(json.replace("%text%", text), Update.class);
    }
}
