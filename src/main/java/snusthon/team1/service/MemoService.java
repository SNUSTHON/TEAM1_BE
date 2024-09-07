package snusthon.team1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snusthon.team1.domain.Card;
import snusthon.team1.domain.Memo;
import snusthon.team1.repository.neo4j.CardRepository;
import snusthon.team1.repository.neo4j.MemoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final CardRepository cardRepository;
    private final MemoRepository memoRepository;

    // 카드에 메모 추가
    @Transactional
    public Memo addMemoToCard(Long cardId, String content) {
        Optional<Card> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        Card card = cardOpt.get();
        Memo memo = new Memo(content);
        memoRepository.save(memo);  // 메모를 먼저 저장

        card.getMemo().add(memo);  // 카드의 메모 리스트에 추가
        cardRepository.save(card);  // 카드 저장

        return memo;
    }

    // 카드의 모든 메모 조회
    public List<Memo> getMemoByCard(Long cardId) {
        Optional<Card> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        return cardOpt.get().getMemo();  // 메모 리스트 반환
    }

    // 특정 메모 수정
    @Transactional
    public Memo updateMemo(Long cardId, Long memoId, String content) {
        Optional<Card> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        Card card = cardOpt.get();
        Optional<Memo> memoOpt = card.getMemo().stream().filter(m -> m.getId().equals(memoId)).findFirst();

        if (memoOpt.isEmpty()) {
            throw new IllegalArgumentException("Memo not found");
        }

        Memo memo = memoOpt.get();
        memo.setContent(content);
        memoRepository.save(memo);

        return memo;
    }

    // 특정 메모 삭제
    @Transactional
    public void deleteMemo(Long cardId, Long memoId) {
        Optional<Card> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        Card card = cardOpt.get();
        card.getMemo().removeIf(m -> m.getId().equals(memoId));  // 메모 리스트에서 삭제
        cardRepository.save(card);  // 카드 저장

        memoRepository.deleteById(memoId);  // 메모 삭제
    }
}