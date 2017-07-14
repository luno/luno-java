# [Luno]

Java library to connect to the [Luno REST API]

#### Build the library
```
./gradlew build
```

#### Usage
```java
// create the API instance
LunoAPI luno = new LunoAPIImpl("key", "secret");
// use the API
LunoOrders listorders = luno.listOrders(State.PENDING, null)
//...
```
[Luno]: <http://www.luno.com>
[Luno REST API]: <https://www.luno.com/en/api>
 