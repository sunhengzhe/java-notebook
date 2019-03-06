package design.patterns.factory.method;

abstract class Logger {
   abstract public void info();
}

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

abstract class LogFactory {
    abstract public Logger createLog();
}

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
