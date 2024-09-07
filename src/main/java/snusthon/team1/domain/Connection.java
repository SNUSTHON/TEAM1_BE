package snusthon.team1.domain;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Getter
@NoArgsConstructor
public class Connection {

    @Id
    @GeneratedValue
    private String id;
    private String type;  // "SIMILAR", "NEUTRAL", "OPPOSITE"로 구분

    @Relationship(type = "FROM_CARD")
    private Card fromCard;

    @Relationship(type = "TO_CARD")
    private Card toCard;

    public Connection(String type, Card fromCard, Card toCard) {
        this.type = type;
        this.fromCard = fromCard;
        this.toCard = toCard;
    }

    // Getters and Setters
}
