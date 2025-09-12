/**
 * The Attempt module represents user attempts to complete tests.
 * It manages the lifecycle of an attempt and publishes events
 * such as {@code AnswersSubmittedEvent}.
 *
 * Exposes:
 * - {@code AttemptExternalAPI} for other modules
 * - {@code AttemptInternalAPI} for internal collaboration
 *
 * Allowed dependencies:
 * - Answer module (for saving submitted answers)
 * - User module (to associate attempts with users)
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Attempt",
        allowedDependencies = {"answer", "user"}
)
package ukma.springboot.nextskill.modules.attempt;

import org.springframework.modulith.ApplicationModule;
