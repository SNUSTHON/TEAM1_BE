package snusthon.team1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue
    private Long id;
  
    private String content;

    public Memo(String content) {
        this.content = content;
    }
}