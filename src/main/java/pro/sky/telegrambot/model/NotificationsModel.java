package pro.sky.telegrambot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class NotificationsModel {
    public static final String TABLE_NAME = "notification_task";

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "chat_id")
    private long chatId;
    private String message;
    private LocalDateTime datetime;
    @Column(name = "is_sent")
    private boolean isSent = false;






    public Long getId() {
        return id;
    }
    public long getChatId() {
        return chatId;
    }
    public String getMessage() {
        return message;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public boolean isSent() {
        return isSent;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSent(boolean sent) {
        isSent = sent;
    }
}
