package snusthon.team1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import snusthon.team1.domain.Canvas;
import snusthon.team1.service.CanvasService;

import java.util.List;

@RestController
@RequestMapping("/api/canvas")
@RequiredArgsConstructor
public class CanvasController {

    private final CanvasService canvasService;

    // Canvas 생성
    @PostMapping
    public ResponseEntity<Canvas> createCanvas(@RequestParam String subject) {
        Canvas canvas = canvasService.createCanvas(subject);
        return ResponseEntity.ok(canvas);
    }

    // 모든 Canvas의 주제(subject)만 조회
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllCanvasSubjects() {
        List<String> subjects = canvasService.findAllSubjects();
        return ResponseEntity.ok(subjects);
    }
}