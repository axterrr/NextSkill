package ukma.springboot.nextskill.modules.test;

import ukma.springboot.nextskill.models.entities.TestEntity;

import java.util.List;
import java.util.UUID;

public interface TestInternalAPI {
    List<TestEntity> getAll();
}
