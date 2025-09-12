/**
 * The Post module is responsible for handling user posts
 * (e.g. course announcements, discussions).
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Post",
        allowedDependencies = {"user", "course"}
)
package ukma.springboot.nextskill.modules.post;

import org.springframework.modulith.ApplicationModule;
