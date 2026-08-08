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
    --publish $(sed -n 's/^MYSQL_PORT=//p' .env):$(sed -n 's/^MYSQL_PORT=//p' .env) \
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



# initialize a migration repository

```shell
$ (
    set -a ;

    source .env ;

    ./mvnw -q clean compile liquibase:diff \
        -Dliquibase.changeLogFile=src/main/resources/db/changelog/db.changelog-master.mysql.sql \
        -Dliquibase.diffChangeLogFile=src/main/resources/db/changelog/db.changelog-master.mysql.sql ;
)

# generates `src/main/resources/db/changelog/db.changelog-master.mysql.sql`
```

apply the initial set of migrations
```shell
$ (
    set -a ;

    source .env ;

    ./mvnw -q clean compile liquibase:update \
        -Dliquibase.changeLogFile=db/changelog/db.changelog-master.mysql.sql ;
)

UPDATE SUMMARY
Run:                          1
Previously run:               1
Filtered out:                 0
-------------------------------
Total change sets:            2
```

```sql
mysql> show tables;
+--------------------------------+
| Tables_in_tutorial-60-database |
+--------------------------------+
| DATABASECHANGELOG              |
| DATABASECHANGELOGLOCK          |
| user                           |
+--------------------------------+
3 rows in set (0.00 sec)

mysql> select ID, FILENAME, DATEEXECUTED from DATABASECHANGELOG;
+-----------------+--------------------------------------------+---------------------+
| ID              | FILENAME                                   | DATEEXECUTED        |
+-----------------+--------------------------------------------+---------------------+
| 1785443916372-1 | db/changelog/db.changelog-master.mysql.sql | 2026-07-30 20:38:54 |
| 1785444468459-1 | db/changelog/db.changelog-master.mysql.sql | 2026-07-30 20:48:50 |
+-----------------+--------------------------------------------+---------------------+
2 rows in set (0.00 sec)
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

```sql
mysql> show tables;
+--------------------------------+
| Tables_in_tutorial-60-database |
+--------------------------------+
| DATABASECHANGELOG              |
| DATABASECHANGELOGLOCK          |
| user                           |
+--------------------------------+
3 rows in set (0.00 sec)

mysql> select ID, FILENAME, DATEEXECUTED from DATABASECHANGELOG;
-- The output should be identical to
-- the output of the above-noted preceding run of this same statement.
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
    -d email=someemail@someemailprovider.com \
    -d zipCode=1234
Saved

$ curl \
    http://localhost:8080/demo/all \
    | json_pp
# ...
[
   {
      "email" : "someemail@someemailprovider.com",
      "id" : 1,
      "name" : "John",
      "zipCode" : "1234"
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
