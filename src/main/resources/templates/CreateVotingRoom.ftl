<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <style>
        /* Общие стили */
        body {
            background-color: #FFFFFF;
            margin: 0;
            padding: 20px;
            position: relative;
            font-family: Arial, sans-serif;
        }

        h1 {
            text-align: center;
            color: #6b6868;
            margin-bottom: 30px;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #F9F9F9;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-bottom: 20px;
            font-size: 1.2em;
            color: #6b6868;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #6b6868;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #D9D9D9;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 1em;
        }

        /* Блоки добавления с закруглёнными рамочками */
        #trip_link-container,
        #trip_name-container,
        #friends-container {
            border: 1px solid #D9D9D9;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #FAFAFA;
        }

        /* Кнопки */
        button {
            display: block;
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-family: Arial, sans-serif;
            font-size: 1em;
            transition: background-color 0.3s ease;
        }

        /* Кнопка Добавить путешествие */
        button[onclick="addTrip()"] {
            background-color: #FF0032;
            color: #FFFFFF;
        }

        button[onclick="addTrip()"]:hover {
            background-color: #0056b3;
        }

        /* Кнопка Добавить друга */
        button[onclick="addFriend()"] {
            background-color: #24ed52;
            color: #FFFFFF;
            font-size: 1.1em;
            font-weight: bold;
            position: relative;
        }

        button[onclick="addFriend()"]::before {
            content: "+";
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 1.5em;
        }

        button[onclick="addFriend()"] {
            padding-left: 40px;
        }

        button[onclick="addFriend()"]:hover {
            background-color: #218838;
        }

        /* Кнопка Создать голосование */
        button[onclick="submitVotingForm()"] {
            background-color: #FF0032;
            color: #FFFFFF;
            font-size: 1.2em;
            font-weight: bold;
            padding: 15px;
        }

        button[onclick="submitVotingForm()"]:hover {
            background-color: #cc0029;
        }

        /* 9) Красный круг в левом верхнем углу */
        .corner-circle {
            position: absolute;
            top: 10px;
            left: 15px;
            width: 70px;
            height: 70px;
            background-color: #FF0000;
            border-radius: 50%;

            color: #FFFFFF;
            font-weight: bold;
            font-size: 9px;

            /* Flexbox для выравнивания текста внутри круга */
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: left;

            padding-left: 2px;
        }

    </style>
</head>
<body>
<h1>Создание голосования</h1>

<!-- Красный круг в верхнем левом углу -->
<div class="corner-circle">
    Сервис<br>
    Совместного<br>
    Путешествия
</div>

<form id="votingForm">
    <h2>
        <label for="date_finish">Дата конца голосования</label>
        <input type="text" id="date_finish" name="date_finish" required>
    </h2>

    <!-- Блок добавления путешествий -->
    <h2>
        <div id="trip_link-container" style="display:none;"></div>
        <div id="trip_name-container"></div>
        <label for="website_trip">Ссылка на путешествие:</label>
        <input type="text" id="website_trip" name="website_trip">
        <label for="name_trip">Название:</label>
        <input type="text" id="name_trip" name="name_trip">
        <button type="button" onclick="addTrip()">Добавить путешествие</button>
    </h2>

    <!-- Блок добавления друзей -->
    <h2>
        <div id="friends-container"></div>
        <label for="friends">ID друга:</label>
        <input type="text" id="friends" name="friends">
        <button type="button" onclick="addFriend()">Добавить друга</button>
    </h2>

    <!-- Кнопка отправки данных -->
    <h2>
        <button type="button" onclick="submitVotingForm()">Создать голосование</button>
    </h2>
</form>

<script>
    // Ваш JavaScript код здесь
</script>
</body>
</html>
