package ukma.springboot.nextskill.docs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import ukma.springboot.nextskill.NextSkillApplication;

@SpringBootTest
class DocumentationTests {
    static ApplicationModules modules = ApplicationModules.of(NextSkillApplication.class);

    @Test
    void shouldBeCompliant() {
        modules.verify();
    }

    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules)
                .writeModuleCanvases()
                .writeModulesAsPlantUml()
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }
}
