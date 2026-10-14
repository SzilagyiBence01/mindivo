package mindivo.controller;

import lombok.AllArgsConstructor;
import mindivo.dto.ContentBlockRequestDto;
import mindivo.dto.ContentBlockResponseDto;
import mindivo.service.ContentBlockService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/content-blocks")
public class ContentBlockController {

    private final ContentBlockService contentBlockService;

    @PostMapping
    public ContentBlockResponseDto createContentBlock(
            @RequestBody ContentBlockRequestDto dto,
            @AuthenticationPrincipal Jwt jwt
    ) {

        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.createContentBlock(dto, userId);
    }

    @GetMapping
    public List<ContentBlockResponseDto> getAllContentBlocks(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.getAllContentBlocks(userId);
    }

    @GetMapping("/{id}")
    public ContentBlockResponseDto getContentBlockById(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.getContentBlockById(
                id,
                userId
        );
    }

    @GetMapping("/account/content-blocks")
    public List<ContentBlockResponseDto> getMyContentBlocks(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.getMyContentBlocks(userId);
    }

    @PutMapping("/{id}")
    public ContentBlockResponseDto updateContentBlock(
            @PathVariable Long id,
            @RequestBody ContentBlockRequestDto dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.updateContentBlock(
                id,
                dto,
                userId
        );
    }

    @DeleteMapping("/{id}")
    public void deleteContentBlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        contentBlockService.deleteContentBlock(
                id,
                userId
        );
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likeContentBlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        contentBlockService.likeContentBlock(
                id,
                userId
        );
    }

    @DeleteMapping("/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlikeContentBlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        contentBlockService.unlikeContentBlock(
                id,
                userId
        );
    }

    @PostMapping("/{id}/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveContentBlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        contentBlockService.saveContentBlock(id, userId);
    }

    @DeleteMapping("/{id}/save")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsaveContentBlock(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        contentBlockService.unsaveContentBlock(id, userId);
    }

    @GetMapping("/account/saved-content-blocks")
    public List<ContentBlockResponseDto> getSavedContentBlocks(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return contentBlockService.getSavedContentBlocks(userId);
    }
}