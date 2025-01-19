<!DOCTYPE html>
<html lang="ru" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Всплывающее Окно</title>
    <style>
        /* Стили для модального окна */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
        }
        .FriendsWindow-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
    </style>

    <script>
        // Получаем элементы модального окна
        var modal = document.getElementById("FriendsWindow");
        var btn = document.getElementById("AddFriends");
        var span = document.getElementsByClassName("close")[0];

        // Когда кнопка нажата, открываем модальное окно
        btn.onclick = function() {
            modal.style.display = "block";
        }

        // Когда пользователь нажимает на <span> (x), закрываем модальное окно
        span.onclick = function() {
            modal.style.display = "none";
        }

        // Закрываем модальное окно, если пользователь нажимает за его пределами
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    </script>

    <script>

        function AddData() {
            fetch('/AddData', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    date_start: document.getElementById("date_start").value,
                    date_finish: document.getElementById("date_finish").value,
                    friends_names: document.getElementById("friends-container").textContent,
                    trip_variants: document.getElementById("trip-container").textContent
                })
            })
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
    <label id="trip-container"></label>
    <label for="website_trip">Добавьте ссылку на путешествие</label>
    <input type="text" id="website_trip" name="website_trip" required>
    <label for="name_trip"> и название</label>
    <input type="text" id="name_trip" name="name_trip" required>
    <button onclick="AddTrip()">Добавить путешествие</button>
</h2>
<h2>
    <button id="AddFriends">Добавить друзей</button>
    <div id="FriendsWindow" class="modal">
        <div class="FriendsWindow-content">
            <span class="close">&times;</span>
            <h2>Выберите друзей</h2>
            <#--        <div>-->
            <#--            <#list friends as friend>-->
            <#--                <chekbox>${friend}</chekbox>-->
            <#--            </#list>-->
            <#--        </div>-->
        </div>
    </div>

</h2>
<h1>
    <button onclick="AddData()">Создать голосование</button>
</h1>
</body>
</html>
