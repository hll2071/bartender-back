package bartender.bartenderback.user.domain;

import bartender.bartenderback.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    public static User of(UserRequest request) {
        return new User(request.getEmail(), request.getName());
    }

    User (String email, String name) {
        this.email = email;
        this.name = name;
    }
}
