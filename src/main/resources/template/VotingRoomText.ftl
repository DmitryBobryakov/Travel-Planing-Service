<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пример сайта на FreeMarker</title>
    <script>
        function reloadPage() {
            window.location.reload(); // Перезагрузить текущую страницу
        }
    </script>
    <script>
        function AddTrip() {
            var labelText = document.getElementById("website_trip").value;
            var newTextElement = document.createElement("p");
            newTextElement.innerText = labelText;
            document.getElementById("trip-container").appendChild(newTextElement);
        }
    </script>
    <script>
        // Функция для добавления текста в контейнер
        function AddFriend() {
            // Получаем текст из label
            var labelText = document.getElementById("friends").value;
            var newTextElement = document.createElement("p");
            newTextElement.innerText = labelText;
            document.getElementById("friends-container").appendChild(newTextElement);
        }
    </script>

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
</h2>
<h1>
    <form action="/AddData" method="post">
        <button type="submit">Создать голосование</button>
    </form>
</h1>
</body>
</html>
