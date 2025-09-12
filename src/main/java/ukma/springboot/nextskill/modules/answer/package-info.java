/**
 * The Answer module is responsible for storing and updating
 * user answers during test attempts.
 *
 * Exposes:
 * - {@code AnswerExternalAPI} for other modules
 * - {@code AnswerInternalAPI} for internal collaboration
 *
 * Allowed dependencies:
 * - Attempt module (to link answers to attempts)
 * - Question module (to validate selected options)
 */
@org.springframework.modulith.ApplicationModule(
        displayName = "Answer",
        allowedDependencies = {"attempt", "question"}
)
package ukma.springboot.nextskill.modules.answer;

import org.springframework.modulith.ApplicationModule;
