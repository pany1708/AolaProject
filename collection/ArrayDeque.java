public void addFirst(E e) {
    if (e == null) // 非空
        throw new NullPointerException();
    elements[head = (head - 1) & (elements.length - 1)] = e; // 下标是否越界
    if (head == tail)  // 空间是否够用
        doubleCapacity(); // 扩容
}

1.   elements[head = (head - 1) & (elements.length - 1)] = e; // 下标是否越界
2. 循环数组(circular array): 长度总是1<<n
   这段代码相当于取余，同时解决了head为负值的情况。
3. (head - 1) & (elements.length - 1) == (head - 1); 【重点理解】

assert <boolean表达式> : <错误信息表达式>
如果<boolean表达式>为true，则程序继续执行。
如果为false，则程序抛出java.lang.AssertionError，并输入<错误信息表达式>。

4. 扩容是在插入问题解决之后解决的.
