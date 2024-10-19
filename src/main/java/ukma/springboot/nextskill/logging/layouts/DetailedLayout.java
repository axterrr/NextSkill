package ukma.springboot.nextskill.logging.layouts;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailedLayout extends AbstractStringLayout {
    protected DetailedLayout(Charset charset) {
        super(charset);
    }

    protected DetailedLayout() {
        super(StandardCharsets.UTF_8);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return String.format(
                "%s | [%s] | %-8s | %s.%s() - %s%n",
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(event.getTimeMillis())),
                event.getLevel(),
                event.getThreadName(),
                event.getSource().getClassName(),
                event.getSource().getMethodName(),
                event.getMessage().getFormattedMessage()
        );
    }
}
