package ukma.springboot.nextskill.logging.layouts;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Plugin(
        name = "SimpleLayout",
        category = Core.CATEGORY_NAME,
        elementType = Layout.ELEMENT_TYPE)
public class SimpleLayout extends AbstractStringLayout {
    public SimpleLayout(Charset charset) {
        super(charset);
    }

    public SimpleLayout() {
        super(StandardCharsets.UTF_8);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return String.format(
                "[ %-5s ] %s - %s%n",
                event.getLevel(),
                event.getSource().getClassName(),
                event.getMessage().getFormattedMessage()
        );
    }

    @PluginFactory
    public static SimpleLayout createLayout(@PluginAttribute("charset") String charset) {
        Charset cs = charset == null ? StandardCharsets.UTF_8 : Charset.forName(charset);
        return new SimpleLayout(cs);
    }
}
