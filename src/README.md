# **RagnaROCK**

**RagnaROCK** — это приложение для ... Приложение предоставляет REST API для взаимодействия с ...

---

## Основные функции

1. ****:
    - .
    - .
    - .

2. ****:
    - .
    - .

3. **REST API**:
    - Интеграция с другими API через RESTful API.

---

## Технологии

- **Язык программирования**: Java 23
- **Фреймворк**: Spring Boot 3.x
- **База данных**: PostgreSQL
- **Сборка**: Maven

---

## Установка и запуск

### Требования

- Установленная Java 23 или выше.
- Установленный Maven/Graddle
- Установленная PostgreSQL.

---

### **Функционал**  
- Запуск локального REST API.  
- Работа с базой данных (**PostgreSQL**).  
- Добавление и получение объектов через REST API.  
- GET-запрос с Query Parameters для фильтрации товаров.  
- GET-запрос с Path Parameters для поиска товара по ID.  
- Настроенный **CheckStyle** для кодстайла.  

---

## **Задание**  
1. **Создать и запустить локально REST-сервис** на Java (Spring Boot + PostgreSQL + Maven/Gradle).  
2. **Настроить подключение к базе данных (PostgreSQL).**  
3. **Добавить GET эндпоинт с Query Parameters** для .  
4. **Добавить GET эндпоинт с Path Parameters** для .  
5. **Настроить CheckStyle** и исправить ошибки.  
6. **Формат ответа – JSON.**  

---

## **Установка и запуск**  

---

## **Доступные эндпоинты**

---

## **Настройка CheckStyle**

### **Maven**
Добавьте в `pom.xml`:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
    </configuration>
</plugin>
```
Запустите проверку:
```sh
mvn checkstyle:check
```

### **Gradle**
Добавьте в `build.gradle`:
```groovy
plugins {
    id 'checkstyle'
}

checkstyle {
    toolVersion = '10.12.0'
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
}

tasks.withType(Checkstyle).configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
```
Запустите проверку:
```sh
gradle check
```

---

## **Автор**
[dstpia](https://github.com/dstpia)

---

### SonarCloud
[Sonar](https://sonarcloud.io/project/overview?id=maks2134_Finance-tracker)
