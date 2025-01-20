<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title> <!-- Только один тег <title> -->
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: #f9f9f9;
        }

        /* Стили для таблицы */
        table {
            border-collapse: collapse;
            width: 80%;
            margin-top: 20px;
            background-color: #fff;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        /* Стили для кнопок */
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 24px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 2px;
            cursor: pointer;
            border-radius: 4px;
        }

        /* Стили для модального окна */
        .modal {
            display: none; /* Скрыто по умолчанию */
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5); /* Полупрозрачный фон */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto; /* Отцентрировано по вертикали и горизонтали */
            padding: 20px;
            border: 1px solid #888;
            width: 50%; /* Ширина модального окна */
            border-radius: 8px;
            position: relative;
        }

        /* Кнопка закрытия */
        .close {
            color: #aaa;
            position: absolute;
            top: 10px;
            right: 20px;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
        }

        /* Контейнер с фоновым изображением */
        .hero-section {
            width: 100%;
            background-image: url("/images/image.png"); /* Путь к изображению */
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            padding: 60px 20px;
            color: white; /* Цвет текста поверх изображения */
            text-shadow: 2px 2px 4px #000;
            box-sizing: border-box;
        }

        .hero-section h1 {
            margin: 0;
            font-size: 48px;
        }

        /* Отступы между секциями */
        .section {
            width: 80%;
            max-width: 800px;
            margin: 20px 0;
            text-align: left;
        }

        /* Стили для списка друзей внутри модального окна */
        .friend-list {
            list-style-type: none;
            padding: 0;
        }

        .friend-list li {
            padding: 8px 0;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>
<body>

    <!-- Контейнер с фоновым изображением -->
    <div class="hero-section">
        <h1>${title}</h1>
        <div class="section">
            <!-- Добавьте любые дополнительные элементы, если необходимо -->
            <form action="/startVoting" method="GET">
                <button type="submit" class="button">Начать голосование</button>
            </form>
        </div>
    </div>

    <!-- Основная секция: Текущие комнаты -->
    <div class="section">
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
                    <td>Друзья</td>
                    <td>Китай, 09.11-19.11</td>
                    <td>Ааа, Ббб, Ссс</td>
                </tr>
                <tr>
                    <td>Семья</td>
                    <td>Италия, 6.01-15.02</td>
                    <td>Ккк, Ддд, Иии, Ццц, Ссс</td>
                </tr>
                <!-- Добавьте дополнительные комнаты при необходимости -->
            </tbody>
        </table>
    </div>

    <!-- Кнопка для открытия модального окна со списком друзей -->
    <div class="section">
        <button class="button" onclick="openModal()">Показать список друзей</button>
    </div>

    <!-- Модальное окно со списком друзей -->
    <div id="friendsModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Список ваших друзей</h2>
            <ul class="friend-list">
                <#if friends?has_content>
                    <#list friends as friend>
                        <li>${friend}</li>
                    </#list>
                <#else>
                    <li>У вас пока нет друзей.</li>
                </#if>
            </ul>
        </div>
    </div>

    <script>
        // Функции для открытия и закрытия модального окна
        function openModal() {
            document.getElementById("friendsModal").style.display = "block";
        }

        function closeModal() {
            document.getElementById("friendsModal").style.display = "none";
        }

        // Закрытие модального окна при клике вне его содержимого
        window.onclick = function(event) {
            var modal = document.getElementById("friendsModal");
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>
