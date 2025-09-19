package ukma.springboot.nextskill.course.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.common.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.mappers.SectionMapper;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.course.repository.SectionRepository;
import ukma.springboot.nextskill.course.service.SectionService;
import ukma.springboot.nextskill.course.validation.SectionValidator;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private static final String SECTION = "Section";
    private SectionRepository sectionRepository;
    private SectionValidator sectionValidator;

    @Override
    public List<SectionResponse> getAll() {
        return sectionRepository.findAll().stream().map(SectionMapper::toSectionResponse).toList();
    }

    @Override
    public SectionResponse get(UUID id) {
        SectionEntity sectionEntity = sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SECTION, id));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public SectionResponse create(SectionView sectionView) {
        sectionValidator.validateForCreation(sectionView);
        SectionEntity sectionEntity = sectionRepository.save(SectionMapper.toSectionEntity(sectionView));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public SectionResponse update(SectionView sectionView) {
        sectionValidator.validateForUpdate(sectionView);
        SectionEntity existingSection = sectionRepository.findById(sectionView.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException(SECTION, sectionView.getUuid()));
        SectionEntity sectionEntity = sectionRepository.save(SectionMapper.toSectionEntity(sectionView, existingSection));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public void delete(UUID id) {
        if (sectionRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(SECTION, id);
        }
        sectionRepository.deleteById(id);
    }
}
