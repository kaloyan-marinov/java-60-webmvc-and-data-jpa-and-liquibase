# configure

```shell
$ cp \
    .env.template \
    .env

# Edit the newly-created file according to the instructions therein.
```



# create an empty database:

```shell
$ podman run \
    --name container-tutorial-60-mysql \
    --mount source=volume-tutorial-60-mysql,destination=/var/lib/mysql \
    --env-file .env \
    --publish 3306:3306 \
    mysql:8.4.10 \
    --character-set-server=utf8mb4 \
    --collation-server=utf8mb4_bin \
    --mysql-native-password=ON
```

```shell
$ podman container exec \
    -it container-tutorial-60-mysql \
    /bin/bash

bash-5.1# mysql \
    -u tutorial-60-user \
    -p \
    tutorial-60-database
Enter password: <enter-the-requested-password>

mysql> show tables;
Empty set (0.00 sec)
```



# run the application from the command line

using one of the following:

option 1:
with Maven

```shell
# Since the following line is wrapped in parentheses,
# it runs not in your current shell, but in a child process - in a sub-shell.
# After that sub-shell exits, no environment variables will remain set in the parent shell process.
#
# The first statement = Turn on the shell's "allexport" (auto-export) option.
$ (
    set -a ;

    source ./.env ;

    ./mvnw spring-boot:run ;
)

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



option 2:
build a single executable JAR file that contains all the necessary dependencies, classes, and resources
and
run that

build the JAR file with
```shell
$ ./mvnw clean package
```

run the JAR file, as follows:
```shell
$ (
    set -a ;

    source ./.env ;

    java \
        -jar target/tutorial-about-java-60-0.0.1-SNAPSHOT.jar ;
)
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

in the terminal where the application is running:

- press `Ctrl + C`

- verify that no environment variables have leaked into the environment:

  ```shell
  $ env | grep SPRING_
  <no output>
  $ env | grep MYSQL_
  <no output>
  ```



```shell
$ podman container rm -f container-tutorial-60-mysql \
    && \
    podman volume prune
```
