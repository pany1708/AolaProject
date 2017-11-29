private static void perfectWZWD(NewPMParam newPMParam) {
    newPMParam.initIndBAType = 3;
    newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
}

private static Void perfectWZWD(NewPMParam newPMParam) {
    newPMParam.initIndBAType = 3;
    newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
    return null;
}

1. 这2种写法是等价的.
2. Void是void的自动装箱类型,想让一个方法 返回类型 永远是 null 的话, 可以把返回类型置为Void.
3. Void 可以作为类, Void一般作为参数类型.

Consumer<NewPMParam> consumer = newPMParam -> {
    newPMParam.initIndBAType = 3;
    newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
};
