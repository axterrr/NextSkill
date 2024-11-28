package ukma.springboot.nextskill.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private UUID uuid;
    private String name;
    private LocalDateTime createdAt;
    private boolean isHidden;
    private String content;
    private SectionResponse section;
}
