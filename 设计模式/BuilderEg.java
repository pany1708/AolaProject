public class NutritionFacts {
    //都设置为final类型，不可变对象，以保证线程的安全
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    //传入builder对象，通过builder对象来初始化参数
    public NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static class Builder{
        //其中servingSize和servings为必要参数，因此设置为final类型，通过构造器传入的参数来初始化
        private final int servingSize;
        private final int servings;
        //其余为可选参数，定义默认值
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;
        //构造器传入必要参数，初始化对象中的必要参数
        public Builder(int servingSize, int servings){
            this.servingSize = servingSize;
            this.servings = servings;
        }
        //设置可选参数，并返回builder对象
        public Builder calories(int val){
            calories = val;
            return this;
        }

        public Builder fat(int val){
            fat = val;
            return this;
        }

        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }

        public Builder sodium(int val){
            sodium = val;
            return this;
        }
        //通过build方法获取到NutritionFacts对象
        public NutritionFacts build(){
            return new NutritionFacts(this);
        }

    }
}

NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
