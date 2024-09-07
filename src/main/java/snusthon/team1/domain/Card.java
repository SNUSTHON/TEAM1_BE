package snusthon.team1.domain;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Getter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue
    private Long id;  // UUID
    private String content;  // 카드의 내용(주제나 의견)

    @Relationship(type = "CONNECTED_TO")
    private List<Connection> connections;  // 이 카드에 연결된 카드들의 관계 정보

    @Relationship(type = "HAS_MEMO")
    private List<Memo> memo;  // 카드에 연결된 메모

    // Getters and Setters


}