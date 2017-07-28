import java.util.*;
import static net.mindview.util.Print.*;


public class PrintingContainers1{
  static Collection fill(Collection<String> collection){
    collection.add("rat");
    collection.add("cat");
    collection.add("dog");
    collection.add("tog");
    return collection;
  }

  static Map fill(Map<String,String> map){
    map.add("rat","Fuzzy");
    map.add("cat","Rags");
    map.add("dog","Bosco");
    map.add("tog","Aboj");
    return map;
  }
  public static void main(String[] args){
    print(new ArrayList<String>());
  }

}
