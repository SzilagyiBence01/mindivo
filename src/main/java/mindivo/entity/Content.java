package mindivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="content")
public class Content {
    // Content ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Position of the content block from top to bottom
    @Column(name = "position")
    private Integer position;

    // JSON formated, content type specified
    @Column(name = "content_data", columnDefinition = "JSON")
    private String contentData;

    // Content type enum (TEXT, QUIZ, IMAGE)
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    // cb id, connection
    @ManyToOne
    @JoinColumn(name = "content_block_id")
    private ContentBlock contentBlock;

    //
    //
    public enum ContentType{
        TEXT,
        QUIZ,
        IMAGE // ,VIDEO ,(CODE) in later updates
    }

}
