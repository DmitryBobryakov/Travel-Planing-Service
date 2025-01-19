<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Pagе. Часть Ангелины</title>
    <script>
        function reloadPage() {
            window.location.reload(); // Перезагрузить текущую страницу
        }
    </script>
    <style>
        #trip-container {
            margin-top: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            width: 300px;
        }
    </style>
    <title>${title}</title>
    <style>
        body {
            text-align: center; /* Центрируем текст по горизонтали */
            margin: 0;
            padding: 0;
            height: 100vh; /* Высота страницы */
            display: flex;
            flex-direction: column;
            justify-content: center; /* Центрируем по вертикали */
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px; /* Отступ сверху для таблицы */
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    <script>
        // Функция для добавления текста в контейнер
        function addText() {
            // Получаем текст из label
            var labelText = document.getElementById("website_trip").value;
            // Создаем новый элемент для отображения текста
            var newTextElement = document.createElement("p");
            newTextElement.innerText = labelText;
            // Добавляем новый элемент на страницу
            document.getElementById("trip-container").appendChild(newTextElement);
        }
    </script>
    <style>
        /* Стили для модального окна */
        .choise_friends_window {
            display: none; /* Скрыто по умолчанию */
            position: fixed; /* Фиксированное положение */
            z-index: 1; /* На переднем плане */
            left: 0;
            top: 0;
            width: 100%; /* Полная ширина */
            height: 100%; /* Полная высота */
            overflow: auto; /* Прокрутка, если необходимо */
            background-color: rgba(0, 0, 0, 0.5); /* Полупрозрачный фон */
        }

        /* Стили для содержимого модального окна */
        .choise_friends_window-content {
            background-color: #fefefe;
            margin: 15% auto; /* Центрирование */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Ширина окна */
        }

        /* Кнопка закрытия */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
    <script>
        // Функция для открытия модального окна
        function openModal() {
            document.getElementById("choise_friends_window").style.display = "block";
        }

        // Функция для закрытия модального окна
        function closeModal() {
            document.getElementById("choise_friends_window").style.display = "none";
        }

        // Закрытие модального окна при клике вне его
        window.onclick = function(event) {
            var modal = document.getElementById("choise_friends_window");
            if (event.target === modal) {
                closeModal();
            }
        }
    </script>
</head>
<body>
<h1>${title}</h1>
<h2>
    <label for="date_finish">..................................................</label>
</h2>
<h2>
    <form action="/startVoting" method="GET">
        <button type="submit">Начать голосование</button>
    </form>
</h2>
<h2>
    <label for="date_finish">..................................................</label>
</h2>

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
        <th>Друзья</th>
        <th>Китай, 09.11-19.11</th>
        <th>Ааа, Ббб, Ссс</th>
    </tr>
    </tbody>
    <tbody>
    <tr>
        <th>Семья</th>
        <th>Италия, 6.01-15.02</th>
        <th>Ккк, Ддд, Иии, Ццц, Ссс</th>
    </tr>
    </tbody>
</table>
</body>
</html>