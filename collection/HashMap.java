1. alibaba:
【强制】不要在foreach循环里进行元素的remove/add操作。remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁。
正例：
  Iterator<String> iterator = list.iterator();
  while (iterator.hasNext()) {
    String item = iterator.next();
    if (删除元素的条件) {
      iterator.remove();
    }
  }
反例：
  List<String> a = new ArrayList<String>();
  list.add("1");
  list.add("2");
  for (String item : list) {
    if ("1".equals(item)) {
      list.remove(item);
    }
  }
