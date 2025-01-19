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
        /* --- Ваши стили для контейнера с путешествиями --- */
        #trip-container {
            margin-top: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            width: 300px;
        }

        body {
            text-align: center; /* Центрируем текст по горизонтали */
            margin: 0;
            padding: 0;
            min-height: 100vh; /* Чтобы страница тянулась по высоте */
            display: flex;
            flex-direction: column;
            /* Не обязательно центрировать всё по вертикали, иначе фон может стать слишком маленьким */
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

        /* --- Стили для модального окна выбора друзей --- */
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
        .choise_friends_window-content {
            background-color: #fefefe;
            margin: 15% auto; /* Центрирование по горизонтали */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Ширина окна */
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
        }

        /* --- Главный блок с фоновым изображением --- */
        .hero-section {
            /* Путь к картинке: подстройте, если нужно, в зависимости от того, 
               как у вас отдаются статические файлы. */
            background-image: url("/images/image.png");
            background-size: cover;         /* Масштабируем под блок */
            background-position: center;    /* Центрируем картинку */
            background-repeat: no-repeat;   /* Без повторов */
            padding: 60px 20px;            /* Отступы внутри блока */
            margin-bottom: 20px;           /* Небольшой отступ снизу */
        }
        .hero-section h1 {
            color: #fff; /* Цвет текста поверх картинки (при желании) */
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

<!-- Контейнер с фоновым изображением: от заголовка до кнопки "Начать голосование" -->
<div class="hero-section">
    <h1>${title}</h1>

    <h2>
        <!-- Просто пример контента: можно убрать или переименовать -->
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
</div>

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

<!-- Пример: модальное окно (если нужно) -->
<div id="choise_friends_window" class="choise_friends_window">
    <div class="choise_friends_window-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Здесь, например, список друзей</h2>
        <!-- Ваш контент для выбора друзей -->
    </div>
</div>

</body>
</html>
