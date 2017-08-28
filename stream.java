private int getBallKind(int[] ballData) {
  return (int) IntStream.of(ballData).filter(i -> i > 0).count();
}


private boolean isExsitGold(int userId) {
  ArrayList<UserPetCard> cardList = new ArrayList<>();
  ArrayList<UserPetEquip> equipList = new ArrayList<>();
  int num = 0;
  for (int i = 0; i < EQUIP_ID_CONFIG.length; ++i) {
    cardList = PetCardService.instance.getBatchUserPetCards(userId,
        StringUtil.intArrayToString(EQUIP_ID_CONFIG[i]));
    equipList = PetEquipService.getInstance().getBatchUserPetExistCardsByEquipTypes(userId, EQUIP_ID_CONFIG[i]);
    if (cardList.stream().filter(card -> card.getQuantity() > 0).count() > 0 || equipList.size() > 0) {
      num++;
    }
  }
  return num == 4;
}


ArrayList<Integer> idToGet = new ArrayList<>();
			for (int i = 0; i < dbBall.length - 2; i++) {
				if (dbBall[i] == 0) {
					idToGet.add(i);
				}
			}

int[] idToGet = IntStream.of(Arrays.copyOf(dbBall, dbBall.length - 2)).filter(i -> i==0).toArray();



public static Service[] getDefine(Integer... ids) {
		return Arrays.stream(ids).map(id -> defineMap.get(id)).toArray(Service[]::new);
}
这种写法：
String[] stringArr = { "a", "b", "c", "d" };
Stream<String> stream = Stream.of(stringArr);
String[] arr = stream.toArray(size -> new String[size]);
System.out.println(Arrays.toString(arr));
等价写法
String[] arr = stream.toArray(String[]::new);


————————————————————————————————————————————————————————————————————————————————————————————————————————————————

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {

        arraysStream();

        collectionStream();

        generate();

        iterateStream();

        populaStream();
    }

    //From Arrays
    public static void arraysStream() {
        String[] arr = {"program", "creek", "program", "creek", "java", "web",
            "program"};
        Stream<String> stream = Stream.of(arr);
        System.out.println(Arrays.toString(arr));

    }

    //From Collections
    public static void collectionStream() {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("python");
        list.add("c++");
        list.add("c");
        list.add("lisp");

        Stream<String> stream = list.stream().filter(p -> p.length() > 3);
        String[] arr = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(arr));
    }

    //Use Stream.generate()
    public static void generate() {
        Stream<String> stream = Stream.generate(() -> "test").limit(10);
        String[] strArr = stream.toArray(String[]::new);
        System.out.println(Arrays.toString(strArr));
    }

    //Use Stream.iterate()
    public static void iterateStream() {
        Stream<BigInteger> bigIntStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.TEN)).limit(10);
        BigInteger[] bigIntArr = bigIntStream.toArray(BigInteger[]::new);
        System.out.println(Arrays.toString(bigIntArr));
    }

    // From Popular APIs
    public static void populaStream() {
        String sentence = "Program creek is a Java site.";
        Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(sentence);
        String[] wordArr = wordStream.toArray(String[]::new);
        System.out.println(Arrays.toString(wordArr));
    }
}

_______________________________________________________________________________________________________________

1. Stream不是数据结构,也不存储数据,它是有关算法和计算的，它更像一个高级版本的 Iterator。原始版本的 Iterator，用户只能显式地
一个一个遍历元素并对其执行某些操作；高级版本的 Stream，用户只要给出需要对其包含的元素执行什么操作，比如 “过滤掉长度大于 10
的字符串”、“获取每个字符串的首字母”等，Stream 会隐式地在内部进行遍历，做出相应的数据转换。

2. 而和迭代器又不同的是，Stream 可以并行化操作，迭代器只能命令式地、串行化操作
并行去遍历时，数据会被分成多个段，其中每一个都在不同的线程中处理，然后将结果一起输出。Stream 的并行操作依赖于 Java7 中引入
的 Fork/Join 框架（JSR166y）来拆分任务和加速处理过程。Java 的并行 API 演变历程基本如下：
1.0-1.4 中的 java.lang.Thread
5.0 中的 java.util.concurrent
6.0 中的 Phasers 等
7.0 中的 Fork/Join 框架
8.0 中的 Lambda
Stream 的另外一大特点是，数据源本身可以是无限的。

3.当我们使用一个流的时候，通常包括三个基本步骤：
获取一个数据源（source）→ 数据转换→执行操作获取想要的结果，每次转换原有 Stream 对象不改变，返回一个新的 Stream 对象
（可以有多次转换）

4.生成Stream
从 Collection 和数组
Collection.stream()
Collection.parallelStream()
Arrays.stream(T array) or Stream.of()

// 1. Individual values
Stream stream = Stream.of("a", "b", "c");
// 2. Arrays
String [] strArray = new String[] {"a", "b", "c"};
stream = Stream.of(strArray);
stream = Arrays.stream(strArray);
// 3. Collections
List<String> list = Arrays.asList(strArray);
stream = list.stream();

5. IntStream、LongStream、DoubleStream。当然我们也可以用 Stream<Integer>、Stream<Long> >、Stream<Double>，但是 boxing
和 unboxing 会很耗时，所以特别为这三种基本数值型提供了对应的 Stream。

6.流转换为其它数据结构

// 1. Array
String[] strArray1 = stream.toArray(String[]::new);
// 2. Collection
List<String> list1 = stream.collect(Collectors.toList());
List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
Set set1 = stream.collect(Collectors.toSet());
Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
// 3. String
String str = stream.collect(Collectors.joining()).toString();


7. 转换大写
List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());

8.当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
_________________________________________________________________________________________________________
1.
private String getTeamUserIds(Team team) {
  String teamIds = Stream.of(team.users).map(u -> u == null ? "0" : String.valueOf(u.userId)).collect(Collectors.joining("#"));
  return teamIds;
}
2.
list.stream().forEach(ids -> rw.addWanted(ids));
3.
Map<Data, Integer> setMap = new HashMap<>();
IntStream.range(0, DB_BOOKS.length).forEach(i -> setMap.put(DB_BOOKS[i], data[i] - 1));
4.
int[] data = DbData.getMultiDataArray(u.getUserId(), DB_BOOKS);
if(IntStream.of(data).anyMatch(d -> d <= 0)) {
		return -2;
}
5.
boolean hasGetAllBooks = evo || IntStream.of(data).allMatch(d -> d > 0);
6.
pojoList.stream().filter(pojo -> npMap.containsKey(pojo.FrUserId)).map(pojo -> {
			ActionscriptObject ao = new ActionscriptObject();
			NameParams np = npMap.get(pojo.FrUserId);
			ao.putString("dd", np.getDuoduoId());
			ao.putString("nn", np.getNickname());
			ao.putLong("t", pojo.Time);
			ao.putBool("g", pojo.IsGet);
			ao.putBool("d", pojo.Done);
			return ao;
}).collect(Collectors.toList());
