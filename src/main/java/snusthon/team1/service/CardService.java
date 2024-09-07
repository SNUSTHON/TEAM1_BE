package snusthon.team1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snusthon.team1.domain.Card;
import snusthon.team1.domain.Canvas;
import snusthon.team1.domain.Memo;
import snusthon.team1.repository.neo4j.CardRepository;
import snusthon.team1.repository.neo4j.CanvasRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final CanvasRepository canvasRepository;
    private final GptService gptService;

    public Card createMainCard(Long canvasId, String content, Long userId) {
        Canvas canvas = canvasRepository.findByIdAndUserId(canvasId, userId)
                .orElseThrow(() -> new RuntimeException("Canvas not found"));

        Card card = new Card(content);
        canvas.setRootCard(card);
        cardRepository.save(card);
        canvasRepository.save(canvas);
        return card;
    }

    public List<Card> expandCard(Long cardId, Long userId) {
        Card parentCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        List<String> expandedContents = gptService.generateRelatedTopics(parentCard.getContent());

        List<Card> expandedCards = new ArrayList<>();
        for (String content : expandedContents) {
            Card newCard = new Card(content);
            parentCard.addChildCard(newCard);
            expandedCards.add(newCard);
        }

        cardRepository.save(parentCard);
        return cardRepository.saveAll(expandedCards);
    }

    public Card getCard(Long cardId, Long userId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Memo addMemoToCard(Long cardId, String memoContent, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Memo memo = new Memo(memoContent);
        card.addMemo(memo);
        cardRepository.save(card);

        return memo;
    }

    public List<Memo> getMemosForCard(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return card.getMemos();
    }

    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        deleteCardRecursively(card);
    }

    private void deleteCardRecursively(Card card) {
        if (card.getChildCards() != null) {
            for (Card childCard : card.getChildCards()) {
                deleteCardRecursively(childCard);
            }
        }
        cardRepository.delete(card);
    }

    // 메모 수정
    public Memo updateMemo(Long cardId, Long memoId, String content) {
        // 카드가 존재하는지 확인
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        // 해당 카드에 속한 메모가 존재하는지 확인
        Optional<Memo> memo = card.get().getMemos().stream()
                .filter(m -> m.getId().equals(memoId))
                .findFirst();

        if (memo.isEmpty()) {
            throw new IllegalArgumentException("Memo not found");
        }

        // 메모 업데이트
        Memo memoToUpdate = memo.get();
        memoToUpdate.setContent(content);

        cardRepository.save(card.get());

        return memoToUpdate;
    }

    // 메모 삭제
    @Transactional
    public void deleteMemo(Long cardId, Long memoId) {
        // 카드가 존재하는지 확인
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }

        // 해당 카드에 속한 메모가 존재하는지 확인
        Optional<Memo> memo = card.get().getMemos().stream()
                .filter(m -> m.getId().equals(memoId))
                .findFirst();

        if (memo.isEmpty()) {
            throw new IllegalArgumentException("Memo not found");
        }

        // 메모 삭제
        card.get().getMemos().remove(memo.get());

        // 카드 저장 (메모 삭제가 반영된 상태로)
        cardRepository.save(card.get());
    }
}