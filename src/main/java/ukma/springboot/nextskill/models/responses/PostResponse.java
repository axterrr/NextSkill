package ukma.springboot.nextskill.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PostResponse {
    private UUID uuid;
    private String name;
    private LocalDateTime createdAt;
    private boolean isHidden;
    private String content;
    private SectionResponse section;
}
