package snusthon.team1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import snusthon.team1.domain.Canvas;
import snusthon.team1.service.CanvasService;
import snusthon.team1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/canvases")
@RequiredArgsConstructor
public class CanvasController {

    private final CanvasService canvasService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Canvas> createCanvas(@RequestParam String subject, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        Canvas canvas = canvasService.createCanvas(subject, userId);
        return ResponseEntity.ok(canvas);
    }

    @GetMapping
    public ResponseEntity<List<Canvas>> getAllCanvases(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        List<Canvas> canvases = canvasService.getAllCanvasesByUserId(userId);
        return ResponseEntity.ok(canvases);
    }

    @GetMapping("/{canvasId}")
    public ResponseEntity<Canvas> getCanvas(@PathVariable Long canvasId, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        return canvasService.getCanvasByIdAndUserId(canvasId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{canvasId}")
    public ResponseEntity<Canvas> updateCanvas(@PathVariable Long canvasId, @RequestParam String newSubject, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        Canvas updatedCanvas = canvasService.updateCanvas(canvasId, newSubject, userId);
        return updatedCanvas != null ? ResponseEntity.ok(updatedCanvas) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{canvasId}")
    public ResponseEntity<Void> deleteCanvas(@PathVariable Long canvasId, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        canvasService.deleteCanvas(canvasId, userId);
        return ResponseEntity.noContent().build();
    }
}