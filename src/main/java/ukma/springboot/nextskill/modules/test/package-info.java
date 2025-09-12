/**
 * The Test module manages tests and their structure.
 * It depends on Question and Section modules.
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Test",
        allowedDependencies = {"question", "section", "course"}
)
package ukma.springboot.nextskill.modules.test;

import org.springframework.modulith.ApplicationModule;
