package design.patterns.factory.abstract_;

/**
 * 抽象产品 A
 */
abstract class Mouse {
    abstract public void move();
}

/**
 * 抽象产品 B
 */
abstract class Keyboard {
    abstract public void press();
}


/**
 * AppleMouse 和 SamsungMouse 同属一个产品等级结构
 *
 * AppleMouse 和 AppleKeyboard 同属一个产品族
 */
class AppleMouse extends Mouse {

    @Override
    public void move() {
        System.out.println("apple mouse move");
    }
}

class SamsungMouse extends Mouse {

    @Override
    public void move() {
        System.out.println("samsung mouse move");
    }
}

class AppleKeyboard extends Keyboard {

    @Override
    public void press() {
        System.out.println("apple keyboard press");
    }
}

class SamsungKeyboard extends Keyboard {

    @Override
    public void press() {
        System.out.println("samsung keyboard press");
    }
}

/**
 * 抽象工厂
 */
abstract class Factory {
    abstract public Mouse createMouse();

    abstract public Keyboard createKeyboard();
}

/**
 * 具体工厂 A
 */
class AppleFactory extends Factory {

    @Override
    public Mouse createMouse() {
        return new AppleMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new AppleKeyboard();
    }
}

/**
 * 具体工厂 B
 */
class SamsungFactory extends Factory {

    @Override
    public Mouse createMouse() {
        return new SamsungMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new SamsungKeyboard();
    }
}



public class Demo {
    public static void main(String[] args) {
        AppleFactory appleFactory = new AppleFactory();

        Keyboard keyboard = appleFactory.createKeyboard();
        keyboard.press();

        Mouse mouse = appleFactory.createMouse();
        mouse.move();
    }
}
