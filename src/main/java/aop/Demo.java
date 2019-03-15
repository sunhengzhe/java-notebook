package aop;

class HappyTheater implements Performance {

    @Override
    public void perform() {
        System.out.println("~~~ performing ~~~");
    }
}

public class Demo {
    public static void main(String[] args) {
        HappyTheater happyTheater = new HappyTheater();
        happyTheater.perform();
    }
}
