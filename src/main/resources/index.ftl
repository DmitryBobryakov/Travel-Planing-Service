<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Voting Room</title>
    <style>
        body {
            display: flex;
            flex-direction: column; /* Вертикальное выравнивание элементов */
            align-items: center; /* Центрирование заголовка */
            background-color: #f0f0f0; /* Цвет фона для всей страницы */
            margin: 0;
            padding: 20px;
        }
        h1 {
            margin-bottom: 20px; /* Отступ между заголовком и таблицей */
        }
        .table-container-participants {
            display: flex;
            justify-content: flex-end; /* Выравнивание таблицы влево */
            width: 100%; /* Занимает всю ширину родителя */
        }
        .table-container-variants {
            display: flex;
            justify-content: flex-start; /* Выравнивание таблицы вправо */
            width: 100%; /* Занимает всю ширину родителя */
        }
        table {
            background-color: #d3d3d3; /* Серый фон для таблицы */
            border-collapse: collapse;
            width: 500px; /* Ширина таблицы */
        }
        th, td {
            border: none; /* Граница ячеек */
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #a9a9a9; /* Темно-серый фон для заголовка */
            color: white; /* Цвет текста заголовка */
        }
        .progress {
            width: 100%;
            background-color: #f3f3f3;
            border-radius: 5px;
            overflow: hidden;
        }
        .progress-bar {
            height: 20px;
            background-color: #4caf50; /* Цвет прогресс-бара */
            text-align: center;
            line-height: 20px; /* Центрирование текста внутри бара */
            color: white;
        }
        /* Стили для попапа */
        .modal {
            display: none; /* Скрыть попап по умолчанию */
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }
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
        .vote-button {
            vertical-align: middle;
            background-color: red; /* Цвет фона кнопки */
            border: none; /* Убираем границу */
            border-radius: 50%; /* Делаем кнопку круглой */
            width: 50px;
            height: 50px;
            cursor: pointer; /* Указатель при наведении */
        }
        .invite-button {
            vertical-align: middle;
        }
        /* Показать попап при использовании :target */
        #modal:target {
            display: block;
        }
    </style>
</head>
<body>

<h1>Information About Voting Room</h1>

<div class="table-container-variants">
    <table>
        <#list variants as variants>
            <tr>
                <td>${variants.name}</td>
                <td colspan="2">
                    <div class="progress">
                        <div class="progress-bar" style="width: ${variants.interest}%;">${variants.count}</div>
                    </div>
                </td>
                <td>
                    <button class="vote-button" data-number-of-variant="${variants.number}"></button>
                </td>
            </tr>
        </#list>
    </table>
</div>

<div class="table-container-participants">
    <table>
        <tr>
            <th>Участники</th>
        </tr>
        <#list ids as person>
            <tr>
                <td>${person.name}</td>
                <td>${person.surname}</td>
            </tr>
        </#list>

    </table>
</div>

<!-- Кнопка "Добавить друга" -->
<a href="#modal" style="margin-top: 20px; text-decoration: none; padding: 10px; background-color: #e70824; color: white; border-radius: 5px;">Добавить друга</a>

<!-- Попап -->
<div id="modal" class="modal">
    <div class="modal-content">
        <a href="javascript:history.back()" class="close">&times;</a>
        <table>
            <tr>
                Ваши друзья(пока для owner)
            </tr>
            <#list friends as friends>
                <tr>
                    <td>${friends.name}</td>
                    <td>${friends.lastname}</td>
                    <td>
                        <button class="invite-button" data-friend-id="${friends.id}">Добавить</button>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
</div>

</body>
</html>

<script>
    document.querySelectorAll('.vote-button').forEach(button =>
    {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const variantNumber = this.getAttribute('data-number-of-variant');
            const data = {
                numberOfVariant: variantNumber
            }

            const jsonData = JSON.stringify(data);

            const currentUrl = window.location.href;
            const urlParts = currentUrl.split('/');
            const roomNumber = urlParts.slice(4).join('/');

            fetch("http://localhost:4567/api/voting-room/" + roomNumber, {
                method: 'POST',
                body: jsonData
            })
        })
    })
</script>

<script>
    <!-- Доработать -->
    document.querySelectorAll('.invite-button').forEach(button =>
    {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const friendId = this.getAttribute('data-friend-id');

            const currentUrl = window.location.href;
            const urlParts = currentUrl.split('/');
            const roomNumber = urlParts.slice(4).join('/');

            const path = "http://localhost:4567/api/voting-room/" + roomNumber + "/" + friendId;

            fetch(path, {
                method: 'POST'
            })
        })
    })
</script>
