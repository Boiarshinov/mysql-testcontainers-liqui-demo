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
 -e MYSQL_DATABASE=demo-db \
 -e MYSQL_PASSWORD=pass \
 -e MYSQL_ROOT_PASSWORD=remote236 \
 --name mysql-demo mysql
```