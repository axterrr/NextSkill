package ukma.springboot.nextskill.interfaces;

public interface ILogger {
    void info(String message);
    void warn(String message);
    void error(String message);
    void debug(String message);
}

