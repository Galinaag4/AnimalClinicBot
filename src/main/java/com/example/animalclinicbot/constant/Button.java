package com.example.animalclinicbot.constant;

public enum Button {
    START("/start"),

    CAT("Кошачий"),
    DOG("Собачий"),
    MAIN_MENU("Главное меню"),
    SHELTER_INFO_MENU("Узнать информацию о приюте"),
    HOW_ADOPT_PET_INFO("Как взять животное из приюта"),
    VOLUNTEER("Позвать волонтера на помощь"),
    SHELTER_INFO("Общая информация"),
    RECOMMENDATIONS_LIST("Список рекомендаций и советов"),
    DOCUMENTS_LIST("Список необходимых документов"),
    SEND_CONTACT("Отправить контактные данные"),
    SEND_REPORT("Прислать отчет о питомце"),
    NOTHING("");


    private final String command;

    Button(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
