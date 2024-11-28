package ukma.springboot.nextskill.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.QuestionResponse;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.QuestionView;
import ukma.springboot.nextskill.services.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class QuestionController {

    private static final String MANAGE_QUESTION = "/manage-question";
    private TestService testService;
    private UserService userService;
    private QuestionService questionService;
    private TestAttemptService attemptService;

    @PostMapping("/question/{questionUuid}/delete")
    public String deleteQuestion(@PathVariable(name = "questionUuid") String questionUuid
    ) {
        UUID questionId = UUID.fromString(questionUuid);
        TestResponse associatedTest = testService.getTestByQuestion(questionId);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), associatedTest.getUuid());
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return TestController.REDIRECT_TO_TEST + associatedTest.getUuid();

        attemptService.removeAllWithTest(associatedTest.getUuid());
        questionService.delete(questionId);

        return TestController.REDIRECT_TO_TEST + associatedTest.getUuid() + MANAGE_QUESTION;
    }

    @PostMapping("/question/{questionUuid}/edit")
    public String updateQuestion(
            @PathVariable(name = "questionUuid") String questionUuid,
            @ModelAttribute QuestionView questionView
    ) {
        UUID questionId = UUID.fromString(questionUuid);
        TestResponse associatedTest = testService.getTestByQuestion(questionId);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), associatedTest.getUuid());
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return TestController.REDIRECT_TO_TEST + associatedTest.getUuid();

        questionService.update(questionView);

        return TestController.REDIRECT_TO_TEST + associatedTest.getUuid() + MANAGE_QUESTION;
    }

    @PostMapping("/question/add")
    public String createQuestion(
            @ModelAttribute QuestionView questionView
    ) {
        TestResponse associatedTest = testService.get(questionView.getTestId());

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), associatedTest.getUuid());
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return TestController.REDIRECT_TO_TEST + associatedTest.getUuid();

        QuestionView view = QuestionView.builder()
                .questionText(questionView.getQuestionText())
                .testId(associatedTest.getUuid())
                .build();

        questionService.create(view);

        return TestController.REDIRECT_TO_TEST + associatedTest.getUuid() + MANAGE_QUESTION;
    }

    @GetMapping("/question/{questionUuid}/manage-options")
    public String getManageOptionsView(
            @PathVariable(name = "questionUuid") String questionUuid,
            Model model
    ) {
        UUID questionId = UUID.fromString(questionUuid);
        QuestionResponse questionResponse = questionService.get(questionId);
        TestResponse associatedTest = testService.getTestByQuestion(questionId);

        UserResponse authenticated = userService.getAuthenticatedUser();
        boolean isOwner = testService.hasOwnerRights(authenticated.getUuid(), associatedTest.getUuid());
        if(!isOwner && authenticated.getRole() != UserRole.ADMIN)
            return TestController.REDIRECT_TO_TEST + associatedTest.getUuid();

        model.addAttribute("question", questionResponse);
        model.addAttribute("options", questionResponse.getQuestionOptions());
        model.addAttribute("user", authenticated);

        return "manage-options";
    }
}
