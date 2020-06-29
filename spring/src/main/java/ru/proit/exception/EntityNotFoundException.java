package ru.proit.exception;

import org.springframework.util.Assert;

/**
 * Исключение выбрасывается если объект отсутствует в базе
 */
public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Идентификатор не может быть пустым");
        Assert.hasText(id.toString(), "Идентификатор не может быть пустым");
        return String.format("%s с ключом %s не найден", type, id);
    }

}
