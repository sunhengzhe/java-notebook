package design.patterns.factory.simple;

abstract class Product {
    abstract public void log();
}

class ProductA extends Product {

    @Override
    public void log() {
        System.out.println("I am product A");
    }
}

class ProductB extends Product {

    @Override
    public void log() {
        System.out.println("I am product B");
    }
}

class Factory {
    public static Product createProduct(String productName) {
       if (productName.equals("A")) {
           return new ProductA();
       } else if (productName.equals("B")) {
           return new ProductB();
       }

       return null;
    }
}

public class Demo {
    public static void main(String[] args) {
        Product product = Factory.createProduct("A");
        product.log();
    }
}
