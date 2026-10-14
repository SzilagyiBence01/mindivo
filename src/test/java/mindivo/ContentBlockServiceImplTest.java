package mindivo;

import mindivo.dto.ContentBlockRequestDto;
import mindivo.dto.ContentBlockResponseDto;
import mindivo.entity.ContentBlock;
import mindivo.entity.User;
import mindivo.repository.ContentBlockRepository;
import mindivo.repository.UserRepository;
import mindivo.service.impl.ContentBlockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentBlockServiceImplTest {

    @Mock
    private ContentBlockRepository contentBlockRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContentBlockServiceImpl contentBlockService;

    @Test
    void createContentBlock() {
        User user = new User();
        user.setId(1L);

        ContentBlockRequestDto dto = new ContentBlockRequestDto();
        dto.setTitle("Java alapok");
        dto.setDescription("Java tananyag");

        ContentBlock contentBlock = new ContentBlock();
        contentBlock.setId(1L);
        contentBlock.setTitle("Java alapok");
        contentBlock.setDescription("Java tananyag");
        contentBlock.setAuthor(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(contentBlockRepository.save(any(ContentBlock.class))).thenReturn(contentBlock);

        ContentBlockResponseDto result = contentBlockService.createContentBlock(dto, 1L);

        assertNotNull(result);
        assertEquals("Java alapok", result.getTitle());
    }

    @Test
    void getContentBlockById() {
        User user = new User();
        user.setId(1L);

        ContentBlock contentBlock = new ContentBlock();
        contentBlock.setId(1L);
        contentBlock.setTitle("Java alapok");
        contentBlock.setDescription("Java tananyag");
        contentBlock.setAuthor(user);

        when(contentBlockRepository.findById(1L)).thenReturn(Optional.of(contentBlock));

        ContentBlockResponseDto result = contentBlockService.getContentBlockById(1L, 1L);

        assertNotNull(result);
        assertEquals("Java alapok", result.getTitle());
    }

    @Test
    void cannotUpdateOtherUsersContentBlock() {
        User owner = new User();
        owner.setId(1L);

        ContentBlock contentBlock = new ContentBlock();
        contentBlock.setId(1L);
        contentBlock.setAuthor(owner);

        ContentBlockRequestDto dto = new ContentBlockRequestDto();

        when(contentBlockRepository.findById(1L)).thenReturn(Optional.of(contentBlock));

        assertThrows(RuntimeException.class, () ->
                contentBlockService.updateContentBlock(1L, dto, 2L)
        );
    }

    @Test
    void likeContentBlock() {
        User user = new User();
        user.setId(1L);

        ContentBlock contentBlock = new ContentBlock();
        contentBlock.setId(1L);

        when(contentBlockRepository.findById(1L)).thenReturn(Optional.of(contentBlock));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        contentBlockService.likeContentBlock(1L, 1L);

        assertEquals(1, contentBlock.getLikedBy().size());
    }
}