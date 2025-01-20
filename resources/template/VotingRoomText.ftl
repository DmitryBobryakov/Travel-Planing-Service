<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <style>
        /* Простейшие стили для модального окна */
        .modal {
            display: none; /* Изначально скрыто */
            position: fixed; /* Окно фиксированное */
            z-index: 9999; /* Поверх всего */
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto; /* Скролл, если контент выходит за экран */
            background-color: rgba(0,0,0,0.4); /* Полупрозрачный фон */
        }
        .modal-content {
            background-color: #fefefe;
            margin: 10% auto; /* Отцентровать по горизонтали */
            padding: 20px;
            width: 50%; /* Примерная ширина окна */
            border-radius: 8px;
        }
        .closeBtn {
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        .closeBtn:hover {
            color: red;
        }
    </style>
</head>
<body>
<h1>${title}</h1>

<h2>
    <label for="date_start">Выберите дату начала голосования</label>
    <input type="text" id="date_start" name="date_start" required>
</h2>
<h2>
    <label for="date_finish">Выберите дату конца голосования</label>
    <input type="text" id="date_finish" name="date_finish" required>
</h2>
<h2>
    <div id="trip-container"></div>
    <label for="website_trip">Добавьте ссылку на путешествие</label>
    <input type="text" id="website_trip" name="website_trip" required>
    <label for="name_trip"> и название</label>
    <input type="text" id="name_trip" name="name_trip" required>
    <button onclick="AddTrip()">Добавить путешествие</button>
</h2>
<h2>
    <div id="friends-container"></div>
    <label for="friends">Добавьте id друга</label>
    <input type="text" id="friends" name="friends" required>
    <button onclick="AddFriend()">Добавить друга</button>
    <!-- Новая кнопка, которая открывает всплывающее окно -->
    <button type="button" onclick="showFriendsModal()">Показать друзей</button>
</h2>

<h1>
    <form action="/AddData" method="post">
        <button type="submit">Создать голосование</button>
    </form>
</h1>

<!-- Всплывающее окно (модальное) со списком друзей -->
<div id="friendsModal" class="modal">
    <div class="modal-content">
        <span class="closeBtn" onclick="closeFriendsModal()">&times;</span>
        <h2>Список ваших друзей:</h2>
        <ul id="friendsList"></ul>
    </div>
</div>

<script>
    // Массив друзей с сервера в JSON-формате
    // FreeMarker: friends?json_string
    var allFriends = ${friends?json_string};

    // Функция для добавления путешествия
    function AddTrip() {
        var labelText = document.getElementById("website_trip").value;
        var newTextElement = document.createElement("p");
        newTextElement.innerText = labelText;
        document.getElementById("trip-container").appendChild(newTextElement);

        // Очистка поля
        document.getElementById("website_trip").value = "";
        document.getElementById("name_trip").value = "";
    }

    // Функция для добавления друга (вручную введённого)
    function AddFriend() {
        var labelText = document.getElementById("friends").value;
        var newTextElement = document.createElement("p");
        newTextElement.innerText = labelText;
        document.getElementById("friends-container").appendChild(newTextElement);

        // Очистка поля
        document.getElementById("friends").value = "";
    }

    // === ЛОГИКА МОДАЛЬНОГО ОКНА (СПИСОК ДРУЗЕЙ) ===

    // Открыть окно и показать список друзей
    function showFriendsModal() {
        // 1) Очищаем список, если он уже чем-то заполнен
        var ul = document.getElementById("friendsList");
        ul.innerHTML = "";

        // 2) Добавляем каждого друга в <li>
        allFriends.forEach(function(friend) {
            var li = document.createElement("li");
            li.innerText = friend;
            ul.appendChild(li);
        });

        // 3) Показываем само окно
        var modal = document.getElementById("friendsModal");
        modal.style.display = "block";
    }

    // Закрыть всплывающее окно
    function closeFriendsModal() {
        var modal = document.getElementById("friendsModal");
        modal.style.display = "none";
    }

    // Закрываем окно, если кликнули по «фону» (за пределами .modal-content)
    window.onclick = function(event) {
        var modal = document.getElementById("friendsModal");
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>
</body>
</html>
