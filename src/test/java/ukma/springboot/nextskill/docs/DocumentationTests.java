package ukma.springboot.nextskill.docs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import ukma.springboot.nextskill.NextSkillApplication;

@SpringBootTest
class DocumentationTests {

    @Test
    void generateModulithDocumentation() {
        var modules = ApplicationModules.of(NextSkillApplication.class).verify();

        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }
}
