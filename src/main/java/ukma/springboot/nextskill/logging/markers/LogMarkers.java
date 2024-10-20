package ukma.springboot.nextskill.logging.markers;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LogMarkers {
    public static final Marker COURSE_MARKER = MarkerManager.getMarker("COURSE");
    public static final Marker COURSE_SECTION_MARKER = MarkerManager.getMarker("COURSE_SECTION");
    public static final Marker COURSE_OBJECT_MARKER = MarkerManager.getMarker("COURSE_OBJECT");
    public static final Marker USER_MARKER = MarkerManager.getMarker("USER");
    public static final Marker CREATE_MARKER = MarkerManager.getMarker("CREATE");
    public static final Marker UPDATE_MARKER = MarkerManager.getMarker("UPDATE");
    public static final Marker DELETE_MARKER = MarkerManager.getMarker("DELETE");
    public static final Marker READ_MARKER = MarkerManager.getMarker("READ");
    public static final Marker GET_MARKER = MarkerManager.getMarker("GET");
    public static final Marker POST_MARKER = MarkerManager.getMarker("POST");
    public static final Marker PUT_MARKER = MarkerManager.getMarker("PUT");

}
