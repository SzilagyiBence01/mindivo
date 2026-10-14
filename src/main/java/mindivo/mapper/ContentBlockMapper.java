package mindivo.mapper;

import mindivo.dto.ContentBlockRequestDto;
import mindivo.dto.ContentBlockResponseDto;
import mindivo.dto.ContentItemDto;
import mindivo.dto.ContentResponseDto;
import mindivo.entity.Content;
import mindivo.entity.ContentBlock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContentBlockMapper {

    // Create new content unit
    public static ContentBlock toEntity(ContentBlockRequestDto dto) {
        ContentBlock contentBlock = new ContentBlock();

        contentBlock.setTitle(dto.getTitle());
        contentBlock.setDescription(dto.getDescription());

        List<Content> contents = new ArrayList<>();

        if (dto.getContents() != null) {
            for (int i = 0; i < dto.getContents().size(); i++) {
                ContentItemDto itemDto = dto.getContents().get(i);

                Content content = ContentMapper.toEntity(itemDto);
                content.setPosition(i);
                content.setContentBlock(contentBlock);

                contents.add(content);
            }
        }

        contentBlock.setContents(contents);

        return contentBlock;
    }

    // Updating the block title and description
    public static void updateEntity(
            ContentBlock contentBlock,
            ContentBlockRequestDto dto
    ) {
        contentBlock.setTitle(dto.getTitle());
        contentBlock.setDescription(dto.getDescription());
    }

    // Converting the content unit back into a response DTO
    public static ContentBlockResponseDto toResponse(
            ContentBlock contentBlock,
            Long userId
    ) {
        List<Content> contents = contentBlock.getContents() == null
                ? new ArrayList<>()
                : new ArrayList<>(contentBlock.getContents());

        contents.sort(
                Comparator.comparing(
                        Content::getPosition,
                        Comparator.nullsLast(
                                Comparator.<Integer>naturalOrder()
                        )
                )
        );

        List<ContentResponseDto> contentDtos = contents.stream()
                .map(ContentMapper::toResponse)
                .toList();

        // Number of likes
        int likeCount = contentBlock.getLikedBy().size();

        // Did the current user like this content block?
        boolean likedByMe = contentBlock.getLikedBy()
                .stream()
                .anyMatch(user ->
                        user.getId().equals(userId)
                );

        return new ContentBlockResponseDto(
                contentBlock.getId(),
                contentBlock.getTitle(),
                contentBlock.getDescription(),
                contentDtos,
                likeCount,
                likedByMe
        );
    }
}