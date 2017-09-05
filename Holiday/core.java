1. Holiday的核心4个类: ResponderManager, ResponderClassWrapper, HolidayResponder, HolidayExtension

2. ResponderManager:

   private ResponderManager():
1) 获取"com.altratek.altraserver.extensions"包下的索引继承了HolidayResponder类的对象到list<> subClasses;
2) 移除被注解为无效的类对象(不在BeginDate/EndDate时间内的对象);
3) 遍历subClasses，把所有对象中被Command、SimpleCommand注解的方法提取到HashMap<String, ResponderClassWrapper> 中


   public void handleRequest(HolidayExtension holidayExtension...) ：处理命令请求

   public synchronized void register(HolidayExtension holidayExtension) ：
    注册 "com.altratek.altraserver.extensions"包下的所有被EventInject注解的方法


2. ResponderClassWrapper: 注解处理器解析,还有些细节待定

3. HolidayExtension: 这个没有理解

4. HolidayResponder: 父类

------------------------------------------------------------------------------------------------------------
1. EventDispatcher
