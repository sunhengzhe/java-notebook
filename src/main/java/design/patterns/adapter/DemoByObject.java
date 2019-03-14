package design.patterns.adapter;

import java.util.HashMap;
import java.util.Map;

interface TargetByObject {
    String getName();
    String getAddress();
}

class AdapteeByObject implements AdapteeByObjectIml {
    private String name;

    public AdapteeByObject(String name) {
        this.name = name;
    }

    public Map<String, String> getUserInfo() {
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", this.name);
        return userInfo;
    }
}

class AdapteeByObject2 implements AdapteeByObject2Iml {
    private String address;

    public AdapteeByObject2(String address) {
        this.address = address;
    }

    public Map<String, String> getAddressInfo() {
        HashMap<String, String> addressInfo = new HashMap<>();
        addressInfo.put("address", this.address);
        return addressInfo;
    }
}

interface AdapteeByObjectIml {
    Map<String, String> getUserInfo();
}

interface AdapteeByObject2Iml {
    Map<String, String> getAddressInfo();
}

class AdapterByObject implements TargetByObject {
    private AdapteeByObjectIml adapteeByObjectIml;
    private AdapteeByObject2Iml adapteeByObject2Iml;

    public AdapterByObject(AdapteeByObjectIml adapteeByObjectIml, AdapteeByObject2Iml adapteeByObject2Iml) {
        this.adapteeByObjectIml = adapteeByObjectIml;
        this.adapteeByObject2Iml = adapteeByObject2Iml;
    }

    @Override
    public String getName() {
        return adapteeByObjectIml.getUserInfo().get("name");
    }

    @Override
    public String getAddress() {
        return adapteeByObject2Iml.getAddressInfo().get("address");
    }
}

public class DemoByObject {
    public static void main(String[] args) {
        AdapteeByObjectIml adapteeByObjectIml = new AdapteeByObject("aaaa");
        AdapteeByObject2Iml adapteeByObject2Iml = new AdapteeByObject2("bbb");

        TargetByObject targetByObject = new AdapterByObject(adapteeByObjectIml, adapteeByObject2Iml);
        System.out.println(targetByObject.getName() + ":" + targetByObject.getAddress());
    }
}
