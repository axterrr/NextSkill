package ukma.springboot.nextskill.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ukma.springboot.nextskill.interfaces.ILogger;

public class SimpleLogger implements ILogger {
    private final Logger logger = LoggerFactory.getLogger(SimpleLogger.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }
}
