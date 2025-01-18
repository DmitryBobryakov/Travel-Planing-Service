<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
</head>
<body>
<#if error??>
    <p style="color:red">${error}</p>
</#if>
<form action="/login" method="post">
    <label for="phone">Номер телефона</label><br>
    <input type="text" id="phone" name="phone" required><br><br>
    <label for="password">Пароль</label><br>
    <input type="password" id="password" name="password" required><br><br>
    <button type="submit">Войти</button>
    <a href="/register">Зарегистрироваться</a>
</form>
</body>
</html>
