package snusthon.team1.model.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Relationship;
import snusthon.team1.domain.Card;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    // Card의 ID를 저장하는 필드
    @ElementCollection
    @CollectionTable(name = "user_cards", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "card_id")
    private List<Long> cardIds = new ArrayList<>();

}