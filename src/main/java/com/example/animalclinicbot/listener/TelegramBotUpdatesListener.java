package com.example.animalclinicbot.listener;

import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.model.UserContext;
import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.repository.PersonDogRepository;
import com.example.animalclinicbot.repository.ReportRepository;
import com.example.animalclinicbot.repository.UserContextRepository;
import com.example.animalclinicbot.service.KeyBoardService;
import com.example.animalclinicbot.service.ReportService;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String START_CMD = "/start";

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
    private static final String INFO_SHELTER_DOG = "Приют для животных находится по адресу г.Москва, ул.Ленина 25, стр.2," +
            "контакты: 8(495) 111 11 11 - директор Николаева Людмила Викторовна.\n\n" +
            "8(495) 333 33 33 - горячая линия приюта\n" +
            "для помощи животным: р/с 42200053355655554579\n\n" +
            "Нужды приюта: сухие корма, ветпрепараты, дез.средства";
    private static final String ADOPT_CAT_INFO = "Правила содержания и ухода кот \nhttps://google.com \n" +
            "Список документов \nhttps://yandex.ru\n" +
            "";
    private static final String ADOPT_DOG_INFO = "Правила содержания и ухода собака \nhttps://google.com \n" +
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
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private PersonDogRepository personDogRepository;
    @Autowired
    private PersonCatRepository personCatRepository;
    @Autowired
    private KeyBoardService keyBoardService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserContextRepository userContextRepository;
    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
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

                switch (textUpdate) {

                    case START_CMD:
                        sendMessage(chatId, nameUser + START_TEXT);
                        keyBoardService.chooseMenu(chatId);
                        break;
                    case "\uD83D\uDC31 CAT":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        sendMessage(chatId, "Вы выбрали кошку.");
                        keyBoardService.sendMenuCat(chatId);
                        break;
                    case "\uD83D\uDC36 DOG":
                        if (userContextRepository.findByChatId(chatId)
                                .map(UserContext::isCatShelter)
                                .orElse(false)) ;
                        keyBoardService.sendMenuDog(chatId);
                        sendMessage(chatId, "Вы выбрали собаку.");
                        break;
                    case "Главное меню":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        keyBoardService.sendMenuInfoShelterCat(chatId);
                        sendMessage(chatId,INFO_SHELTER_CAT);
                        break;
                    case "Информация о приюте для кошек":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        keyBoardService.sendMenuInfoShelterCat(chatId);
                        try {
                            byte[] photo = Files.readAllBytes(
                                    Paths.get(KeyBoardService.class.getResource("/Priyut.jpg").toURI())
                            );
                            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
                            sendPhoto.caption(INFO_SHELTER_CAT);
                            telegramBot.execute(sendPhoto);
                        } catch (IOException | URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Информация о приюте для собак":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(false)) ;
                        keyBoardService.sendMenuInfoShelterDog(chatId);
                        try {
                            byte[] photo = Files.readAllBytes(
                                    Paths.get(KeyBoardService.class.getResource("/Priyut.jpg").toURI())
                            );
                            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
                            sendPhoto.caption(INFO_SHELTER_DOG);
                            telegramBot.execute(sendPhoto);
                        } catch (IOException | URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "Как взять кошку из приюта":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        keyBoardService.sendMenuTakeCat(chatId);
                        sendMessage(chatId, TAKE_CAT);
                        break;
                    case "Как взять собаку из приюта":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(false)) ;
                        keyBoardService.sendMenuTakeDog(chatId);
                        sendMessage(chatId, TAKE_DOG);
                        break;
                    case "Содержание и уход за кошкой":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        keyBoardService.sendMenuTakeCat(chatId);
                        sendMessage(chatId, ADOPT_CAT_INFO);
                        break;

                    case "Содержание и уход за собакой":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(false)) ;
                        keyBoardService.sendMenuTakeDog(chatId);
                        sendMessage(chatId, ADOPT_DOG_INFO);
                        break;
                    case "Прислать отчет о кошке":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(true)) ;
                        sendMessage(chatId, REPORT_CASE);
                        sendMessage(chatId, REPORT_CASE_EXAMPLE);
                        break;
                    case "Прислать отчет о собаке":
                        if (userContextRepository.findByChatId(chatId).map(UserContext::isCatShelter).orElse(false)) ;
                        sendMessage(chatId, REPORT_CASE);
                        sendMessage(chatId, REPORT_CASE_EXAMPLE);
                        break;
                    case "Информация о возможностях бота":
                        sendMessage(chatId, ABOUT_BOT);
                        break;
                    case "Вернуться в меню":
                        keyBoardService.chooseMenu(chatId);
                        break;
                    case "Приветствую!":
                        if (messageId != null) {
                            sendReplyMessage(chatId, "Привет", messageId);
                            break;
                        }
                    case "Позвать волонтера на помощь":
                        sendMessage(chatId, "Я передал ваше сообщение волонтеру. " +
                                "Если у вас закрытый профиль - поделитесь контактом. " +
                                "Справа сверху 3 точки - отправить свой телефон");
                        sendForwardMessage(chatId, messageId);
                        break;
                    case "":
                        System.out.println("Нельзя");
                        sendMessage(chatId, "Пустое сообщение");
                        break;
                    default:
                        sendReplyMessage(chatId, "Я не знаю такой команды", messageId);
                        break;
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
                    .filter(i -> i.getChatIdPersonCat() == finalChatId)
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
                reportService.uploadReportData(update.message().chat().id(), fileContent, file,
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
                reportService.uploadReportData(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        }
    }

    private void chooseCatShelter(Long chatId) {
        UserContext userContext = new UserContext();
        userContext.setChatId(chatId);
        userContext.setCatShelter(true);
        userContextRepository.save(userContext);
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
