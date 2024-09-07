package snusthon.team1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import snusthon.team1.domain.Memo;
import snusthon.team1.service.MemoService;

import java.util.List;

@RestController
@RequestMapping("/api/canvases/{canvasId}/cards/{cardId}/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 카드에 메모 추가
    @PostMapping
    public ResponseEntity<Memo> addMemoToCard(@PathVariable Long cardId,
                                              @RequestBody String content,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Memo memo = memoService.addMemoToCard(cardId, content);
        return ResponseEntity.ok(memo);
    }

    // 카드의 메모 조회
    @GetMapping
    public ResponseEntity<List<Memo>> getMemoByCard(@PathVariable Long cardId,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        List<Memo> memoList = memoService.getMemoByCard(cardId);  // 메모 리스트를 가져옴
        if (memoList.isEmpty()) {
            return ResponseEntity.noContent().build();  // 메모가 없을 경우 204 No Content 응답
        }
        return ResponseEntity.ok(memoList);  // 메모 리스트 반환
    }

    // 카드의 메모 수정
    @PutMapping
    public ResponseEntity<Memo> updateMemo(@PathVariable Long cardId,
                                           @PathVariable Long memoId,
                                           @RequestBody String content,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Memo updatedMemo = memoService.updateMemo(cardId, memoId, content);
        return ResponseEntity.ok(updatedMemo);
    }

    // 카드의 메모 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteMemo(@PathVariable Long cardId,
                                           @PathVariable Long memoId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        memoService.deleteMemo(cardId, memoId);
        return ResponseEntity.noContent().build();
    }
}