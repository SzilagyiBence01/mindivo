package mindivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content_block")
public class ContentBlock {
    // Content Block ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Content Block title
    @Column(name =  "title")
    private String title;

    // Short lesson description displayed on the main screen
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    // Content elements that make up the lesson
    @OneToMany(mappedBy = "contentBlock")
    private List<Content> contents;

    // Users who liked the post
    @ManyToMany
    @JoinTable(
            name = "content_block_likes",
            joinColumns = @JoinColumn(name = "content_block_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedBy;
}
