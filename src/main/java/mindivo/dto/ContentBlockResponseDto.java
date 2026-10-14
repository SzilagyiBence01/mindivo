package mindivo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentBlockResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<ContentResponseDto> contents;

    private Integer likeCount;
    private Boolean likedByMe;
}