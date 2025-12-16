# Друге завданння

За допомогою Spring Boot було розроблено REST API для управління розміщенням станків на промисловому виробництві.
Базовими сутностями предметної області є станок ЧПУ та цех, який може містити одразу кілька станків, при цьому станок
може належати лише до одного цеху в конкретний момент часу. CNCMachine являє собою модель станка ЧПУ:
```java
public class CNCMachine {
    public String maker;
    public String countryOfOrigin;
    public String model;
    public double length;
    public double width;
    public double height;
    public double weight;
    public double tableWorkArea;
    public int maxPowerConsumption;
    public int voltage;
    public String commandLanguage;
    public String operatingSystem;
}
```
Клас Workshop використовується для моделювання цеху:
```java
public class Workshop {
    private String code;
    private double height;
    private double width;
    private double length;
    private double floorLoadCapacity;
    private int voltage;
    private int availablePower;
    private List<CncMachine> cncMachines;
}
```
Оскільки основна задача програми полягає в автоматизації процесу розміщення об'єктів, цех та станок мають перелік 
обов'язкових для надання параметрів, серед яких фізичні розміри (для обох сутностей), вольтаж, доступна потужність та 
максимальне допустиме навантаження на підлогу у цеху, вольтаж, вага та максимальне можливе споживання у станка. Бізнес-
логіка здійснює валідацію наданих даних на можливість розміщення станка в цеху, зважаючи на характеристики обох об'єктів.
Окрім поступового додавання станків шляхом звернення до URL, існує можливість завантаження файлу JSON, який містить 
масив сутностей, що необхідно зберегти. Приклад файлу, доступного до завантаження:
```json
[ {
  "maker" : "Haas Automation",
  "countryOfOrigin" : "South Korea",
  "model" : "VF-2 # Integrex i-200",
  "length" : 3700.0,
  "width" : 3900.0,
  "height" : 3600.0,
  "weight" : 1740.0,
  "tableWorkArea" : 940.0,
  "maxPowerConsumption" : 13000,
  "voltage" : 400,
  "commandLanguage" : "Mazatrol",
  "operatingSystem" : "Fanuc 31i"
}]
```
У відповідь система сповіщає клієнта, надаючи кількість записаних та незаписаних файлів. Оскільки причини невдачі можуть мати
різну природу - помилка читання файлу, відсутність обов'язкових даних - то конкретизація цих обставин може розглядатися 
як варіант покращення UX від взаємодії із системою. Приклад вихідних даних:
```json
{
  "written": 5,
  "unwritten": 5
}
```
Крім того, система надає функціонал формування звіту у форматі CSV, що міститиме перелік станків, який буде 
відсортований у разі передачі відповідних параметрів у запиті. Приклад звіту:
```csv
"HEIGHT","LENGTH","MAXIMUM POWER CONSUMPTION","VOLTAGE","WEIGHT","WIDTH"
"3500.0","2300.0","2100","220","1360.0","3900.0"
"3000.0","3300.0","1900","220","1620.0","3300.0"
"2700.0","2300.0","2000","220","1180.0","2400.0"
```
## Використані технології
- Java 21
- Spring Boot 4.0.0
- PostgreSQL 17.0
- Liquibase
- JPA/Hibernate
- Maven

## База даних 
- Користувач: postgres
- Пароль: psql
- База даних: plant
- Порт: 5432