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
            position: relative;
            font-family: Arial, sans-serif; /* Шрифт для всего текста */
        }

        h1 {
            margin-bottom: 20px; /* Отступ между заголовком и таблицей */
            color: #333; /* Цвет заголовка */
            text-align: center; /* Центрирование заголовка */
        }
        .table-container-participants,
        .table-container-variants {
            display: flex;
            justify-content: center; /* Центрирование таблиц */
            width: 100%; /* Занимает всю ширину родителя */
            margin-bottom: 20px; /* Отступ между таблицами */
        }
        table {
            background-color: #ffffff; /* Белый фон для таблицы */
            border-collapse: collapse;
            width: 500px; /* Ширина таблицы */
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Тень для таблицы */
            border-radius: 8px; /* Скругление углов таблицы */
            overflow: hidden; /* Скрытие переполнения из-за скругления */
        }

        th, td {
            border: none; /* Граница ячеек */
            padding: 12px; /* Увеличенный отступ */
            text-align: left;
        }

        th {
            background-color: #24ed52; /* Цвет фона заголовка */
            color: white; /* Цвет текста заголовка */
        }

        tr:nth-child(even) {
            background-color: #f9f9f9; /* Чередующиеся цвета строк */
        }

        .progress {
            width: 100%;
            background-color: #f3f3f3;
            border-radius: 5px;
            overflow: hidden;
        }


        .corner-circle {    position: absolute;
            top: 10px;    left: 15px;
            width: 70px;    height: 70px;
            background-color: #FF0000;    border-radius: 50%;
            color: #FFFFFF;
            font-weight: bold;    font-size: 9px;
            /* Flexbox для выравнивания текста внутри круга */
            display: flex;    flex-direction: column;
            justify-content: center;    align-items: center;
            text-align: left;
            padding-left: 2px;}

        .progress-bar {
            height: 20px;
            background-color: #24ed52; /* Цвет прогресс-бара */
            display: flex; /* Используем flexbox для центрирования текста */
            justify-content: center; /* Центрирование по горизонтали */
            align-items: center; /* Центрирование по вертикали */
            color: white; /* Цвет текста в прогресс-баре */
            padding: 0 10px; /* Отступы по бокам для лучшего отображения текста */
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
            margin: 10% auto; /* Уменьшено до 10% для центрирования */
            padding: 20px;
            border-radius: 8px; /* Скругление углов попапа */
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* Тень для попапа */
            width: 40%; /* Уменьшено до 40% для уменьшения размера */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .table-container {
            display: flex; /* Используем Flexbox для выравнивания таблиц в ряд */
            justify-content: center; /* Центрирование таблиц */
            width: 100%;
            margin-bottom: 20px; /* Отступ между таблицами */
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .vote-button {
            vertical-align: middle;
            background-color: #FF0032; /* Цвет фона кнопки */
            border: none; /* Убираем границу */
            border-radius: 50%; /* Делаем кнопку круглой */
            width: 50px;
            height: 50px;
            cursor: pointer; /* Указатель при наведении */
            transition: background-color 0.3s ease; /* Плавный переход цвета */
        }

        .vote-button:hover {
            background-color: #c70039; /* Цвет кнопки при наведении */
        }

        .invite-button {
            background-color: #FF0032; /* Цвет фона кнопки */
            border: none; /* Убираем границу */
            cursor: pointer; /* Указатель при наведении */
            color: white;
        }

        /* Показать попап при использовании :target */
        #modal:target {
            display: block;
        }
    </style>
</head>
<body>

<h1>Отдых с друзьями</h1>

<div class="corner-circle">
    Сервис<br>
    Совместного<br>
    Путешествия
</div>

<div class="table-container">
    <div class="table-container-variants">
        <table>
            <#list variants as variants>
                <tr>
                    <td>${variants.name}</td>
                    <td colspan="2">
                        <div class="progress">
                            <div class="progress-bar" style="width: ${variants.interest}%;">${variants.interest}%</div>
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
</div>

<!-- Кнопка "Добавить друга" -->
<a href="#modal" style="margin-top: 20px; text-decoration: none; padding: 10px; background-color: #FF0032; color: white; border-radius: 5px;">Добавить друга</a>

<!-- Попап -->
<div id="modal" class="modal">
    <div class="modal-content">
        <a href="javascript:history.back()" class="close">&times;</a>
        <table>
            <tr>
                Ваши друзья
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
