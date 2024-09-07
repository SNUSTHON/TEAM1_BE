package snusthon.team1.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import snusthon.team1.domain.Canvas;

import java.util.List;

public interface CanvasRepository extends Neo4jRepository<Canvas, String> {
    // Cypher 쿼리로 subject 필드만 반환
    @Query("MATCH (c:Canvas) RETURN c.subject AS subject")
    List<String> findAllSubjects();
}
