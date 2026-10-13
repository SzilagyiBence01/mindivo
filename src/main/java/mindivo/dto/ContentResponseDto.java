package mindivo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mindivo.entity.Content.ContentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDto {
    private Long id;
    private Integer position;
    private ContentType contentType;
    private String contentData;
}