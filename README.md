# Сервис авторизации

Сервис авторизации на основе Keycloak для тестирования интеграции с ЕСИА

## Разработка

**Поднять контейнер:**
```shell
docker compose up -d --wait
```

**Перейти в настройки Keycloak:**

[Admin Console](http://localhost:8080)

Логин: *admin*
Пароль: *admin*

**Выбрать область gosex:**

1. Перейти на вкладку `Manage realms`
2. Выбрать область `gosex`

**Добавить тестовых пользователей:**

1. Перейти на вкладку `Users`
2. Нажать `Create new user`
3. Заполнить атрибуты пользователя, включить `Email verified`
4. Нажать Create
5. На странице нового пользователя перейти на вкладку `Credentials`
6. Нажать `Set password`
7. Заполнить форму, выключить `Temporary`

**Конфигурация клиента:**

- `realm`: `gosex`
- `clientId`: `gosex-app`
- `redirectUris`: `gosex-app://oauth/callback`
- `scopes`: `openid profile`
