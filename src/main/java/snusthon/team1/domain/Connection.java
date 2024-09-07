package snusthon.team1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Connection {

    @Id
    @GeneratedValue
    private Long id;
    private String type;  // "SIMILAR", "NEUTRAL", "OPPOSITE"

    @Relationship(type = "TO_CARD", direction = Relationship.Direction.OUTGOING)
    private Card toCard;

    public Connection(String type, Card toCard) {
        this.type = type;
        this.toCard = toCard;
    }
}