package snusthon.team1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import snusthon.team1.domain.Card;
import snusthon.team1.domain.Memo;
import snusthon.team1.service.CardService;
import snusthon.team1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @PostMapping("/canvas/{canvasId}")
    public ResponseEntity<Card> createMainCard(@PathVariable Long canvasId,
                                               @RequestParam String content,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        Card card = cardService.createMainCard(canvasId, content, userId);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{cardId}/expand")
    public ResponseEntity<List<Card>> expandCard(@PathVariable Long cardId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        List<Card> expandedCards = cardService.expandCard(cardId, userId);
        return ResponseEntity.ok(expandedCards);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCard(@PathVariable Long cardId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        Card card = cardService.getCard(cardId, userId);
        return ResponseEntity.ok(card);
    }

    // 메모 추가
    @PostMapping("/{cardId}/memos")
    public ResponseEntity<Memo> addMemoToCard(@PathVariable Long cardId,
                                              @RequestParam String content,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        Memo memo = cardService.addMemoToCard(cardId, content, userId);
        return ResponseEntity.ok(memo);
    }

    // 메모 조회
    @GetMapping("/{cardId}/memos")
    public ResponseEntity<List<Memo>> getMemosForCard(@PathVariable Long cardId,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        List<Memo> memos = cardService.getMemosForCard(cardId, userId);
        return ResponseEntity.ok(memos);
    }

    // 메모 수정
    @PutMapping("/{cardId}/memos/{memoId}")
    public ResponseEntity<Memo> updateMemo(@PathVariable Long cardId,
                                           @PathVariable Long memoId,
                                           @RequestParam String content) {
        Memo updatedMemo = cardService.updateMemo(cardId, memoId, content);
        return ResponseEntity.ok(updatedMemo);
    }

    // 메모 삭제
    @DeleteMapping("/{cardId}/memos/{memoId}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long cardId,
                                           @PathVariable Long memoId) {
        cardService.deleteMemo(cardId, memoId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }


}