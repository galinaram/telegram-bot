package pro.sky.telegrambot.service.ReminderParser;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notifications;
import pro.sky.telegrambot.repository.NotificationsRepository;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReminderParserImpl implements ReminderParser {
    public final String REMINDER_PATTERN = "(0[1-9]|[12]\\d|3[01]).(0?[1-9]|1[012]).((?:19|20)\\d\\d) (0\\d|1\\d|2[0-3]):([0-5]\\d) ([\\s\\S]*)";
    private final NotificationsRepository notificationsRepository;
    Logger logger = LoggerFactory.getLogger(ReminderParser.class);

    public ReminderParserImpl(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public String getCreatePattern() {
        return REMINDER_PATTERN;
    }

    @Override
    public SendMessage create(Update update) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->create");

//        try{
            Matcher matcher = Pattern.compile(REMINDER_PATTERN).matcher(update.message().text());

            if (matcher.find()) {
                LocalDateTime nowDateTime = LocalDateTime.now();
                LocalDateTime notificationDateTime = LocalDateTime.parse(matcher.group(3) + "-" + matcher.group(2) + "-" + matcher.group(1) + "T" + matcher.group(4) + ":" + matcher.group(5) + ":00");

                Notifications notificationsModel = new Notifications();
                notificationsModel.setChatId(update.message().chat().id());
                notificationsModel.setMessage(matcher.group(6));
                notificationsModel.setDatetime(notificationDateTime);

                if (notificationDateTime.isBefore(nowDateTime) || notificationDateTime.isEqual(nowDateTime)) {
                    return new SendMessage(update.message().chat().id(), "Я не могу создать уведомление в прошлом");
                }

                if (notificationsRepository.existsByChatIdEqualsAndMessageEqualsIgnoreCaseAndDatetimeEqualsAndIsSentFalse(
                        notificationsModel.getChatId(), notificationsModel.getMessage(), notificationsModel.getDatetime())
                ) {
                    return new SendMessage(update.message().chat().id(), "Такое уведомление уже есть");
                }

                notificationsRepository.save(notificationsModel);
                return new SendMessage(update.message().chat().id(), "Уведомление поставлено");
            }
            
//        } catch (Exception e) {
//            return new SendMessage(update.message().chat().id(), "Не могу создать уведомление");
//        }
        return null;
    }


//    private static void create(String input) {
//        Pattern pattern = Pattern.compile(REMINDER_PATTERN);
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.matches()) {
//            String dateTime = matcher.group(1);
//            String reminderText = matcher.group(3);
//
//            Reminder reminder = new Reminder(dateTime, reminderText);
//            saveToDatabase(reminder);
//        } else {
//            System.out.println("Неверный формат сообщения.");
//        }
//    }
//
//    private static void saveToDatabase(Reminder reminder) {
//        // Здесь вы можете реализовать логику сохранения в БД
//        System.out.println("Сохранено в БД: " + reminder);
//    }
}
