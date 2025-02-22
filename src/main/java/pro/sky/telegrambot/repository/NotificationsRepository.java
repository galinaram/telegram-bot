package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pro.sky.telegrambot.model.Notifications;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    boolean existsByChatIdEqualsAndMessageEqualsIgnoreCaseAndDatetimeEqualsAndIsSentFalse(
            @NonNull long chatId, @NonNull String message, @NonNull LocalDateTime datetime
    );

    List<Notifications> findByDatetimeLessThanEqualAndIsSentFalse(@NonNull LocalDateTime datetime);
}
