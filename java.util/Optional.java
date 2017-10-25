1.
public static String getName(User u) {
    if (u == null)
        return "Unknown";
    return u.name;
}

修改为:
return Optional.ofNullable(u).map(user -> user.name).orElse("Unknown");

2. Optional给了我们一个真正优雅的Java风格的方法来解决null安全问题。

3. java8新加入的一个容器,这个容器只存1个或0个元素,防止NullpointException.

4. 几个核心的方法:
    isPresent()
    ifPresent()
    get()
    orElse()

5. OptionalDouble, OptionalInt, OptionalLong
   这3个是为了应付 double, int, long.
   Optional<T> ---- 存入的是obj.

6. 
