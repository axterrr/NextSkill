package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.modules.answer.AnswerExternalAPI;
import ukma.springboot.nextskill.modules.attempt.AttemptExternalAPI;
import ukma.springboot.nextskill.exceptions.MaxAttemptsException;
import ukma.springboot.nextskill.models.entities.QuestionEntity;
import ukma.springboot.nextskill.models.entities.TestAttemptEntity;
import ukma.springboot.nextskill.models.entities.TestEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.*;
import ukma.springboot.nextskill.models.views.TestView;
import ukma.springboot.nextskill.modules.question.QuestionExternalAPI;
import ukma.springboot.nextskill.modules.section.SectionExternalAPI;
import ukma.springboot.nextskill.modules.test.TestExternalAPI;
import ukma.springboot.nextskill.modules.user.UserExternalAPI;

import java.util.*;

@Controller
@AllArgsConstructor
public class TestController {

    public static final String REDIRECT_TO_TEST = "redirect:/test/";
    private static final String REDIRECT_TO_HOME = "redirect:/home";
    private static final String QUESTIONS = "questions";
    private AnswerExternalAPI answerExternalAPI;
    private TestExternalAPI testExternalAPI;
    private UserExternalAPI userExternalAPI;
    private AttemptExternalAPI attemptExternalAPI;
    private QuestionExternalAPI questionExternalAPI;
    private SectionExternalAPI sectionExternalAPI;

    @GetMapping("/test/{uuid}")
    public String testInfo(@PathVariable String uuid, Model model) {
        UserResponse authenticatedUser = userExternalAPI.getAuthenticatedUser();
        if (authenticatedUser.getRole() != UserRole.ADMIN){
            testExternalAPI.checkTestAccess(UUID.fromString(uuid), authenticatedUser);
        }

        TestEntity test = testExternalAPI.get(UUID.fromString(uuid));
        model.addAttribute("test", test);

        boolean hasOwnerRights = testExternalAPI.hasOwnerRights(authenticatedUser.getUuid(), test.getUuid());
        model.addAttribute("isOwner", hasOwnerRights);
        model.addAttribute("user", authenticatedUser);

        List<TestAttemptEntity> userAttempts = test.getAttempts().stream().filter(
                attempt -> attempt.getCompletedBy().getUuid().equals(authenticatedUser.getUuid())
        ).toList();
        List<TestAttemptResponse> finishedAttempts =
                attemptExternalAPI.getFinishedAttempts(test.getUuid(), authenticatedUser.getUuid());

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
        UserResponse authenticatedUser = userExternalAPI.getAuthenticatedUser();
        testExternalAPI.checkTestAccess(UUID.fromString(testUuid), authenticatedUser);

        UUID testId = UUID.fromString(testUuid);
        UUID userId = authenticatedUser.getUuid();

        Optional<TestAttemptResponse> unfinishedAttempt = attemptExternalAPI.getUnfinishedAttempt(testId, userId);
        if (unfinishedAttempt.isPresent()) {
            return REDIRECT_TO_TEST + testUuid + "/attempt/" + unfinishedAttempt.get().getUuid();
        }

        List<TestAttemptResponse> finishedAttempts = attemptExternalAPI.getFinishedAttempts(testId, userId);
        if (!finishedAttempts.isEmpty()) throw new MaxAttemptsException();

        TestAttemptResponse newAttempt = attemptExternalAPI.createNewAttempt(testId, userId);

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

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        testExternalAPI.checkTestAccess(testId, authenticated);
        attemptExternalAPI.checkAttemptAccess(attemptId, authenticated);

        TestAttemptResponse attempt = attemptExternalAPI.get(attemptId);
        List<QuestionResponse> questions = questionExternalAPI.getTestQuestions(testId);

        List<UUID> answeredOptions = attempt.getAnswers().stream()
                .map(answer -> answer.getAnswerOption().getId()).toList();

        model.addAttribute("user", authenticated);
        model.addAttribute("answers", answeredOptions);
        model.addAttribute(QUESTIONS, questions);
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

        TestAttemptResponse attempt = attemptExternalAPI.get(attemptId);
        if(attempt.isSubmitted())
            return REDIRECT_TO_TEST + testUuid;

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        testExternalAPI.checkTestAccess(testId, authenticated);
        attemptExternalAPI.checkAttemptAccess(attemptId, authenticated);

        answerExternalAPI.updateSavedAnswers(formData, UUID.fromString(attemptUuid));
        attemptExternalAPI.submitAttempt(attemptId);

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

        TestAttemptResponse attempt = attemptExternalAPI.get(attemptId);
        if(attempt.isSubmitted())
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Cannot update answers for a submitted attempt.");

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        testExternalAPI.checkTestAccess(testId, authenticated);
        attemptExternalAPI.checkAttemptAccess(attemptId, authenticated);

        answerExternalAPI.updateSavedAnswers(formData, UUID.fromString(attemptUuid));
        return ResponseEntity.ok("Answers saved successfully");
    }

    @GetMapping("/attempts/{attemptUuid}/view")
    public String viewAttemptInfo(
            @PathVariable(name = "attemptUuid") String attemptUuid,
            Model model
    ) {
        UUID attemptId = UUID.fromString(attemptUuid);
        TestAttemptResponse attempt = attemptExternalAPI.get(attemptId);
        if(!attempt.isSubmitted()) return REDIRECT_TO_HOME;

        TestResponse test = testExternalAPI.getTestByAttempt(attempt.getUuid());
        List<QuestionResponse> questions = questionExternalAPI.getTestQuestions(test.getUuid());

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), test.getUuid());
        if (!attempt.getCompletedBy().getUuid().equals(authenticated.getUuid())
            && !isOwner && authenticated.getRole() != UserRole.ADMIN) {
                return REDIRECT_TO_TEST + test.getUuid();
        }

        List<UUID> answeredQuestions = attempt.getAnswers().stream()
                .map(answer -> answer.getQuestion().getId()).toList();
        Map<UUID, QuestionOptionResponse> questionAnswerMap = new HashMap<>();

        for (QuestionAnswerResponse answer : attempt.getAnswers()) {
            questionAnswerMap.put(answer.getQuestion().getId(), answer.getAnswerOption());
        }

        model.addAttribute("answeredQuestions", answeredQuestions); //ids of questions that were answered
        model.addAttribute("questionAnswerMap", questionAnswerMap); //map question_id -> answered option
        model.addAttribute(QUESTIONS, questions); //Just questions.
        model.addAttribute("user", authenticated);

        StringBuilder score = new StringBuilder()
                .append(questionAnswerMap.values().stream().filter(QuestionOptionResponse::isCorrect).count())
                .append(" / ")
                .append(questions.size());
        model.addAttribute("score", score.toString());
        model.addAttribute("attemptData", attempt);

        return "attempt-view";
    }

    @GetMapping("test/{testId}/all-attempts")
    public String getAllAttempts(
            @PathVariable(name = "testId") UUID testId,
            Model model
    ) {
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        TestEntity testResponse = testExternalAPI.get(testId);

        if ( authenticated.getRole() != UserRole.ADMIN &&
                !testResponse.getSection().getCourse().getTeacher().getUuid().equals(authenticated.getUuid())
        ) {
            return REDIRECT_TO_HOME;
        }

        model.addAttribute("user", authenticated);
        model.addAttribute("attempts", testResponse.getAttempts());

        return "all-attempts";
    }

    @PostMapping("/attempt/{attemptId}/delete")
    public String deleteAttempt(
            @PathVariable(name = "attemptId") UUID attemptId
    ) {
        TestResponse test = testExternalAPI.getTestByAttempt(attemptId);
        attemptExternalAPI.delete(attemptId);
        return REDIRECT_TO_TEST + test.getUuid() + "/all-attempts";
    }

    @PostMapping("test/create")
    public String createTest(
            @ModelAttribute TestView testView
    ) {
        SectionResponse associatedSection = sectionExternalAPI.get(testView.getSectionId());
        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();

        if ( authenticated.getRole() != UserRole.ADMIN &&
                !associatedSection.getCourse().getTeacher().getUuid().equals(authenticated.getUuid())
        ) {
            return REDIRECT_TO_HOME;
        }

        TestView view = TestView.builder()
                .sectionId(testView.getSectionId())
                .description(testView.getDescription())
                .name(testView.getName())
                .isHidden(testView.isHidden())
                .build();

        TestResponse res = testExternalAPI.create(view);

        return REDIRECT_TO_TEST + res.getUuid();
    }

    @PostMapping("/test/{testUuid}/delete")
    public String deleteTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        UUID courseId = testExternalAPI.get(testId).getSection().getCourse().getUuid();
        testExternalAPI.delete(testId);

        return "redirect:/course/" + courseId + "?test&deleted";
    }

    @PostMapping("/test/{testUuid}/hide")
    public String hideTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testExternalAPI.hide(testId);

        return REDIRECT_TO_TEST + testId;
    }

    @PostMapping("/test/{testUuid}/unhide")
    public String unhideTest(
            @PathVariable(name = "testUuid") String testUuid
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testExternalAPI.unhide(testId);

        return REDIRECT_TO_TEST + testId;
    }

    @GetMapping("/test/{testUuid}/edit")
    public String getEditTest(
            @PathVariable(name = "testUuid") String testUuid,
            Model model
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        model.addAttribute("test", testExternalAPI.get(testId));
        model.addAttribute("user", authenticated);

        return "edit-test";
    }

    @PostMapping("/test/{testUuid}/edit")
    public String postEditTest(
            @PathVariable(name = "testUuid") String testUuid,
            @ModelAttribute TestView testView
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        testView.setUuid(testId);
        testExternalAPI.update(testView);

        return REDIRECT_TO_TEST + testId;
    }

    @GetMapping("/test/{testUuid}/manage-questions")
    public String getQuestionsManageView(
            @PathVariable(name = "testUuid") String testUuid,
            Model model
    ) {
        UUID testId = UUID.fromString(testUuid);

        UserResponse authenticated = userExternalAPI.getAuthenticatedUser();
        boolean isOwner = testExternalAPI.hasOwnerRights(authenticated.getUuid(), testId);
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return REDIRECT_TO_TEST + testId;

        TestEntity test = testExternalAPI.get(testId);
        List<QuestionEntity> questionResponses = test.getQuestions();

        model.addAttribute("test", test);
        model.addAttribute(QUESTIONS, questionResponses);
        model.addAttribute("user", authenticated);

        return "manage-questions";
    }
}
