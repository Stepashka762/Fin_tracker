# Анализ транзакций — консольное приложение для учёта финансов

## Описание

Консольное приложение на чистой Java Core для управления личными финансами.  
Позволяет вести счета, добавлять транзакции (доходы/расходы), искать и группировать счета.

**Проект демонстрирует:**
- Работу с сериализацией (хранение данных в файлах)
- Использование дженериков для переиспользования кода
- Stream API для фильтрации и группировки
- Абстрактные классы и наследование
- Паттерн "Шаблонный метод"

## Технологический стек

- **Java 16** (Core)
- **Сериализация** (ObjectInputStream / ObjectOutputStream)
- **Stream API, Collections Framework**
- **Gradle** — сборка проекта
- **Lombok** — сокращение бойлерплейт-кода
- **SLF4J + Logback** — логирование

## Архитектура

Проект построен на **многослойной архитектуре**:
controller/ # Контроллеры меню (AbstractMenuController + наследники)
service/ # Бизнес-логика (TransactionService)
repository/ # Работа с данными (AccountRepository, TransactionRepository)
model/ # Сущности (Account, Transaction, Analytic)
exception/ # Кастомные исключения

text

### Ключевая особенность — абстрактный контроллер с дженериками

```java
public abstract class AbstractMenuController<T extends Enum<T>> {
    public final void runMenu() { 
    }
    protected abstract void processSelectedOption(T option);
    protected abstract boolean isExitOption(T option);
}
Все конкретные контроллеры (MainMenuController, SearchMenuController, TransactionMenuController) наследуются от него и переопределяют только обработку пунктов меню. Логика вывода меню и чтения ввода — общая для всех.

##Функциональность
-Управление счетами
-Просмотр всех счетов
-Добавление нового счёта (название, начальный баланс, валюта)
-Удаление счёта

##Управление транзакциями
-Добавление дохода/расхода
-Просмотр всех транзакций
-Просмотр транзакций по счёту

##Поиск счетов
-Поиск по имени (частичное совпадение, без учёта регистра)
Поиск по валюте (RUB/USD/EUR)
-Поиск по диапазону баланса

##Группировка счетов
-Группировка по валюте
-Группировка по диапазону баланса (менее 1000, 1000-5000, 5000-10000, более 10000)

##Хранение данных
Данные сохраняются через Java-сериализацию:
-accounts.dat — счета
-transactions.dat — транзакции
При запуске приложения данные автоматически загружаются из файлов. При добавлении/изменении — сохраняются.

##Запуск проекта
Требования:
-Java 16 или выше
-Gradle (или использовать wrapper)

##Команды
-bash
# Клонирование репозитория
git clone https://github.com/olgabazhova/finance-tracker.git
cd finance-tracker

# Сборка проекта
./gradlew build

# Запуск приложения
./gradlew run

# Или через JAR
java -jar build/libs/fin-tracker.jar
Пример работы
text
=== Главное меню ===
1. Просмотр всех счетов
2. Добавить новый счет
3. Управление транзакциями
4. Поиск счетов
5. Группировка счетов
6. Аналитика и отчеты
7. Выход
Выберите вариант: 1

=== Список всех счетов ===
ID  | Название счета         | Баланс       | Валюта
--------------------------------------------------
1   | Накопления             |     15000.00 | RUB
2   | Ежедневные расходы     |       500.00 | RUB
3   | Долларовый счёт        |      1000.00 | USD
Структура данных
Account
java
public class Account implements Serializable {
    private Long id;
    private String name;
    private double balance;
    private String currency;  
}
Transaction
java
public class Transaction implements Serializable {
    private Long id;
    private Long accountId;
    private LocalDate date;
    private double amount;      
    private String category;    
    private String description;
}


