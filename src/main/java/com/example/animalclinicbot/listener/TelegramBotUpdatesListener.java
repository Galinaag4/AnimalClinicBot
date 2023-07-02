package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.constant.Button;
import com.example.animalclinicbot.constant.TypeOfShelter;
import com.example.animalclinicbot.keyboard.KeyBoardService;
import com.example.animalclinicbot.model.PersonCat;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.model.UserContext;
import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.repository.PersonDogRepository;
import com.example.animalclinicbot.repository.ReportRepository;
import com.example.animalclinicbot.repository.UserContextRepository;
import com.example.animalclinicbot.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Класс TelegramBotUpdatesListener <b>implements</b> UpdatesListener.
 *
 * @author Anna Molodtsova
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private static final String START_TEXT = "Приветствую вас! Этот бот поможет ответить на ваши вопросы о том, " +
            "что нужно знать,чтобы забрать животное из приюта";

    private static final String ABOUT_BOT = "Я могу:*\n" +
            "✅ рассказать вам о приюте для животных\n" +
            "✅ показать животных которые ищут хозяина\n" +
            "✅ ознакомить о правилах содержания животных\n\n" +
            "И помните мы в ответе за тех кого приручили\n\n" +
            "Удачи!\n\n";
    private static final String INFO_SHELTER_CAT = "Приют для животных находится по адресу г.Москва, ул.Ленина 25, стр.2," +
            "контакты: 8(495) 333 33 33 - директор Морозова Людмила Федоровна.\n\n" +
            "8(495) 222 22 22 - горячая линия приюта\n" +
            "для помощи животным: р/с 42200053355655554578\n\n" +
            "Нужды приюта: сухие корма, ветпрепараты, дез.средства";
    private static final String INFO_SHELTER_DOG = "Приют для животных находится по адресу г.Москва, ул.Ленина 27, стр.1," +
            "контакты: 8(495) 333 33 22 - директор Иванова Людмила Федоровна.\n\n" +
            "8(495) 222 22 33 - горячая линия приюта\n" +
            "для помощи животным: р/с 42200053355655554579\n\n" +
            "Нужды приюта: сухие корма, ветпрепараты, дез.средства";

    private static final String ADOPT_INFO = "Правила содержания и ухода \nhttps://google.com \n" +
            "Список документов \nhttps://yandex.ru\n" +
            "";
    private static final String VOLUNTEER_CONTACT = "Контактные данные волонтера  \n @anna_ivanova \n" +
            "Телефон - +7 910 645 17 10 \n";
    private static final String REPORT_CASE = "Для отчета нужна следующая информация:\n" +
            "- Фото животного.  \n" +
            "- Рацион животного\n" +
            "- Общее самочувствие и привыкание к новому месту\n" +
            "- Изменение в поведении: отказ от старых привычек, приобретение новых.\nСкопируйте следующий пример. Не забудьте прикрепить фото";

    private static final String REPORT_CASE_EXAMPLE = "Рацион: ваш текст;\n" +
            "Самочувствие: ваш текст;\n" +
            "Поведение: ваш текст;";

    private static final String REGEX_MESSAGE = "(Рацион:)(\\s)(\\W+)(;)\n" +
            "(Самочувствие:)(\\s)(\\W+)(;)\n" +
            "(Поведение:)(\\s)(\\W+)(;)";
    private static final String TAKE_CAT = "Как выбрать кошку из приюта:*\n" +
            "1. Определитесь с возрастом. \n" +
            "2. Выберите пол.\n" +
            "3. Выберите породу\n\n" +
            "4. Познакомтесь с кошкой.\n\n" +
            "5. Хорошо взвесте все за и против!\n\n";
    private static final String TAKE_DOG = "Как выбрать собаку из приюта:*\n" +
            "1. Определитесь с возрастом. \n" +
            "2. Выберите пол.\n" +
            "3. Выберите породу\n\n" +
            "4. Познакомтесь с собакой.\n\n" +
            "5. Хорошо взвесте все за и против!\n\n";

    private static final long telegramChatVolunteers = -748879962L;

    private long daysOfReports;



    private KeyBoardService keyBoardService;

    private ReportService reportService;

    private PersonCatService personCatService;

    private PersonDogService personDogService;

    private UserContextService userContextService;

    private UserContextRepository userContextRepository;
    private PersonCatRepository personCatRepository;
    private PersonDogRepository personDogRepository;
    private ReportRepository reportRepository;
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(KeyBoardService keyBoardService, ReportService reportService, PersonCatService personCatService, PersonDogService personDogService, UserContextService userContextService, UserContextRepository userContextRepository, PersonCatRepository personCatRepository, PersonDogRepository personDogRepository, ReportRepository reportRepository, TelegramBot telegramBot) {
        this.keyBoardService = keyBoardService;
        this.reportService = reportService;
        this.personCatService = personCatService;
        this.personDogService = personDogService;
        this.userContextService = userContextService;
        this.userContextRepository = userContextRepository;
        this.personCatRepository = personCatRepository;
        this.personDogRepository = personDogRepository;
        this.reportRepository = reportRepository;
        this.telegramBot = telegramBot;
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
     * Организация процесса общения с пользователем
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String nameUser = update.message().chat().firstName();
            String textUpdate = update.message().text();
            Integer messageId = update.message().messageId();
            long chatId = update.message().chat().id();
            Calendar calendar = new GregorianCalendar();
            daysOfReports = reportRepository.findAll().stream()
                    .filter(s -> s.getChatId() == chatId)
                    .count() + 1;
            try {
                long compareTime = calendar.get(Calendar.DAY_OF_MONTH);

                Long lastMessageTime = reportRepository.findAll().stream()
                        .filter(s -> s.getChatId() == chatId)
                        .map(Report::getLastMessage)
                        .map(date -> date.getTime() / 1000)
                        .max(Long::compare)
                        .orElseGet(() -> null);
                if (lastMessageTime != null) {
                    Date lastMessage = new Date(lastMessageTime * 1000);
                    long numberOfDay = lastMessage.getDate();

                    if (daysOfReports < 30) {
                        if (compareTime != numberOfDay) {
                            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                getReport(update);
                            }
                        } else {
                            if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                sendMessage(chatId, "Сегодня мы уже получали отчет от вас!");
                            }
                        }
                        if (daysOfReports == 31) {
                            sendMessage(chatId, "Испытательный срок пройден!");
                        }
                    }
                } else {
                    if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                        getReport(update);
                    }
                }
                if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
                    sendMessage(chatId, "Пожалуйста, вышлите отчет с описанием!");
                }

                if (update.message() != null && update.message().contact() != null) {
                    shareContact(update);
                }

                    switch (parse(textUpdate)) {


                        case START-> {
                            if (userContextService.getByChatId(chatId).isEmpty()) {
                                UserContext userContext = new UserContext();
                                userContext.setChatId(chatId);
                                userContextService.saveUserContext(userContext);
                            }

                            sendMessage(chatId, nameUser + START_TEXT);
                            keyBoardService.chooseMenu(chatId);
                        }
                    case CAT-> {
                        if (userContextService.getByChatId(chatId).isPresent()) {
                            UserContext userContext = userContextService.getByChatId(chatId).get();
                            if (userContext.getPersonCat() == null) {
                                PersonCat personCat = new PersonCat();
                                personCat.setChatId(chatId);
                                personCatRepository.save(personCat);
                                userContext.setPersonCat(personCat);
                            }
                            userContext.setTypeOfShelter(TypeOfShelter.CAT);
                            userContextService.saveUserContext(userContext);
                            sendMessage(chatId, "Вы выбрали кошку.");
                            keyBoardService.shelterMainMenu(chatId);
                        }
                    }
                    case DOG->{
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
                            sendMessage(chatId, "Вы выбрали собаку.");
                            keyBoardService.shelterMainMenu(chatId);
                        }
                        }

                    case MAIN_MENU-> {
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
                                    sendMessage(chatId, INFO_SHELTER_CAT);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendMessage(chatId, INFO_SHELTER_DOG);
                                }
                            }
                        }
                        case RECOMMENDATIONS_LIST -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendMessage(chatId, TAKE_CAT);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendMessage(chatId, TAKE_DOG);
                                }
                            }
                        }
                        case DOCUMENTS_LIST -> {
                            if (userContextService.getByChatId(chatId).isPresent()) {
                                UserContext userContext = userContextService.getByChatId(chatId).get();
                                if (userContext.getTypeOfShelter().equals(TypeOfShelter.CAT)) {
                                    sendMessage(chatId, ADOPT_INFO);
                                } else if (userContext.getTypeOfShelter().equals(TypeOfShelter.DOG)) {
                                    sendMessage(chatId, ADOPT_INFO);
                                }
                            }
                        }
                        case VOLUNTEER -> {
                            sendMessage(chatId, "Я передал ваше сообщение волонтеру. " +
                                    "Если у вас закрытый профиль - поделитесь контактом. " +
                                    "Справа сверху 3 точки - отправить свой телефон");
                            sendForwardMessage(chatId, messageId);
                        }
                        case SEND_REPORT -> {
                            sendMessage(chatId, REPORT_CASE+REPORT_CASE_EXAMPLE);
                        }
                        case NOTHING -> {
                            System.out.println("Нельзя");
                            sendMessage(chatId, "Пустое сообщение");
                        }
                        default-> sendReplyMessage(chatId, "Я не знаю такой команды", messageId);
                    }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод отправки ответного сообщения
     */
    public void sendReplyMessage(Long chatId, String messageText, Integer messageId) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyToMessageId(messageId);
        telegramBot.execute(sendMessage);
    }

    /**
     * Метод отправки пересылаемого сообщения
     */
    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(telegramChatVolunteers, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }

    /**
     * Метод отправки сообщений.
     */
//            public void sendResponseMessage(long chatId, String text) {
//                SendMessage sendMessage = new SendMessage(chatId, text);
//                SendResponse sendResponse = telegramBot.execute(sendMessage);
//                if (!sendResponse.isOk()) {
//                    logger.error("Error during sending message: {}", sendResponse.description());
//                }
//            }
    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    /**
     * Метод доступа к контакту.
     */
    public void shareContact(Update update) {
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            long finalChatId = update.message().chat().id();
            var sortChatId = personDogRepository.findAll().stream()
                    .filter(i -> i.getChatId() == finalChatId)
                    .collect(Collectors.toList());
            var sortChatIdCat = personCatRepository.findAll().stream()
                    .filter(i -> i.getChatId() == finalChatId)
                    .collect(Collectors.toList());

            if (!sortChatId.isEmpty() || !sortChatIdCat.isEmpty()) {
                sendMessage(finalChatId, "Вы уже в базе!");
                return;
            }
            if (lastName != null) {
                String name = firstName + " " + lastName + " " + username;
                sendMessage(finalChatId, "Вас успешно добавили в базу. Скоро вам перезвонят.");
                return;
            }
            sendMessage(finalChatId, "Вас успешно добавили в базу! Скоро вам перезвонят.");
            // Сообщение в чат волонтерам
            sendMessage(telegramChatVolunteers, phone + " " + firstName + " Добавил(а) свой номер в базу");
            sendForwardMessage(finalChatId, update.message().messageId());
        }
    }


    /**
     * Метод получения отчетов.
     */
    public void getReport(Update update) {
        Pattern pattern = Pattern.compile(REGEX_MESSAGE);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(7);
            String habits = matcher.group(11);

            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file,
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));

                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportService.uploadReport(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        }
    }

    /**
     * Метод для отслеживания отправки отчетов.
     */
    @Scheduled(cron = "* 30 21 * * *")
    public void checkResults() {
        if (daysOfReports < 30) {
            var twoDay = 172800000;
            var nowTime = new Date().getTime() - twoDay;
            var getDistinct = this.reportRepository.findAll().stream()
                    .sorted(Comparator
                            .comparing(Report::getChatId))
                    .max(Comparator
                            .comparing(Report::getLastMessage));
            getDistinct.stream()
                    .filter(i -> i.getLastMessage().getTime() * 1000 < nowTime)
                    .forEach(s -> sendMessage(s.getChatId(), "Мы так и не получили ваш отчет!" +
                            " Отправьте его как можно скорее"));
        }
    }

}
