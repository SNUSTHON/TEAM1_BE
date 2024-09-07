package snusthon.team1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snusthon.team1.domain.Card;
import snusthon.team1.domain.Canvas;
import snusthon.team1.domain.Connection;
import snusthon.team1.domain.Memo;
import snusthon.team1.repository.neo4j.CardRepository;
import snusthon.team1.repository.neo4j.CanvasRepository;

import java.util.ArrayList;
import java.util.List;

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
        canvas.getCards().add(card);
        canvasRepository.save(canvas);

        return card;
    }

    public List<Card> expandCard(Long cardId, Long userId) {
        Card parentCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        List<String> expandedContents = gptService.generateRelatedTopics(parentCard.getContent());

        List<Card> expandedCards = new ArrayList<>();
        List<String> connectionTypes = List.of("SIMILAR", "NEUTRAL", "OPPOSITE");

        for (int i = 0; i < 3; i++) {
            Card newCard = new Card(expandedContents.get(i));
            Connection connection = new Connection(connectionTypes.get(i), parentCard, newCard);

            parentCard.addConnection(connection);
            expandedCards.add(newCard);
        }

        cardRepository.save(parentCard);
        cardRepository.saveAll(expandedCards);

        return expandedCards;
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
}