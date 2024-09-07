package snusthon.team1.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import snusthon.team1.domain.Card;

public interface CardRepository extends Neo4jRepository<Card, Long> {
}