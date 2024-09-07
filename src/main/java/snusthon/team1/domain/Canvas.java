package snusthon.team1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Canvas {

    @Id
    @GeneratedValue
    private String id;
    private String subject;
    private Long userId;

    @Relationship(type = "HAS_CARD")
    private List<Card> cards = new ArrayList<>();

    public Canvas(String subject, Long userId) {
        this.subject = subject;
        this.userId = userId;
    }
}