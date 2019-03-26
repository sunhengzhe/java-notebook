package basic.exception;

import java.io.IOException;

class MyUncheckedException extends RuntimeException {

}

class MyCheckedException extends IOException {

}

class User {
    // 无须 throws
    public void say() {
        throw new MyUncheckedException();
    }

    // 需要 throws
    public void read() throws MyCheckedException {
        throw new MyCheckedException();
    }
}

public class Demo {
    public static void main(String[] args) throws MyCheckedException {
        User user = new User();
        user.say();
        user.read();
    }
}
