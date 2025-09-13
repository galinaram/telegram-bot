# Telegram Bot - Умный планировщик напоминаний

Spring Boot приложение для создания и управления напоминаниями через Telegram.

## 🚀 Возможности

- 📅 Создание напоминаний простым текстовым сообщением
- ⏰ Автоматическая отправка уведомлений в заданное время
- 💾 Надежное хранение данных в PostgreSQL
- 🔄 Защита от дубликатов напоминаний
- ⚡ Автоматическое расписание с помощью Spring Scheduler

## 📋 Формат сообщений

Для создания напоминания отправьте боту сообщение в формате:
text


**Пример:**

01.01.2025 20:00 Поздравить друзей с Новым годом
text


## 🛠 Технологический стек

- **Java 11**
- **Spring Boot 2.6.5**
- **Spring Data JPA** - работа с базой данных
- **PostgreSQL** - реляционная база данных
- **Liquibase** - управление миграциями БД
- **Telegram Bot API** - интеграция с Telegram
- **ShedLock** - координация распределенных задач

## 📦 Установка и запуск

### Предварительные требования

- Java 11 или выше
- PostgreSQL
- Maven
- Telegram bot token ([получить у @BotFather](https://t.me/BotFather))

### 1. Клонирование репозитория

```bash
git clone https://github.com/your-username/telegram-bot.git
cd telegram-bot
```

### 2. Настройка базы данных

Создайте базу данных в PostgreSQL:
```sql
CREATE DATABASE telegram;
```
### 3. Настройка конфигурации

Отредактируйте src/main/resources/application.properties:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/telegram
spring.datasource.username=your_username
spring.datasource.password=your_password

telegram.bot.name=YourBotName
telegram.bot.token=your_bot_token_here
```
### 4. Запуск приложения
```bash
mvn spring-boot:run
```

## 🏗 Архитектура проекта
```text
src/
├── main/
│   ├── java/pro/sky/telegrambot/
│   │   ├── configuration/     # Конфигурационные классы
│   │   ├── job/              # Scheduled задачи
│   │   ├── listener/         # Обработчик updates от Telegram
│   │   ├── model/            # Сущности БД
│   │   ├── repository/       # JPA репозитории
│   │   ├── service/          # Бизнес-логика
│   │   └── TelegramBotApplication.java # Главный класс
│   └── resources/
│       ├── liquibase/        # Миграции БД
│       └── application.properties
```
## 🔧 Основные компоненты

    TelegramBotUpdatesListener - обработчик входящих сообщений

    NotificationTaskJob - планировщик отправки уведомлений

    ReminderParserImpl - парсер текстовых напоминаний

    NotificationsRepository - работа с данными напоминаний

## 📊 База данных

Проект использует Liquibase для управления миграциями. Основная таблица:
```sql
CREATE TABLE notification_task (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    datetime TIMESTAMP NOT NULL,
    is_sent BOOLEAN DEFAULT FALSE
);
```

## ⚙️ Настройка расписания

Частота проверки напоминаний настраивается в application.properties:
```properties
cron.interval.notification.task=0 0/1 * * * *  # каждую минуту
```

## 🚀 Развертывание

Приложение готово к развертыванию на:

    Локальном сервере

    Heroku

    AWS

    Docker контейнере

## 📝 Лицензия

Этот проект создан в учебных целях.

**Автор:** Галина
**Контакт:** galina.ramodina@gmail.com
