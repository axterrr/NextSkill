package ukma.springboot.nextskill.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.SectionEntity;
import ukma.springboot.nextskill.models.mappers.SectionMapper;
import ukma.springboot.nextskill.models.responses.SectionResponse;
import ukma.springboot.nextskill.models.views.SectionView;
import ukma.springboot.nextskill.repositories.SectionRepository;
import ukma.springboot.nextskill.services.SectionService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private SectionRepository sectionRepository;

    @Override
    public List<SectionResponse> getAll() {
        return sectionRepository.findAll().stream().map(SectionMapper::toSectionResponse).toList();
    }

    @Override
    public SectionResponse get(UUID id) {
        SectionEntity sectionEntity = sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Section", id));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public SectionResponse create(SectionView sectionView) {
        SectionEntity sectionEntity = sectionRepository.save(SectionMapper.toSectionEntity(sectionView));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public SectionResponse update(SectionView sectionView) {
        SectionEntity existingSection = sectionRepository.findById(sectionView.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Section", sectionView.getUuid()));
        SectionEntity sectionEntity = sectionRepository.save(SectionMapper.toSectionEntity(sectionView, existingSection));
        return SectionMapper.toSectionResponse(sectionEntity);
    }

    @Override
    public void delete(UUID id) {
        sectionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Section", id));
        sectionRepository.deleteById(id);
    }
}
