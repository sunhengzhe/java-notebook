package design.patterns.strategy;

class Context {
    private IStategy stategy;

    public Context(IStategy stategy) {
        this.stategy = stategy;
    }

    public int operate(int a, int b) {
        return stategy.operate(a, b);
    }
}

interface IStategy {
    public int operate(int a, int b);
}

class Add implements IStategy {

    @Override
    public int operate(int a, int b) {
        return a + b;
    }
}

class Sub implements IStategy {

    @Override
    public int operate(int a, int b) {
        return a - b;
    }
}

public class Demo {

    public static void main(String[] args) {
        IStategy add = new Add();
        IStategy sub = new Sub();

        Context context = new Context(add);
        System.out.println(context.operate(1, 2));

        Context context1 = new Context(sub);
        System.out.println(context1.operate(1, 2));
    }
}
