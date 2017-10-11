1.
public static String getName(User u) {
    if (u == null)
        return "Unknown";
    return u.name;
}

修改为:
return Optional.ofNullable(u).map(user -> user.name).orElse("Unknown");

2. Optional给了我们一个真正优雅的Java风格的方法来解决null安全问题。

3. 可以视为一个可以为null的容器对象.
