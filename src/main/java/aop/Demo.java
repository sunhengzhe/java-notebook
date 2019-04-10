package aop;

class HappyTheater implements Performance {

    @Override
    public void perform(String name, int time) {
        System.out.println("~~~ performing " + name + " takes " + time + "s ~~~");
    }
}

public class Demo {
    public static void main(String[] args) {
        HappyTheater happyTheater = new HappyTheater();
        happyTheater.perform("Sing", 60);
    }
}
