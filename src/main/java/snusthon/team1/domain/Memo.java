package snusthon.team1.domain;

import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;


@Node
@Getter
@Setter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue
    private String id;
    private String content;  // 메모 내용

    public Memo(String content) {
        this.content = content;
    }
}
