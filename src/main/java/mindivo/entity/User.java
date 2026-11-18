
package mindivo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    // User ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    @Column(name = "full_name")
    private String fullName;

    // Unique user name (nickname)
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    // Hashed password
    @Column(name = "password", nullable = false)
    private String password;

    // User email address
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    // Profile picture URL
    @Column(name = "profile_picture_url", length = 2048)
    private String profilePictureUrl;

    // User biography
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    // User own content blocks
    @OneToMany(mappedBy = "author")
    private List<ContentBlock> createdContentBlocks;

    // Content blocks saved by the user to view later
    @ManyToMany
    @JoinTable(
            name = "saved_content_blocks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "content_block_id")
    )
    private List<ContentBlock> savedContentBlocks = new ArrayList<>();
}
