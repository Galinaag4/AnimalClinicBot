package com.example.animalclinicbot.constants;
/**
 * Названия кнопок основной клавиатуры
 */
public enum ButtonNameEnum {

    CALL_VOLUNTEER_BUTTON("Позвать волонтера");



    private final String ButtonName;

    ButtonNameEnum(String buttonName) {
        ButtonName = buttonName;
    }

    public String getButtonName() {
        return ButtonName;
    }
}
