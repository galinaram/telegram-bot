package pro.sky.telegrambot.service.ReminderParser;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface ReminderParser {
    String getCreatePattern();
    SendMessage create(Update update);
}
