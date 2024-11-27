package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ukma.springboot.nextskill.exceptions.MaxAttemptsException;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.TestAttemptService;
import ukma.springboot.nextskill.services.TestService;
import ukma.springboot.nextskill.services.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class TestController {

    TestService testService;
    UserService userService;
    TestAttemptService attemptService;

    @GetMapping("/test/{uuid}")
    public String testInfo(@PathVariable String uuid, Model model) {
        TestResponse test = testService.get(UUID.fromString(uuid));
        model.addAttribute("test", test);

        UserResponse authenticatedUser = userService.getAuthenticatedUser();

        boolean hasOwnerRights = testService.hasOwnerRights(authenticatedUser.getUuid(), test.getUuid());

        model.addAttribute("isOwner", hasOwnerRights);
        model.addAttribute("user", authenticatedUser);

        boolean canTakeAttempt = !test.getQuestions().isEmpty();
        if (test.getAttempts().stream().anyMatch(
                attempt -> attempt.getCompletedBy().getUuid() == authenticatedUser.getUuid()
        )) {
            canTakeAttempt = false;
        }
        model.addAttribute("canTakeAttempt", canTakeAttempt);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        model.addAttribute("formattedDate", test.getCreatedAt().format(formatter));

        List<TestAttemptResponse> userAttempts = test.getAttempts().stream().filter(
                attempt -> attempt.getCompletedBy().getUuid().equals(authenticatedUser.getUuid())
        ).toList();

        model.addAttribute("myAttempts", userAttempts);

        return "testInfo";
    }

    @GetMapping("/test/{uuid}/attempt")
    public String doAttempt(@PathVariable(name = "uuid") String testUuid, Model model) {
        UserResponse authenticatedUser = userService.getAuthenticatedUser();
        UUID testId = UUID.fromString(testUuid);
        UUID userId = authenticatedUser.getUuid();

        Optional<TestAttemptResponse> unfinishedAttempt = attemptService.getUnfinishedAttempt(testId, userId);
        if (unfinishedAttempt.isPresent()) {
            return "redirect:/test/" + testUuid + "/attempt/" + unfinishedAttempt.get().getUuid();
        }

        List<TestAttemptResponse> finishedAttempts = attemptService.getFinishedAttempts(testId, userId);
        if (!finishedAttempts.isEmpty()) throw new MaxAttemptsException();

        TestAttemptResponse newAttempt = attemptService.createNewAttempt(testId, userId);

        return "redirect:/test/" + testUuid + "/attempt/" + newAttempt.getUuid();
    }
}

/*
    TODO:
        1. Restrict access to the page if user is not a part of the course
 */