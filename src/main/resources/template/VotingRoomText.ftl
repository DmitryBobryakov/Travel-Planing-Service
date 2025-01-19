<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
<h1>Создание голосования</h1>

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
    // Массивы для хранения добавленных значений
    let trip_links = [];
    let trip_names = [];
    let friendsArr = [];

    // Добавить путешествие
    function addTrip() {
        const link = document.getElementById("website_trip").value;
        const name = document.getElementById("name_trip").value;
        if (link.trim() !== "" && name.trim() !== "") {
            trip_links.push(link);
            trip_names.push(name);
            // Показываем добавленное в контейнер
            const p1 = document.createElement("p");
            const p2 = document.createElement("p");
            p1.innerText = link;
            p2.innerText = name;
            document.getElementById("trip_link-container").appendChild(p1);
            document.getElementById("trip_name-container").appendChild(p2);

            // Очищаем поля
            document.getElementById("website_trip").value = "";
            document.getElementById("name_trip").value = "";
        }
    }

    // Добавить друга
    function addFriend() {
        const friendId = document.getElementById("friends").value;
        if (friendId.trim() !== "") {
            friendsArr.push(friendId);
            // Показываем
            const p = document.createElement("p");
            p.innerText = friendId;
            document.getElementById("friends-container").appendChild(p);

            // Очищаем поле
            document.getElementById("friends").value = "";
        }
    }

    // Отправка формы (JSON)
    function submitVotingForm() {
        const date_finish = document.getElementById("date_finish").value;

        // Сформируем объект для бэкенда
        const data = {
            date_finish: date_finish,
            friends_names: friendsArr,
            trip_links: trip_links,
            trip_name: trip_names,
        };

        // Отправляем POST на /AddData с JSON
        fetch("/AddData", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                // Если сервер делает redirect - перешли
                if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    return response.json();
                }
            })
            .then(respData => {
                if (respData) {
                    console.log("Ответ сервера:", respData);
                }
            })
            .catch(error => console.error("Ошибка:", error));
    }
</script>

</body>
</html>
