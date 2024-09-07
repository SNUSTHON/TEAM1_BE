package snusthon.team1.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import snusthon.team1.domain.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends Neo4jRepository<Card, Long> {
    @Query("MATCH (c:Card)-[r:CONNECTED_TO]->(related:Card) WHERE c.id = $cardId RETURN c, r, related")
    Optional<Card> findByIdWithConnections(Long cardId);

    @Query("MATCH (canvas:Canvas)-[:HAS_CARD]->(card:Card) WHERE canvas.userId = $userId RETURN card")
    List<Card> findAllByUserId(Long userId);
}