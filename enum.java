1. eg1
ItTakesTwo161111里的例子：
定义:
private enum PerfectOption {
  完美学习力(PerfectOption::perfectEffort),
  满级(PerfectOption::perfectFullLevel),
  王者无敌(PerfectOption::perfectWZWD);

  final Consumer<NewPMParam> perfectParamConsumer;
  private PerfectOption(Consumer<NewPMParam> c) {
    this.perfectParamConsumer = c;
  }

  private static void perfectWZWD(NewPMParam newPMParam) {
    newPMParam.initIndBAType = 3;
    newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
  }

  private static void perfectFullLevel(NewPMParam newPMParam) {
    newPMParam.level = 100;
  }

  private static void perfectEffort(NewPMParam newPMParam) {
    newPMParam.initIndBAType = 3;
    if (newPMParam.strIndBA.isEmpty()) {
      newPMParam.strIndBA = "35#35#35#35#35#35";
    }
    newPMParam.setEffBaseAbility(NewPMParam.getPerfectEffBa(newPMParam.raceId));
  }
}

使用:
private static final PerfectOption[][] PERFECT_OPTIONS = {
    {PerfectOption.完美学习力},
    {PerfectOption.满级},
    {PerfectOption.王者无敌},
    {PerfectOption.完美学习力, PerfectOption.满级},
    {PerfectOption.完美学习力, PerfectOption.王者无敌},
    {PerfectOption.满级, PerfectOption.王者无敌},
    {PerfectOption.完美学习力, PerfectOption.满级, PerfectOption.王者无敌},
};

int index = RandomUtil.randomIndex(RATES_PERFECT);
NewPMParam newPMParam = new NewPMParam(raceId, 1, 2);
for(PerfectOption perfectOption : PERFECT_OPTIONS[index]) {
   perfectOption.perfectParamConsumer.accept(newPMParam);
}


2. eg:  http://www.cnblogs.com/zhaoyanjun/p/5659811.html
