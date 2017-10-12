1. java.util.StringJoiner: 拼接字符串
   Collectors.join的底层调用的就是这个.

2.
    StringJoiner sj = new StringJoiner("/", "prefix-", "-suffix");
    sj.add("2016");
    sj.add("02");
    sj.add("26");
    String result = sj.toString(); //prefix-2016/02/26-suffix

3. //2015-10-31
  String result = String.join("-", "2015", "10", "31" );

4.
List<String> list = Arrays.asList("java", "python", "nodejs", "ruby");
//java, python, nodejs, ruby
String result = String.join(", ", list);

5. StringJoiner::merge
   2个SttingJoiner融合,分隔符是第一个的.
