<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
</head>
<body>
<#if error??>
    <p style="color:red">${error}</p>
</#if>
<form action="/register" method="post">
    <label for="phone">Номер телефона</label><br>
    <input type="text" id="phone" name="phone" required><br><br>
    <label for="username">Придумайте username</label><br>
    <input type="text" id="username" name="username" required><br><br>
    <label for="email">Почта</label><br>
    <input type="email" id="email" name="email" required><br><br>
    <label for="password">Придумайте пароль</label><br>
    <input type="password" id="password" name="password" required><br><br>
    <button type="submit">Продолжить</button>
</form>
</body>
</html>
