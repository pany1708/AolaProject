1. synchronized 作用:
   1) 直接作用于实例方法: 对当前实例加锁.
   2) 直接作用于静态方法: 对当前类加锁.[当前类的Class对象].
   3) synchronized(obj){}: 锁住的是obj这个对象,它的粒度不是在方法级别,而是特定的代码块.

2. synchronized 不保证原子性.
