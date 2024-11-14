package ukma.springboot.nextskill.security;

public enum FileStorageType {
    LOCAL(0),
    CLOUD(1);

    final int value;

    FileStorageType(int i) {
        this.value = i;
    }
}
