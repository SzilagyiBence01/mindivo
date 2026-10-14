package mindivo.service.impl;

import lombok.AllArgsConstructor;
import mindivo.dto.ContentBlockRequestDto;
import mindivo.dto.ContentBlockResponseDto;
import mindivo.dto.ContentItemDto;
import mindivo.entity.Content;
import mindivo.entity.ContentBlock;
import mindivo.entity.User;
import mindivo.mapper.ContentBlockMapper;
import mindivo.mapper.ContentMapper;
import mindivo.repository.ContentBlockRepository;
import mindivo.repository.UserRepository;
import mindivo.service.ContentBlockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentBlockServiceImpl implements ContentBlockService {

    private final ContentBlockRepository contentBlockRepository;
    private final UserRepository userRepository;

    // Create new Content Block
    @Override
    public ContentBlockResponseDto createContentBlock(ContentBlockRequestDto dto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        ContentBlock contentBlock = ContentBlockMapper.toEntity(dto);

        contentBlock.setAuthor(user);

        ContentBlock savedContentBlock = contentBlockRepository.save(contentBlock);

        return ContentBlockMapper.toResponse(savedContentBlock, userId);
    }

    // Get all Content Block
    @Override
    public List<ContentBlockResponseDto> getAllContentBlocks(Long userId) {
        // Retrieve all Content Blocks from the database,
        // convert each one into a Response DTO, and return them in a list.
        return contentBlockRepository.findAll()
                .stream()
                .map(contentBlock ->
                        ContentBlockMapper.toResponse(
                                contentBlock,
                                userId
                        )
                )
                .toList();
    }

    // Get a single Content Block by id
    @Override
    public ContentBlockResponseDto getContentBlockById(
            Long id,
            Long userId
    ) {

        ContentBlock contentBlock = contentBlockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        return ContentBlockMapper.toResponse(
                contentBlock,
                userId
        );
    }

    @Override
    public List<ContentBlockResponseDto> getMyContentBlocks(Long userId) {

        return contentBlockRepository.findByAuthorId(userId)
                .stream()
                .map(contentBlock ->
                        ContentBlockMapper.toResponse(
                                contentBlock,
                                userId
                        )
                )
                .toList();
    }

    @Transactional
    @Override
    public ContentBlockResponseDto updateContentBlock(Long id, ContentBlockRequestDto dto, Long userId) {

        ContentBlock contentBlock = contentBlockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        // You can only modify your own ContentBlock
        if (!contentBlock.getAuthor().getId().equals(userId)) {
            throw new RuntimeException(
                    "You are not allowed to edit this content block."
            );
        }

        // Title + description update
        ContentBlockMapper.updateEntity(contentBlock, dto);

        // Replacing the entire contents list
        contentBlock.getContents().clear();

        if (dto.getContents() != null) {

            for (int i = 0; i < dto.getContents().size(); i++) {

                ContentItemDto itemDto = dto.getContents().get(i);

                Content content = ContentMapper.toEntity(itemDto);

                content.setPosition(i);
                content.setContentBlock(contentBlock);

                contentBlock.getContents().add(content);
            }
        }

        ContentBlock savedContentBlock =
                contentBlockRepository.save(contentBlock);

        return ContentBlockMapper.toResponse(savedContentBlock, userId);
    }

    @Override
    public void deleteContentBlock(Long id, Long userId) {

        ContentBlock contentBlock = contentBlockRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        // You can only delete your own ContentBlock
        if (!contentBlock.getAuthor().getId().equals(userId)) {
            throw new RuntimeException(
                    "You are not allowed to delete this content block."
            );
        }

        contentBlockRepository.delete(contentBlock);
    }

    @Override
    public void likeContentBlock(Long contentBlockId, Long userId) {

        ContentBlock contentBlock = contentBlockRepository.findById(contentBlockId)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        boolean alreadyLiked = contentBlock.getLikedBy()
                .stream()
                .anyMatch(existingUser ->
                        existingUser.getId().equals(userId)
                );

        // Prevent duplicate likes
        if (!alreadyLiked) {
            contentBlock.getLikedBy().add(user);
        }

        contentBlockRepository.save(contentBlock);
    }

    @Override
    public void unlikeContentBlock(Long contentBlockId, Long userId) {

        ContentBlock contentBlock = contentBlockRepository.findById(contentBlockId)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        contentBlock.getLikedBy().removeIf(
                existingUser ->
                        existingUser.getId().equals(userId)
        );

        contentBlockRepository.save(contentBlock);
    }

    @Override
    public void saveContentBlock(Long contentBlockId, Long userId) {

        ContentBlock contentBlock = contentBlockRepository.findById(contentBlockId)
                .orElseThrow(() ->
                        new RuntimeException("Content block not found.")
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        boolean alreadySaved = user.getSavedContentBlocks()
                .stream()
                .anyMatch(savedContentBlock ->
                        savedContentBlock.getId().equals(contentBlockId)
                );

        if (!alreadySaved) {
            user.getSavedContentBlocks().add(contentBlock);
        }

        userRepository.save(user);
    }

    @Override
    public void unsaveContentBlock(Long contentBlockId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        user.getSavedContentBlocks().removeIf(
                contentBlock ->
                        contentBlock.getId().equals(contentBlockId)
        );

        userRepository.save(user);
    }

    @Override
    public List<ContentBlockResponseDto> getSavedContentBlocks(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        return user.getSavedContentBlocks()
                .stream()
                .map(contentBlock ->
                        ContentBlockMapper.toResponse(
                                contentBlock,
                                userId
                        )
                )
                .toList();
    }

}