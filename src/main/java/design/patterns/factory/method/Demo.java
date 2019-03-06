package design.patterns.factory.method;

/**
 * 抽象产品
 */
abstract class Logger {
   abstract public void info();
}

/**
 * 具体产品
 */
class FileLog extends Logger {

   @Override
   public void info() {
      System.out.println("log to file");
   }
}

class ConsoleLog extends Logger {

    @Override
    public void info() {
        System.out.println("log to console");
    }
}

/**
 * 抽象工厂
 */
abstract class LogFactory {
    abstract public Logger createLog();
}

/**
 * 具体工厂
 */
class FileLogFactory extends LogFactory {

    @Override
    public Logger createLog() {
        return new FileLog();
    }
}

class ConsoleLogFactory extends LogFactory {

    @Override
    public Logger createLog() {
        return new ConsoleLog();
    }
}

public class Demo {
    public static void main(String[] args) {
        Logger logger = new ConsoleLogFactory().createLog();
        logger.info();
    }
}
