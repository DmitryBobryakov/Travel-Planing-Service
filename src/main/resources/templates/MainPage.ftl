<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page. Часть Ангелины</title>
    <style>
        /* Основные стили */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #FFFFFF; /* Белый фон */
            color: #333333; /* Цвет текста */
        }

        .background-image {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 300px; /* Высота блока */
            margin: 20px auto;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* Тень */
            max-width: 90%; /* Ограничение ширины */
        }

        .background-image img {
            width: 100%; /* Заполняет всю ширину контейнера */
            height: auto; /* Сохраняет пропорции */
            object-fit: cover; /* Подгоняет изображение */
        }

        header {
            background-color: rgba(255, 255, 255, 0.8); /* Белый фон с прозрачностью */
            text-align: center;
            padding: 20px;
            border-radius: 20px;
            margin: 20px auto;
            max-width: 90%; /* Ограничение ширины */
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* Тень */
        }

        header h1 {
            color: #333333;
            font-size: 2.5em;
            margin: 0;
        }

        header .start-vote-btn {
            background-color: #FF0032;
            color: #FFFFFF;
            font-weight: bold;
            font-size: 1.2em;
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }

        header .start-vote-btn:hover {
            background-color: #CC0029;
        }

        main {
            padding: 20px;
            text-align: center;
        }

        h2 {
            font-size: 1.8em;
            margin-bottom: 20px;
        }

        table {
            width: 90%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: rgba(255, 255, 255, 0.9); /* Прозрачный фон таблицы */
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 15px;
            text-align: center;
            border: 1px solid #D9D9D9;
        }

        th {
            background-color: #F2F2F2;
            font-weight: bold;
        }

        td {
            color: #555555;
        }

        .room-name {
            font-weight: bold;
            color: #333333;
        }

        .footer-label {
            display: block;
            margin-top: 40px;
            color: #999999;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<!-- Блок с изображением -->
<div class="background-image">
    <img src="/images/image.jpg" alt="Cannot load static image"
         style="max-height: 50vh;"
    />
</div>

<header>
    <h1>Сервис по планированию совместного путешествия</h1>
    <button class="start-vote-btn">Начать голосование</button>
</header>

<main>
    <h2>Текущие комнаты</h2>
    <table>
        <thead>
        <tr>
            <th>Название комнаты</th>
            <th>Страна и даты поездки</th>
            <th>Участники</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="room-name">Друзья</td>
            <td>Стамбул, 09.11-19.11</td>
            <td>Бахар, Тимур, Эврен</td>
        </tr>
        <tr>
            <td class="room-name">Семья</td>
            <td>Италия, 6.01-15.02</td>
            <td>Карен, Дима, Илья, Сева, Соня</td>
        </tr>
        </tbody>
    </table>
</main>
</body>
</html>
