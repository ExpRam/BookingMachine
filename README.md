<div align="center">
<h3>BookingMachine</h3>
  <p>
    Маркетплейс пассажирских перевозок
  </p>
</div>

# Сделано с использованием

## Бэкенд
* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
* ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
* ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
* ![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)

## Фронтенд
* ![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
* ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
* ![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)
* ![Vite](https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white)

## CI/CD
* ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![NPM](https://img.shields.io/badge/NPM-%23CB3837.svg?style=for-the-badge&logo=npm&logoColor=white)
* ![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)

# Разворачиваем проект через Docker

Вот как можно запустить проект локально (**На компьютере заранее должен быть установлен [Git](https://git-scm.com/), [Docker](https://www.docker.com/) и [Docker Compose](https://docs.docker.com/compose/install/)**):

> Docker и Docker Compose устанавливаются единым компонентом вместе с Docker Desktop

1. Скопируйте репозиторий, предварительно открыв терминал
    ```sh
    git clone https://github.com/ExpRam/BookingMachine.git
    ```

2. Перейдите в корневую директорию проекта:
    ```sh
    cd BookingMachine
    ```

3. При необходимости откройте файл `.env` и установите необходимые параметры (можно оставить стандартные)
> **ВАЖНО!** Если выдаёт ошибку, которая связана с занятыми портами, то обязательно их поменяйте в .env

4. Соберите образ через docker-compose
    ```sh
    docker compose build
    ```

5. Запустите контейнеры с образами через docker-compose
    ```sh
    docker compose up
    ```

> Для того, чтобы запустить контейнеры в фоновом режиме, используйте флаг -d
    
6. Перейдите на `http://127.0.0.1/` (*Если вы меняли порт Nginx в .env, то добавьте порт к айпи `127.0.0.1:порт`*)

# Как собрать компоненты по отдельности

## Backend

> На компьютере должнен быть установлен **Java JDK 21**
1. Переходим в директорию с backend
    ```sh
    cd bookingmachine-backend
    ```
2. Запускаем gardle-скрипт на сборку с пропуском тестов (*используем gradlew.bat для Windows*)
    ```sh
    gradlew build -x test
    ```
3. Результат билда будет находится в папке **build/libs/**
4. Запускаем через
    ```sh
    java -jar app.jar
    ```

## Frontend

> На компьютере должнен быть установлен **Node.js**
1. Переходим в директорию с frontend
    ```sh
    cd bookingmachine-frontend
    ```
2. Запускаем установку зависимостей
    ```sh
    npm install --frozen-lockfile
    ```
3. Запускаем билд проекта
    ```sh
    npm run build
    ```
4. Результат билда будет в папке **dist**
