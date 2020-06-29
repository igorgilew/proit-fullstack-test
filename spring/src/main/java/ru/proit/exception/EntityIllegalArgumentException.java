package ru.proit.exception;

/**
 * Исключение вырасывается при некорректных параметрах метода сервиса
 */
public class EntityIllegalArgumentException extends BaseException {
    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
