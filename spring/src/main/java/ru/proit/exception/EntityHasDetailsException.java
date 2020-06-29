package ru.proit.exception;

import org.springframework.util.Assert;

/**
 * Исключение выбрасывается если удаляемый объект имеет потомков
 */
public class EntityHasDetailsException extends BaseException {
    public EntityHasDetailsException(String message) {
        super(message);
    }

    public EntityHasDetailsException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Идентификатор не может быть пустым");
        Assert.hasText(id.toString(), "Идентификатор не может быть пустым");
        return String.format("%s ссылается на удаляемый объект с идентификатором %s ", type, id);
    }

}
