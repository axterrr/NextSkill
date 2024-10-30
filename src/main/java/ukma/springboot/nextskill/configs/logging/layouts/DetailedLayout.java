package ukma.springboot.nextskill.configs.logging.layouts;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Plugin(
        name = "DetailedLayout",
        category = Core.CATEGORY_NAME,
        elementType = Layout.ELEMENT_TYPE)
public class DetailedLayout extends AbstractStringLayout {
    public DetailedLayout(Charset charset) {
        super(charset);
    }

    public DetailedLayout() {
        super(StandardCharsets.UTF_8);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return String.format(
                "%s | %s | %-8s | %s.%s() - %s%n",
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(event.getTimeMillis())),
                event.getLevel(),
                event.getThreadName(),
                event.getSource().getClassName(),
                event.getSource().getMethodName(),
                event.getMessage().getFormattedMessage()
        );
    }

    @PluginFactory
    public static DetailedLayout createLayout(@PluginAttribute("charset") String charset) {
        Charset cs = charset == null ? StandardCharsets.UTF_8 : Charset.forName(charset);
        return new DetailedLayout(cs);
    }
}
