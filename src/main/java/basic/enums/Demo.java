package basic.enums;

enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}

enum Status {
    NOT_ASSIGNED(1),
    ASSIGNED(2),
    CONTACTED(3),
    SOLD(4);

    private final int code;

    Status (int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

public class Demo {
    public static void main(String[] args) {
        for(Day day: Day.values()) {
            System.out.println("day " + day.toString());
        }

        for(Status status: Status.values()) {
            System.out.printf("status %s %d%n", status, status.getCode());
        }
    }
}
