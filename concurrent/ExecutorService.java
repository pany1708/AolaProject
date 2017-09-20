1. eg:

AtomicInteger atomicInt = new AtomicInteger(0);
ExecutorService executor = Executors.newFixedThreadPool(2);
IntStream.range(0, 1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));
executor.shutdown();
System.out.println(atomicInt.get());
