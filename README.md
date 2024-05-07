## Запуск Keycloak

Для запуска Keycloak:

```shell
   cd docker
   docker compose up -d
```

Keycloak будет доступен по адресу http://localhost:8070.
URL: http://localhost:8070
Login: admin
Password: admin

После входа в административную панель Keycloak необходимо создать новый realm root:
готовый файл тут ./docker/realm.json достаточно импортировать его в админку
