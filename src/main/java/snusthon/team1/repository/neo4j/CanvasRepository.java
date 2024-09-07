package snusthon.team1.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import snusthon.team1.domain.Canvas;

import java.util.List;
import java.util.Optional;

public interface CanvasRepository extends Neo4jRepository<Canvas, Long> {
    List<Canvas> findByUserId(Long userId);

    Optional<Canvas> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    @Query("MATCH (c:Canvas) WHERE c.userId = $userId RETURN c.subject AS subject")
    List<String> findAllSubjectsByUserId(Long userId);
}