package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.constant.Button;
import com.example.animalclinicbot.constant.TypeOfShelter;
import com.example.animalclinicbot.model.PersonCat;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.model.UserContext;
import com.example.animalclinicbot.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс TelegramBotUpdatesListener <b>implements</b> UpdatesListener.
 *
 * @author Anna Molodtsova
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private static final String REGEX_MESSAGE = "(Рацион:)(\\s)(\\W+)(;)\n" +
            "(Самочувствие:)(\\s)(\\W+)(;)\n" +
            "(Поведение:)(\\s)(\\W+)(;)";
    private final Pattern pattern = Pattern.compile(REGEX_MESSAGE);

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;
    private final KeyBoardService keyBoardService;
    private final UserContextService userContextService;

    private final PersonDogService personDogService;
    private final PersonCatService personCatService;
    private final ReportService reportService;
    @Value("${volunteer-chat-id}")
    private Long volunteerChatId;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      KeyBoardService keyBoardService,
                                      UserContextService userContextService,
                                      PersonDogService personDogService,
                                      PersonCatService personCatService,
                                      ReportService reportService) {
        this.telegramBot = telegramBot;
        this.keyBoardService = keyBoardService;
        this.userContextService = userContextService;
        this.personDogService = personDogService;
        this.personCatService = personCatService;
        this.reportService = reportService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Метод предназначенный для switch-case,
     * который принимает текст сообщения пользователя и сравнивает со значениями enum класса ButtonCommand
     *
     * @param buttonCommand
     * @return
     */
    public static Button parse(String buttonCommand) {
        Button[] values = Button.values();
        for (Button command : values) {
            if (command.getCommand().equals(buttonCommand)) {
                return command;
            }
        }
        return Button.NOTHING;
    }

    /**
     * Метод, позволяющий отслеживать и организовывать весь процесс общения с пользователем.
     *
     * @param updates
     */
    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Handles update: {}", update);
                Message message = update.message();
                long chatId = message.chat().id();
                String text = message.text();
                int messageId = message.messageId();
                Contact contact = update.message().contact();

                if (text != null && update.message().photo() == null && contact == null) {
                    switch (parse(text)) {
                        case START -> {
                            if (userContextService.getByChatId(chatId).isEmpty()) {
                                sendResponseMessage(chatId, "Привет! Я могу показать информацию о приютах," +
                                        "как взять животное из приюта и принять отчет о питомце");
                                UserContext userContext = new UserContext();
                                userContext.setChatId(chatId);
                                userContextService.saveUserContext(userContext);
                            }
                            keyBoardService.chooseMenu(chatId);
                        }
                        case CAT -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getPersonCat() == null) {
                                    PersonCat personCat = new PersonCat();
                                    personCat.setChatId(chatId);
                                    personCatService.create(personCat);
                                    userContext.setPersonCat(personCat);
                                }
                                userContext.setTypeOfShelter(TypeOfShelter.CAT);
                                userContextService.saveUserContext(userContext);
                                sendResponseMessage(chatId, "Вы выбрали кошачий приют.");
                                keyBoardService.shelterMainMenu(chatId);
                            }

                        }
                        case DOG -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getPersonDog() == null) {
                                    PersonDog personDog = new PersonDog();
                                    personDog.setChatId(chatId);
                                    personDogService.save(personDog);
                                    userContext.setPersonDog(personDog);
                                }
                                userContext.setTypeOfShelter(TypeOfShelter.DOG);
                                userContextService.saveUserContext(userContext);
                                sendResponseMessage(chatId, "Вы выбрали собачий приют.");
                                keyBoardService.shelterMainMenu(chatId);
                            }
                        }
                        case MAIN_MENU -> {
                            keyBoardService.shelterMainMenu(chatId);
                        }
                        case SHELTER_INFO_MENU -> {
                            keyBoardService.shelterInfoMenu(chatId);
                        }
                        case HOW_ADOPT_PET_INFO -> {
                            keyBoardService.shelterInfoHowAdoptPetMenu(chatId);
                        }
                        case SHELTER_INFO -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendResponseMessage(chatId, """
                                            Информация о кошачем приюте - ...
                                            Рекомендации о технике безопасности на территории кошачего приюта - ...
                                            Контактные данные охраны - ...
                                            """);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendResponseMessage(chatId, """
                                            Информация о собачем приюте - ...
                                            Рекомендации о технике безопасности на территории собачего приюта - ...
                                            Контактные данные охраны - ...
                                            """);
                                }
                            }
                        }
                        case SHELTER_ADDRESS_SCHEDULE -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendResponseMessage(chatId, """
                                            Адрес кошачего приюта - ...
                                            График работы - ...
                                            """);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendResponseMessage(chatId, """
                                            Адрес собачего приюта - ...
                                            График работы - ...
                                            """);
                                }
                            }
                        }
                        case RECOMMENDATIONS_LIST -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendResponseMessage(chatId, """
                                            Правила знакомства с животным - ...
                                            Список рекомендаций - ...
                                            Список причин отказа в выдаче животного - ...
                                            """);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendResponseMessage(chatId, """
                                            Правила знакомства с животным - ...
                                            Список рекомендаций - ...
                                            Советы кинолога по первичному общению с собакой - ...
                                            Рекомендации по проверенным кинологам для дальнейшего обращения к ним
                                            Список причин отказа в выдаче животного - ...
                                            """);
                                }
                            }
                        }
                        case DOCUMENTS_LIST -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendResponseMessage(chatId,
                                            "Для взятия кота из приюта необходимы такие документы: ...");
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendResponseMessage(chatId,
                                            "Для взятия собаки из приюта необходимы такие документы: ...");
                                }
                            }
                        }
                        case VOLUNTEER -> {
                            sendResponseMessage(chatId, "Мы передали ваше сообщение волонтеру. " +
                                    "Если у вас закрытый профиль отправьте контактные данные," +
                                    "с помощью кнопки в меню - Отправить контактные данные");
                            sendForwardMessage(chatId, messageId);
                        }
                        case SEND_REPORT -> {
                            sendResponseMessage(chatId, """
                                    Для отчета необходима фотография, рацион,
                                    самочувствие и изменение в поведении питомца.
                                    Загрузите фото, а в подписи к нему, скопируйте и заполните текст ниже.
                                                                        
                                    Рацион: ваш текст;
                                    Самочувствие: ваш текст;
                                    Поведение: ваш текст;
                                    """);
                        }
                        default -> sendResponseMessage(chatId, "Неизвестная команда!");
                    }
                } else if (update.message().contact() != null && userContextService
                        .getByChatId(chatId).isPresent()) {
                    UserContext userContext = userContextService.getByChatId(chatId).get();
                    if (userContext.getTypeOfShelter().equals(
                            TypeOfShelter.CAT) && update.message() != null && contact != null) {
                        PersonCat personCat = userContext.getPersonCat();
                        personCat.setPhone(contact.phoneNumber());
                        personCat.setName(contact.firstName());
                        personCatService.update(personCat);
                    } else if (userContext.getTypeOfShelter().equals(
                            TypeOfShelter.DOG) && update.message() != null && contact != null) {
                        PersonDog personDog = userContext.getPersonDog();
                        personDog.setPhone(contact.phoneNumber());
                        personDog.setName(contact.firstName());
                        personDogService.save(personDog);
                    }
                    sendForwardMessage(chatId, messageId);
                    sendResponseMessage(chatId, "Мы получили ваши контактные данные");

                } else if (update.message().photo() != null && update.message().caption() != null) {
                    Calendar calendar = new GregorianCalendar();
                    long compareTime = calendar.get(Calendar.DAY_OF_MONTH);
                    long daysOfReports = reportService.getAll().stream()
                            .filter(s -> s.getChatId() == chatId)
                            .count();
                    Date lastMessageDate = reportService.getAll().stream()
                            .filter(s -> s.getChatId() == chatId)
                            .map(Report::getLastMessage)
                            .max(Date::compareTo)
                            .orElse(null);
                    long numberOfDay = 0L;
                    if (lastMessageDate != null) {
                        numberOfDay = lastMessageDate.getDate();
                    } else {
                        numberOfDay = message.date();
                    }
                    if (daysOfReports < 30) {
                        if (compareTime != numberOfDay) {
                            UserContext userContext = userContextService.getByChatId(chatId).get();
                            if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)
                                    && userContext.getPersonCat().getCat() != null) {
                                String petName = userContext.getPersonCat().getCat().getName();
                                getReport(message, petName);
                                daysOfReports++;
                            } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)
                                    && userContext.getPersonDog().getDog() != null) {
                                String petName = userContext.getPersonDog().getDog().getName();
                                getReport(message, petName);
                                daysOfReports++;
                            } else {
                                sendResponseMessage(chatId, "У вас нет животного!");
                            }
                        } else {
                            sendResponseMessage(chatId, "Вы уже отправляли сегодня отчет");
                        }

                    }
                    if (daysOfReports == 30) {
                        sendResponseMessage(chatId, "Вы прошли испытательный срок!");
                        sendResponseMessage(volunteerChatId, "Владелец животного с chatId " + chatId
                                + " прошел испытательный срок!");
                    }

                } else if (update.message().photo() != null && update.message().caption() == null) {
                    sendResponseMessage(chatId, "Отчет нужно присылать с описанием!");
                }

            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод отправки текстовых сообщений.
     *
     * @param chatId
     * @param text
     */
    public void sendResponseMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод пересылки сообщения волонтеру
     *
     * @param chatId
     * @param messageId
     */
    public void sendForwardMessage(long chatId, int messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(volunteerChatId, chatId, messageId);
        SendResponse sendResponse = telegramBot.execute(forwardMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    /**
     * Метод получения отчета и отправки его волонтеру
     *
     * @param message
     */
    public void getReport(Message message, String petName) {
        PhotoSize photo = message.photo()[0];
        String caption = message.caption();
        Long chatId = message.chat().id();

        List<String> captionMatcher = splitCaption(caption);

        String ration = captionMatcher.get(0);
        String health = captionMatcher.get(1);
        String behaviour = captionMatcher.get(2);

        GetFile getFile = new GetFile(photo.fileId());
        GetFileResponse getFileResponse = telegramBot.execute(getFile);

        try {
            File file = getFileResponse.file();
            byte[] fileContent = telegramBot.getFileContent(file);

            long date = message.date();
            Date lastMessage = new Date(date * 1000);
            reportService.uploadReport(
                    chatId, petName, fileContent, ration,
                    health, behaviour, lastMessage);
            sendForwardMessage(chatId, message.messageId());
            sendResponseMessage(chatId, "Ваш отчет принят!");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки фото!");
        }

    }

    /**
     * Метод для разбивки описания под фотографии для добавления полученного текста в отчет
     *
     * @param caption
     * @return
     */
    private List<String> splitCaption(String caption) {
        if (caption == null || caption.isBlank()) {
            throw new IllegalArgumentException("Описание под фотографией не должно быть пустым. Отправьте отчёт заново!");
        }
        Matcher matcher = pattern.matcher(caption);
        if (matcher.find()) {
            return new ArrayList<>(List.of(matcher.group(3), matcher.group(7), matcher.group(11)));
        } else {
            throw new IllegalArgumentException("Проверьте правильность введённых данных и отправьте отчёт ещё раз.");
        }
    }

    /**
     * Метод отслеживания своеврменной отправки отчетов
     */
    @Scheduled(cron = "@daily")
    public void sendWarning() {
        for (UserContext userContext : userContextService.getAll()) {
            long chatId = userContext.getChatId();
            long daysOfReports = reportService.getAll().stream()
                    .filter(s -> Objects.equals(s.getChatId(), chatId))
                    .count();
            if (daysOfReports < 30 && daysOfReports != 0) {
                long twoDay = 172800000;
                Date nowTime = new Date(new Date().getTime() - twoDay);
                Date lastMessageDate = reportService.getAll().stream()
                        .filter(s -> Objects.equals(s.getChatId(), chatId))
                        .map(Report::getLastMessage)
                        .max(Date::compareTo)
                        .orElse(null);
                if (lastMessageDate != null) {
                    if (lastMessageDate.before(nowTime)) {
                        sendResponseMessage(chatId, "Вы не отправляли отчёты уже более двух дней. " +
                                "Пожалуйста, отправьте отчёт или выйдите на связь с волонтёрами.");
                        sendResponseMessage(volunteerChatId, "Владелец животного с chatId " + chatId
                                + " не отправлял отчёты уже более двух дней!");
                    }
                }
            }
        }

    }
}
