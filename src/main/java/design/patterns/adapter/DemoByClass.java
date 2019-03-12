package design.patterns.adapter;

import java.util.HashMap;
import java.util.Map;

interface Target {
    public String getName();
    public String getAddress();
}

class Adaptee {
    private String name;
    private String address;

    public Adaptee(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Map<String, String> getUserInfo() {
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", this.name);
        userInfo.put("phone", this.address);
        return userInfo;
    }
}

class Adapter extends Adaptee implements Target {

    public Adapter(String name, String phone) {
        super(name, phone);
    }

    @Override
    public String getName() {
        return this.getUserInfo().get("name");
    }

    @Override
    public String getAddress() {
        return this.getUserInfo().get("address");
    }
}

public class DemoByClass {
    public static void main(String[] args) {
        Target target = new Adapter("aa", "bb");
        System.out.println(target.getName());
    }
}
