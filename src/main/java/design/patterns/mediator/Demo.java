package design.patterns.mediator;

abstract class Mediator {
    protected HouseOwner houseOwner;
    protected Tenant tenant;

    public void setHouseOwner(HouseOwner houseOwner) {
        this.houseOwner = houseOwner;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    abstract public void collectRent(int money);
}

class ConcreteMediator extends Mediator {

    @Override
    public void collectRent(int money) {
        tenant.reduceMoney(money);
        houseOwner.addMoney(money);
    }
}

abstract class Person {
    protected Mediator mediator;

    public Person(Mediator mediator) {
        this.mediator = mediator;
    }
}

class HouseOwner extends Person {

    public HouseOwner(Mediator mediator) {
        super(mediator);
    }

    /**
     * 委托中介者
     * @param money
     */
    public void collectRent(int money) {
        mediator.collectRent(money);
    }

    /**
     * 自己的业务逻辑
     * @param money
     */
    public void addMoney(int money) {
        System.out.println("House owner get money: " + money);
    }
}

class Tenant extends Person {

    public Tenant(Mediator mediator) {
        super(mediator);
    }

    /**
     * 自己的业务逻辑
     * @param money
     */
    public void reduceMoney(int money) {
        System.out.println("Tenant reduce money: " + money);
    }

    /**
     * 委托中介者
     * @param money
     */
    public void sendRent(int money) {
        mediator.collectRent(money);
    }
}

public class Demo {
    public static void main(String[] args) {
        ConcreteMediator concreteMediator = new ConcreteMediator();

        HouseOwner houseOwner = new HouseOwner(concreteMediator);
        Tenant tenant = new Tenant(concreteMediator);

        concreteMediator.setHouseOwner(houseOwner);
        concreteMediator.setTenant(tenant);

        houseOwner.collectRent(3000);
        System.out.println("----");
        tenant.sendRent(3000);
    }
}
