package snusthon.team1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Node
@Getter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue
    private Long id;
    private String content;  // 카드의 내용(주제나 의견)

    @JsonIgnore
    @Relationship(type = "CONNECTED_TO")
    private List<Connection> connections;  // 이 카드에 연결된 카드들의 관계 정보

    @Relationship(type = "HAS_MEMO")
    private List<Memo> memos;  // 카드에 연결된 메모들

    public Card(String content) {
        this.content = content;
        this.connections = new ArrayList<>();
        this.memos = new ArrayList<>();
    }

    public void addConnection(Connection connection) {
        if (this.connections == null) {
            this.connections = new ArrayList<>();
        }
        this.connections.add(connection);
    }

    public void addMemo(Memo memo) {
        if (this.memos == null) {
            this.memos = new ArrayList<>();
        }
        this.memos.add(memo);
    }
}