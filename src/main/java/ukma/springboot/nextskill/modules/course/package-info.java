/**
 * The Course module manages courses and their ownership.
 * It controls course creation, editing, and publication.
 *
 * Allowed dependencies:
 * - User module (for ownership and access rights)
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Course",
        allowedDependencies = {"user"}
)
package ukma.springboot.nextskill.modules.course;

import org.springframework.modulith.ApplicationModule;
