package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.exceptions.MaxAttemptsException;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.*;
import ukma.springboot.nextskill.models.views.TestView;
import ukma.springboot.nextskill.services.*;

import java.util.*;

@Controller
@AllArgsConstructor
public class TestController {

    private static final String REDIRECT_TO_TEST = "redirect:/test/";
    private QuestionAnswerService questionAnswerService;
    private TestService testService;
    private UserService userService;
    private TestAttemptService attemptService;
    private QuestionService questionService;

    @GetMapping("/test/{uuid}")
    public String testInfo(@PathVariable String uuid, Model model) {
        UserResponse authenticatedUser = userService.getAuthenticatedUser();
        testService.checkTestAccess(UUID.fromString(uuid), authenticatedUser);

        TestResponse test = testService.get(UUID.fromString(uuid));
        model.addAttribute("test", test);

        boolean hasOwnerRights = testService.hasOwnerRights(authenticatedUser.getUuid(), test.getUuid());
        model.addAttribute("isOwner", hasOwnerRights);
        model.addAttribute("user", authenticatedUser);

        List<TestAttemptResponse> userAttempts = test.getAttempts().stream().filter(
                attempt -> attempt.getCompletedBy().getUuid().equals(authenticatedUser.getUuid())
        ).toList();
        List<TestAttemptResponse> finishedAttempts =
                attemptService.getFinishedAttempts(test.getUuid(), authenticatedUser.getUuid());

        model.addAttribute("myAttempts", userAttempts);

        boolean canTakeAttempt = !test.getQuestions().isEmpty();
        if (!finishedAttempts.isEmpty()) {
            canTakeAttempt = false;
        }
        model.addAttribute("canTakeAttempt", canTakeAttempt);

        return "test-info";
    }

    @GetMapping("/test/{uuid}/start")
    public String doAttempt(@PathVariable(name = "uuid") String testUuid, Model model) {
        UserResponse authenticatedUser = userService.getAuthenticatedUser();
        testService.checkTestAccess(UUID.fromString(testUuid), authenticatedUser);

        UUID testId = UUID.fromString(testUuid);
        UUID userId = authenticatedUser.getUuid();

        Optional<TestAttemptResponse> unfinishedAttempt = attemptService.getUnfinishedAttempt(testId, userId);
        if (unfinishedAttempt.isPresent()) {
            return REDIRECT_TO_TEST + testUuid + "/attempt/" + unfinishedAttempt.get().getUuid();
        }

        List<TestAttemptResponse> finishedAttempts = attemptService.getFinishedAttempts(testId, userId);
        if (!finishedAttempts.isEmpty()) throw new MaxAttemptsException();

        TestAttemptResponse newAttempt = attemptService.createNewAttempt(testId, userId);

        return REDIRECT_TO_TEST + testUuid + "/attempt/" + newAttempt.getUuid();
    }

    @GetMapping("/test/{testUuid}/attempt/{attemptId}")
    public String getAttempt(
            @PathVariable(name = "testUuid") String testUuid,
            @PathVariable(name = "attemptId") String attemptUuid,
            Model model
    ) {
        UUID testId = UUID.fromString(testUuid);
        UUID attemptId = UUID.fromString(attemptUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        testService.checkTestAccess(testId, authenticated);
        attemptService.checkAttemptAccess(attemptId, authenticated);

        TestAttemptResponse attempt = attemptService.get(attemptId);
        List<QuestionResponse> questions = questionService.getTestQuestions(testId);

        List<UUID> answeredOptions = attempt.getAnswers().stream()
                .map(answer -> answer.getAnswerOption().getId()).toList();

        model.addAttribute("user", authenticated);
        model.addAttribute("answers", answeredOptions);
        model.addAttribute("questions", questions);
        model.addAttribute("testUuid", testUuid);
        model.addAttribute("attemptId", attemptUuid);

        return "test-attempt";
    }

    @PostMapping("/test/{testUuid}/attempt/{attemptId}/submit")
    public String submitAttempt(
            @PathVariable(name = "testUuid") String testUuid,
            @PathVariable(name = "attemptId") String attemptUuid,
            @RequestParam Map<String, String> formData,
            Model model
    ) {
        UUID testId = UUID.fromString(testUuid);
        UUID attemptId = UUID.fromString(attemptUuid);

        TestAttemptResponse attempt = attemptService.get(attemptId);
        if(attempt.isSubmitted())
            return REDIRECT_TO_TEST + testUuid;

        UserResponse authenticated = userService.getAuthenticatedUser();
        testService.checkTestAccess(testId, authenticated);
        attemptService.checkAttemptAccess(attemptId, authenticated);

        questionAnswerService.updateSavedAnswers(formData, UUID.fromString(attemptUuid));
        attemptService.submitAttempt(attemptId);

        return REDIRECT_TO_TEST + testUuid;
    }

    @PostMapping("/test/{testUuid}/attempt/{attemptId}/save")
    public ResponseEntity<String> saveAttempt(
            @PathVariable(name = "testUuid") String testUuid,
            @PathVariable(name = "attemptId") String attemptUuid,
            @RequestParam Map<String, String> formData
    ) {
        UUID testId = UUID.fromString(testUuid);
        UUID attemptId = UUID.fromString(attemptUuid);

        TestAttemptResponse attempt = attemptService.get(attemptId);
        if(attempt.isSubmitted())
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Cannot update answers for a submitted attempt.");

        UserResponse authenticated = userService.getAuthenticatedUser();
        testService.checkTestAccess(testId, authenticated);
        attemptService.checkAttemptAccess(attemptId, authenticated);

        questionAnswerService.updateSavedAnswers(formData, UUID.fromString(attemptUuid));
        return ResponseEntity.ok("Answers saved successfully");
    }

    @GetMapping("/attempts/{attemptUuid}/view")
    public String viewAttemptInfo(
            @PathVariable(name = "attemptUuid") String attemptUuid,
            Model model
    ) {
        UUID attemptId = UUID.fromString(attemptUuid);
        TestAttemptResponse attempt = attemptService.get(attemptId);
        if(!attempt.isSubmitted()) return "redirect:/home";

        List<UUID> answeredQuestions = attempt.getAnswers().stream()
                .map(answer -> answer.getQuestion().getId()).toList();
        Map<UUID, QuestionOptionResponse> questionAnswerMap = new HashMap<>();

        for (QuestionAnswerResponse answer : attempt.getAnswers()) {
            questionAnswerMap.put(answer.getQuestion().getId(), answer.getAnswerOption());
        }

        TestResponse test = testService.getTestByAttempt(attempt.getUuid());
        List<QuestionResponse> questions = questionService.getTestQuestions(test.getUuid());

        UserResponse authenticated = userService.getAuthenticatedUser();

        model.addAttribute("answeredQuestions", answeredQuestions); //ids of questions that were answered
        model.addAttribute("questionAnswerMap", questionAnswerMap); //map question_id -> answered option
        model.addAttribute("questions", questions); //Just questions.
        model.addAttribute("user", authenticated);

        StringBuilder score = new StringBuilder()
                .append(questionAnswerMap.values().stream().filter(QuestionOptionResponse::isCorrect).count())
                .append(" / ")
                .append(questions.size());
        model.addAttribute("score", score.toString());
        model.addAttribute("attemptData", attempt);

        return "attempt-view";
    }

    @PostMapping("/test/{testUuid}/delete")
    public String deleteTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        UUID courseId = testService.get(testId).getSection().getCourse().getUuid();
        testService.delete(testId);

        return "redirect:/course/" + courseId + "?test&deleted";
    }

    @PostMapping("/test/{testUuid}/hide")
    public String hideTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testService.hide(testId);

        return REDIRECT_TO_TEST + testId;
    }

    @PostMapping("/test/{testUuid}/unhide")
    public String unhideTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testService.unhide(testId);

        return REDIRECT_TO_TEST + testId;
    }

    @GetMapping("/test/{testUuid}/edit")
    public String getEditTest(
            @PathVariable(name = "testUuid") String testUuid,
            Model model
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        model.addAttribute("test", testService.get(testId));
        model.addAttribute("user", authenticated);

        return "edit-test";
    }

    @PostMapping("/test/{testUuid}/edit")
    public String postEditTest(
            @PathVariable(name = "testUuid") String testUuid,
            @ModelAttribute TestView testView
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testView.setUuid(testId);
        testService.update(testView);

        return REDIRECT_TO_TEST + testId;
    }
}
