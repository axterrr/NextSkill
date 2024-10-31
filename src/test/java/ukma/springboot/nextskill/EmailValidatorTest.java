package ukma.springboot.nextskill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ukma.springboot.nextskill.validation.IValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
public class EmailValidatorTest {

    @Autowired
    IValidator<String> emailValidator;

    @Test
    void testExternalEmailValidator() {
        String[] invalidEmails = {
                "plainaddress",
                "@missingusername.com",
                "username@.com",
                "username@domain..com",
                ".username@domain.com",
                "aghebdfs@danndf.sdg",
                "non.existing.email.asg245@gmail.com"
        };

        for (String email : invalidEmails) {
            assertFalse(emailValidator.isValid(email), "Expected invalid email: " + email);
        }
    }
}
