package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.CourseLimitExceededException;

@Service
public class CourseLimitService {

    @Value("${course.limit.enabled}")
    private boolean limitEnabled;

    @Value("${course.limit.max}")
    private int maxCourses;

    public void checkCourseLimit(int currentCourses) {
        if (limitEnabled && currentCourses > maxCourses) {
            throw new CourseLimitExceededException("You have reached the course limit of " + maxCourses);
        }
    }
}
