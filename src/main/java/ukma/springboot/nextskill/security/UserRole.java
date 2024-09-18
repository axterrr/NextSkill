package ukma.springboot.nextskill.security;

public enum UserRole {
    STUDENT(0),
    TEACHER(1),
    ADMIN(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "value=" + value +
                '}';
    }
}
