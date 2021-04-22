# MySQL - Testcontainers - Liquibase Demo

In this project I'm trying to run mysql testcontainer with liquibase migrations
in test environment.

## App preparation

You should have docker (I'm using v.20.10.5 for this demo).  
Run in bash (convert it to powershell if you're on windows):
```shell
 docker container run \
 -d \
 -p 3306:3306 \
 -e MYSQL_USER=user \
 -e MYSQL_DATABASE=first_schema \
 -e MYSQL_PASSWORD=pass \
 -e MYSQL_ROOT_PASSWORD=remote236 \
 --name mysql-demo mysql
```

# H2 vs Testcontainers

Преимущества h2:
- Легкость настройки
- Скорость исполнения тестов

Недостатки h2:
- Ключевые слова, свойственные СУБД, могут отсутствовать в h2 (см. `virtual`)
- Некоторый синтаксис не поддерживается h2 (см. `truncate`)
- h2 медленно подтягивает фичи различных СУБД. Тип JSON, который появился в PostgreSQL в 2012 г., был поддержан в h2 только спустя 7 лет.  
- h2 не развивается. Последний релиз в 2019 г.

[Статья про недостатки h2](https://phauer.com/2017/dont-use-in-memory-databases-tests-h2/)