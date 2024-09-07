package snusthon.team1.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import snusthon.team1.domain.Memo;

public interface MemoRepository extends Neo4jRepository<Memo, Long> {
}