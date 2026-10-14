package mindivo.service;

import mindivo.dto.ContentBlockRequestDto;
import mindivo.dto.ContentBlockResponseDto;
import mindivo.entity.ContentBlock;

import java.util.List;

public interface ContentBlockService {

    ContentBlockResponseDto createContentBlock(ContentBlockRequestDto dto, Long userId);

    List<ContentBlockResponseDto> getAllContentBlocks (Long userId);

    ContentBlockResponseDto getContentBlockById(Long id, Long userId);

    List<ContentBlockResponseDto> getMyContentBlocks(Long userId);

    ContentBlockResponseDto updateContentBlock(Long id, ContentBlockRequestDto dto, Long userId);

    void deleteContentBlock(Long id, Long userId);

    void likeContentBlock(Long contentBlockId, Long userId);

    void unlikeContentBlock(Long contentBlockId, Long userId);

    void saveContentBlock(Long contentBlockId, Long userId);

    void unsaveContentBlock(Long contentBlockId, Long userId);

    List<ContentBlockResponseDto> getSavedContentBlocks(Long userId);
}