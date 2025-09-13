package pro.sky.telegrambot.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class Notifications {
    public static final String TABLE_NAME = "notification_task";

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "message")
    private String message;
    @Column(name = "datetime")
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
