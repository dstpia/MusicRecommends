# **RagnaROCK**

**RagnaROCK** — это приложение на базе Spring Boot ориентированное на поиск песен.
Приложение предоставляет REST API для взаимодействия с песнями, их добавление в базу данных,
обновление информации о них, удаление из базы данных, поиск по конкретным параметрам.
В будущем подобный функционал будет возможен с авторами и их альбомами 

---

## Основные функции

1. **Функционал для пользователя**:

   - Добавление, удаление, редактирование информации о песне.
   - Поиск по определенным параметрам.

2. **Базовый функционал API**:

   - GET-запросы с возвращением данных в виде JSON.
   - POST-запрос с записью данных в виде JSON.
   - PUT-запрос с перезаписью имеющихся данных в виде JSON.
   - DELETE-запрос на удаление из базы данных.

3. **REST API**:

    - Интеграция с другими API через RESTful API.

---

## Технологии

- **Язык программирования**: Java 23
- **Фреймворк**: Spring Boot 3.x
- **База данных**: PostgreSQL
- **Сборка**: Maven

---

### Требования

- **Установленная Java 23 или выше**.
- **Установленный Maven/Gradle**.
- **Установленная PostgreSQL**.

---

### **Функционал** 

- Запуск локального REST API.  
- Работа с базой данных (**PostgreSQL**).  
- Добавление и получение объектов через REST API.  
- GET-запрос с Query Parameters для фильтрации песен.  
- GET-запрос с Path Parameters для поиска песен по ID.  
- Настроенный **CheckStyle** для кодстайла.  

---

## **Задание**  

1. **Создать и запустить локально REST-сервис** на Java (Spring Boot + PostgreSQL + Maven).  
2. **Настроить подключение к базе данных** (PostgreSQL).  
3. **Добавить GET эндпоинт с Query Parameters**.  
4. **Добавить GET эндпоинт с Path Parameters**.  
5. **Настроить CheckStyle** и исправить ошибки.
6. **Формат ответа – JSON**.  

---

## **Установка и запуск**

### **1. Клонирование репозитория**
```sh
git clone https://github.com/dstpia/lab-1.git
cd lab-1
```

### **2. Сборка и запуск приложения**

С использованием **Maven**:
```sh
mvn clean install
mvn spring-boot:run
```

С использованием **Gradle**:
```sh
gradle build
gradle bootRun
```

---

## **Доступные эндпоинты**

### **1. Получение автомобиля по ID**

Запрос:

```
GET /api/songs/{id}
```

Пример запроса:

```
GET /api/songs/1
```

Пример ответа:

```json
{
   "id": 1,
   "name": "Mоя оборона",
   "lyrics": "Пластмассовый мир победил..."
}
```

### **2. Получение списка песен с фильтрацией**

Запрос:

```
GET /api/songs?name=string1&lyrics=string2
```

Параметры:

* **name** _(опционально)_ – название песни.
* **lyrics** _(опционально)_ – первая строка текста песни.

Пример запроса:


```
GET /api/songs?name=Моя оборона&lyrics=Пластмассовый мир победил...
```

Пример ответа:

```json
[
  {
    "id": 1,
    "name": "Mоя оборона",
    "lyrics": "Пластмассовый мир победил..."
  }
]
```

---

### **Настройка CheckStyle**

#### **Maven**
Добавьте в pom.xml:

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

#### **Gradle**
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


## **Автор**

GitHub: [dstpia](https://github.com/dstpia)

SonarCloud: [Sonar](https://sonarcloud.io/project/overview?id=maks2134_Finance-tracker)