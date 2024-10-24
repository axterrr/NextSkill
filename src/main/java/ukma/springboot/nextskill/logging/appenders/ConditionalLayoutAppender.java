package ukma.springboot.nextskill.logging.appenders;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;

import java.io.Serializable;

@Plugin(
        name = "ConditionalLayoutAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class ConditionalLayoutAppender extends AbstractAppender {

    private final Layout<? extends Serializable> simplifiedLayout;
    private final Layout<? extends Serializable> detailedLayout;

    protected ConditionalLayoutAppender(String name, Layout<? extends Serializable> simplifiedLayout, Layout<? extends Serializable> detailedLayout) {
        super(name, null, null, false, null);
        this.simplifiedLayout = simplifiedLayout;
        this.detailedLayout = detailedLayout;
    }

    @Override
    public void append(LogEvent event) {
        Layout<? extends Serializable> currentLayout;

        if (event.getLevel().isLessSpecificThan(Level.INFO)) {
            currentLayout = simplifiedLayout;
        } else {
            currentLayout = detailedLayout;
        }

        System.out.print(currentLayout.toSerializable(event));
    }

    @PluginFactory
    public static ConditionalLayoutAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("SimplifiedLayout") Layout<? extends Serializable> simplifiedLayout,
            @PluginElement("DetailedLayout") Layout<? extends Serializable> detailedLayout) {
        return new ConditionalLayoutAppender(name, simplifiedLayout, detailedLayout);
    }
}
