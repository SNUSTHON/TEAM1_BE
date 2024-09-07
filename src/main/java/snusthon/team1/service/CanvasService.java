package snusthon.team1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snusthon.team1.domain.Canvas;
import snusthon.team1.repository.neo4j.CanvasRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CanvasService {

    private final CanvasRepository canvasRepository;


    // 처음 주제를 정하는 메서드
    @Transactional
    public Canvas createCanvas(String subject) {

        // 빈 카드 리스트로 Canvas 생성
        Canvas canvas = new Canvas(subject, new ArrayList<>());

        // Neo4j에 Canvas 저장
        return canvasRepository.save(canvas);
    }

    // 모든 Canvas의 주제만 조회
    public List<String> findAllSubjects() {
        return canvasRepository.findAllSubjects();
    }



}
