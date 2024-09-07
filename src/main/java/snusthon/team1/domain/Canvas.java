package snusthon.team1.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Canvas {

    @Id
    @GeneratedValue
    private String id;  // UUID나 다른 식별자 사용
    private String subject;

    @Relationship(type = "HAS_CARD")
    private List<Card> cards = new ArrayList<>();;

    public Canvas(String subject, List<Card> cards) {
        this.subject = subject;
        this.cards = cards;
    }

    // Getters and Setters
}
