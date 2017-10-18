1. 一般来说，如果需要使用的Map中的key无序，选择HashMap；如果要求key有序，则选择TreeMap。
但是选择TreeMap就会有性能问题，因为TreeMap的get操作的时间复杂度是O(log(n))的，相比于HashMap的O(1)还是差不少的，
LinkedHashMap的出现就是为了平衡这些因素

2. LinkedHashMap是key键有序的HashMap的一种实现。它除了使用哈希表这个数据结构，使用双向链表来保证key的顺序
