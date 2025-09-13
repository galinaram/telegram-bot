package pro.sky.telegrambot.job;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notifications;
import pro.sky.telegrambot.repository.NotificationsRepository;
import pro.sky.telegrambot.service.ReminderParser.ReminderParser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class NotificationTaskJob {
    private final TelegramBot telegramBot;
    private final NotificationsRepository notificationsRepository;
    Logger logger = LoggerFactory.getLogger(ReminderParser.class);

    public NotificationTaskJob(
            TelegramBot telegramBot,
            NotificationsRepository notificationsRepository
    ) {
        this.telegramBot = telegramBot;
        this.notificationsRepository = notificationsRepository;
    }

    @Scheduled(cron = "${cron.interval.notification.task}")
    @SchedulerLock(name = "notificationTaskJob")
    public void sendingNotifications() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->sendingNotifications");

        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<Notifications> list = notificationsRepository.findByDatetimeLessThanEqualAndIsSentFalse(date);

        for (Notifications notificationTask : list) {
            telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getMessage()));
            notificationTask.setSent(true);
            notificationsRepository.save(notificationTask);
        }
    }
}
