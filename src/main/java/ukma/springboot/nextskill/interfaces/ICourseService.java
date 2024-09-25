package ukma.springboot.nextskill.interfaces;

import ukma.springboot.nextskill.model.CourseObject;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    //...

    CourseObject addCourseItem(CourseObject courseObject);
    List<CourseObject> getAllCourseItems();
    CourseObject getCourseItemById(UUID courseObjectId);
    void removeCourseItem(UUID courseObjectId);
}
