package mindivo.mapper;

import mindivo.dto.ContentItemDto;
import mindivo.dto.ContentResponseDto;
import mindivo.entity.Content;

public class ContentMapper {

    public static Content toEntity(ContentItemDto dto) {
        Content content = new Content();

        content.setContentType(dto.getContentType());
        content.setContentData(dto.getContentData());

        return content;
    }

    public static void updateEntity(
            Content content,
            ContentItemDto dto
    ) {
        content.setContentType(dto.getContentType());
        content.setContentData(dto.getContentData());
    }

    public static ContentResponseDto toResponse(Content content) {
        return new ContentResponseDto(
                content.getId(),
                content.getPosition(),
                content.getContentType(),
                content.getContentData()
        );
    }
}