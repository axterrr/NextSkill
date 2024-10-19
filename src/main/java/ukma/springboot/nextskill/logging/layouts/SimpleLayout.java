package ukma.springboot.nextskill.logging.layouts;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLayout extends AbstractStringLayout {
    protected SimpleLayout(Charset charset) {
        super(charset);
    }

    protected SimpleLayout() {
        super(StandardCharsets.UTF_8);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return String.format(
                "%s | %-5s | %s - %s%n",
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(event.getTimeMillis())),
                event.getLevel(),
                event.getSource().getClassName(),
                event.getMessage().getFormattedMessage()
        );
    }
}
