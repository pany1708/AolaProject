1. field.set(classObject, ObjectConverter.convert(from, fieldCls));

2. Field[] fields = cls.getDeclaredFields();
   for(Field field : fields) {
        field.setAccessible(true);
        String fieldName = field.getName();
        Class<?> fieldCls = field.getType()
    }
