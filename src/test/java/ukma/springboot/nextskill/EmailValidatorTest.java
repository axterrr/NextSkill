package ukma.springboot.nextskill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ukma.springboot.nextskill.validation.VerifiedEmailValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class EmailValidatorTest {

    @Autowired
    VerifiedEmailValidator emailValidator;

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
            assertFalse(emailValidator.isValid(email, null), "Expected invalid email: " + email);
        }
    }
}
