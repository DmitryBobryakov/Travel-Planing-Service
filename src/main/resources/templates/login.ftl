<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>

    <!-- Стили CSS -->
    <style>
        /* 1) Фон всего документа */
        body {
            background-color: #FFFFFF;
            margin: 0;
            padding: 0;

            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;

            /* Чтобы позиционировать красный круг */
            position: relative;
            font-family: Arial, sans-serif;
        }

        /* 2) Контейнер для формы */
        .form-container {
            background-color: #D9D9D9;
            border-radius: 15px;
            width: 450px;
            margin: 90px auto;
            padding: 20px;
        }

        /* 3) Блоки для полей (номер телефона/пароль) */
        .field {
            margin-bottom: 20px;
        }

        /* 4) Сообщение об ошибке */
        .error {
            color: red;
            margin-bottom: 15px;
            font-weight: bold;
        }

        /* 5) Оформление меток (label) */
        label {
            color: rgba(0, 0, 0, 0.45);
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        /* 6) Поля ввода */
        input[type="text"],
        input[type="password"] {
            width: 100%;
            border: 2px solid #000000;
            padding: 8px;
            box-sizing: border-box;
        }

        /* 7) Кнопка "Войти" */
        button {
            margin-left: 160px;
            background-color: #FF0032;
            color: #FFFFFF;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            font-size: 15px;
            margin-right: 10px;
        }

        /* При наведении на кнопку – лёгкое затемнение */
        button:hover {
            opacity: 0.9;
        }

        /* 8) Ссылка-кнопка "Зарегистрироваться" */
        .btn-link {
            background-color: #FF0032;
            color: #FFFFFF;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
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

        /* Наведение на ссылку-кнопку */
        .btn-link:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<!-- Красный круг в верхнем левом углу -->
<div class="corner-circle">
    Сервис<br>
    Совместного<br>
    Путешествия
</div>

<div class="form-container">
    <!-- Если есть сообщение об ошибке, отображаем в виде красного текста -->
    <#if error??>
        <p class="error">${error}</p>
    </#if>

    <form action="/login" method="post">
        <div class="field">
            <label for="phone">Номер телефона</label>
            <input type="text" id="phone" name="phone" required>
        </div>

        <div class="field">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">Войти</button>
        <a href="/register" class="btn-link">Зарегистрироваться</a>
    </form>
</div>

</body>
</html>
