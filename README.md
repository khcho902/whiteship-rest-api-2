## 백기선 - REST API

- [인프런](https://www.inflearn.com/course/spring_rest-api)
- [강의자료](https://docs.google.com/document/d/1GFo3W6XxqhxDVVqxiSEtqkuVCX93Tdb3xzINRtTIx10/edit?usp=sharing)
- [참고깃랩](https://gitlab.com/whiteship/natural)
- [참고깃헙](https://github.com/keesun/study/tree/master/rest-api-with-spring)

### 변경사항
- postgresql => mysql<br>
- maven => gradle 

### 실행 script
```shell
./gradlew build
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### Mysql 컨테이너 생성 및 실행(백그라운드)
```shell
docker run --name rest-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pass -d mysql:8.0.28
```
### 실행중인 Mysql 컨테이너에 접속
```shell
docker exec -i -t rest-mysql bash
```

### 컨테이너 접속후 DB 생성하기
```shell
root/# mysql -uroot -ppass
mysql> create schema restdb;
```

### application.properties
```
spring.jackson.deserialization.fail-on-unknown-properties=true

spring.datasource.username=root
spring.datasource.password=pass
spring.datasource.url=jdbc:mysql://localhost:3306/restdb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### application-test.properties
```
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver

spring.datasource.hikari.jdbc-url=jdbc:h2:mem:testdb
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

### 실행중인 톰캣 확인
```shell
ps ax | grep tomcat
```