package ukma.springboot.nextskill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ukma.springboot.nextskill.models.responses.TestAttemptResponse;
import ukma.springboot.nextskill.models.responses.TestResponse;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.services.TestService;
import ukma.springboot.nextskill.services.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
public class TestController {

    TestService testService;
    UserService userService;

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
    public String doAttempt(@PathVariable String uuid, Model model) {
        return "";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }
}
