/**
 * The Question module manages questions for tests.
 * It depends on the Option module for possible answers.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Question",
        allowedDependencies = {"option"}
)
package ukma.springboot.nextskill.modules.question;

import org.springframework.modulith.ApplicationModule;
