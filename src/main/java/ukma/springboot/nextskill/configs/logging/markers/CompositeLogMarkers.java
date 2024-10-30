package ukma.springboot.nextskill.configs.logging.markers;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class CompositeLogMarkers {
    public static final Marker COURSE_CREATE_MARKER = MarkerManager.getMarker("COURSE_CREATE").addParents(LogMarkers.COURSE_MARKER, LogMarkers.CREATE_MARKER);
    public static final Marker USER_CREATE_MARKER = MarkerManager.getMarker("USER_CREATE").addParents(LogMarkers.USER_MARKER, LogMarkers.CREATE_MARKER);
}
