package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.CommandsService.CommandsService;
import pro.sky.telegrambot.service.ReminderParser.ReminderParser;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final ReminderParser reminderParser;
    private final CommandsService commandsService;
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(ReminderParser reminderParser, CommandsService commandsService, TelegramBot telegramBot) {
        this.reminderParser = reminderParser;
        this.commandsService = commandsService;
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message().text() != null) {
                if (update.message().text().equals("/start")) {
                    System.out.println("/start founded");
                    telegramBot.execute(commandsService.start(update));
                } else if (Pattern.matches(reminderParser.getCreatePattern(), update.message().text())) {
                    telegramBot.execute(reminderParser.create(update));
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
