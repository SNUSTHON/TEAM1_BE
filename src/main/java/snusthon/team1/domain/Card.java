package snusthon.team1.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue
    private Long id;
  
    private String content;

    @Relationship(type = "HAS_CHILD", direction = Relationship.Direction.OUTGOING)
    private List<Card> childCards = new ArrayList<>();

    @Relationship(type = "HAS_MEMO", direction = Relationship.Direction.OUTGOING)
    private List<Memo> memos = new ArrayList<>();

    public Card(String content) {
        this.content = content;
    }

    public void addChildCard(Card childCard) {
        this.childCards.add(childCard);
    }

    public void addMemo(Memo memo) {
        this.memos.add(memo);
    }
}