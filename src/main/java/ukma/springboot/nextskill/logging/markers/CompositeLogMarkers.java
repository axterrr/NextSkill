package ukma.springboot.nextskill.logging.markers;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class CompositeLogMarkers {
    public static final Marker COURSE_CREATE_MARKER = MarkerManager.getMarker("COURSE_CREATE").addParents(LogMarkers.COURSE_MARKER, LogMarkers.CREATE_MARKER);
}
