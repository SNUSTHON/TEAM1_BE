package snusthon.team1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snusthon.team1.domain.Canvas;
import snusthon.team1.repository.neo4j.CanvasRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CanvasService {

    private final CanvasRepository canvasRepository;

    @Transactional
    public Canvas createCanvas(String subject, Long userId) {
        Canvas canvas = new Canvas(subject, userId);
        return canvasRepository.save(canvas);
    }

    public List<Canvas> getAllCanvasesByUserId(Long userId) {
        return canvasRepository.findByUserId(userId);
    }

    public Optional<Canvas> getCanvasByIdAndUserId(String canvasId, Long userId) {
        return canvasRepository.findByIdAndUserId(canvasId, userId);
    }

    @Transactional
    public Canvas updateCanvas(String canvasId, String newSubject, Long userId) {
        Optional<Canvas> canvasOpt = canvasRepository.findByIdAndUserId(canvasId, userId);
        if (canvasOpt.isPresent()) {
            Canvas canvas = canvasOpt.get();
            canvas.setSubject(newSubject);
            return canvasRepository.save(canvas);
        }
        return null;
    }

    @Transactional
    public void deleteCanvas(String canvasId, Long userId) {
        canvasRepository.deleteByIdAndUserId(canvasId, userId);
    }

    public List<String> findAllSubjectsByUserId(Long userId) {
        return canvasRepository.findAllSubjectsByUserId(userId);
    }
}