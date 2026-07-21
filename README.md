# create an empty database:

```shell
$ podman run \
    --name container-tutorial-60-mysql \
    --mount source=volume-tutorial-60-mysql,destination=/var/lib/mysql \
    --env MYSQL_HOST=localhost \
    --env MYSQL_PORT=3306 \
    --env MYSQL_RANDOM_ROOT_PASSWORD=yes \
    --env MYSQL_USER=tutorial-60-user \
    --env MYSQL_PASSWORD=tutorial-60-password \
    --env MYSQL_DATABASE=tutorial-60-database \
    --publish 3306:3306 \
    mysql:8.4.10 \
    --character-set-server=utf8mb4 \
    --collation-server=utf8mb4_bin \
    --mysql-native-password=ON


    --default-authentication-plugin=mysql_native_password \
    --skip-character-set-client-handshake
```

```shell
$ podman container exec \
    -it container-tutorial-60-mysql \
    /bin/bash

bash-5.1# mysql \
    -u tutorial-60-user \
    -p \
    tutorial-60-database

mysql> show tables;
Empty set (0.00 sec)
```



# run the application from the command line with ... Maven

```shell
$ ./mvnw spring-boot:run

# ...

# Running `show tables;` in the `mysql` shell now will return:
```
```
mysql> show tables;
+--------------------------------+
| Tables_in_tutorial-60-database |
+--------------------------------+
| user                           |
| user_seq                       |
+--------------------------------+
2 rows in set (0.00 sec)
```



# build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run that

build the JAR file with
```shell
$ ./mvnw clean package
```

run the JAR file, as follows:
```shell
$ java \
    -jar target/tutorial-about-java-60-0.0.1-SNAPSHOT.jar
```



# Use another terminal window to issue these HTTP requests:

```shell
$ curl \
    http://localhost:8080/demo/all
[]

$ curl \
    http://localhost:8080/demo/add \
    -d name=John \
    -d email=someemail@someemailprovider.com
Saved

$ curl \
    http://localhost:8080/demo/all \
    | json_pp
# ...
[
   {
      "email" : "someemail@someemailprovider.com",
      "id" : 1,
      "name" : "John"
   }
]
```



# cleanup

```shell
$ podman container rm -f container-tutorial-60-mysql \
    && \
    podman volume prune
```