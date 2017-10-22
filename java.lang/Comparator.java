1. Java排序对象Comparable和Comparator:

2. Comparable是排序接口：一个类实现了Comparable接口,该类支持排序,可以通过 Collections.sort 或者 Arrays.sort进行自动排序.

@Override
public int compareTo(Person p){}

从小到大排序.

3. Comparator是比较接口，我们如果需要控制某个类的次序

public interface Comparator<T>
 {
    int compare(T o1, T o2); // 重写这个
    boolean equals(Object obj);
 }

4. Comparable相当于“内部比较器”，而Comparator相当于“外部比较器”.




eg： Comparable

public class Person implements Comparable<Person>
{
    String name;
    int age;
    public Person(String name, int age)
    {
        super();
        this.name = name;
        this.age = age;
    }
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    @Override
    public int compareTo(Person p)
    {
        return this.age-p.getAge();
    }
    public static void main(String[] args)
    {
        Person[] people=new Person[]{new Person("xujian", 20),new Person("xiewei", 10)};
        System.out.println("排序前");
        for (Person person : people)
        {
            System.out.print(person.getName()+":"+person.getAge());
        }
        Arrays.sort(people);
        System.out.println("\n排序后");
        for (Person person : people)
        {
            System.out.print(person.getName()+":"+person.getAge());
        }
    }
}
