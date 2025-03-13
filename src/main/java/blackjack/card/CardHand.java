package blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardHand {

    private static final int BLACKJACK_VALUE = 21;
    private static final int BLACKJACK_CARDS_SIZE = 2;

    private final List<Card> cards;

    public CardHand() {
        this.cards = new ArrayList<>();
    }

    public void addCards(final CardDeck cardDeck, final int count, final int threshold) {
        if (isImpossibleToAdd(threshold)) {
            throw new IllegalArgumentException("더 이상 카드를 추가할 수 없습니다.");
        }

        for (int i = 0; i < count; i++) {
            Card card = cardDeck.pickRandomCard();
            cards.add(card);
        }
    }

    public boolean isBlackjack() {
        return cards.size() == BLACKJACK_CARDS_SIZE
            && calculateDenominations() == BLACKJACK_VALUE;
    }

    public boolean isBust() {
        return calculateDenominations() > BLACKJACK_VALUE;
    }

    public int calculateDenominations() {
        int sum = cards.stream()
            .map(Card::getDenominationNumber)
            .map(List::getFirst)
            .reduce(0, Integer::sum);

        if (hasACE()) {
            return Denomination.convertAceValue(sum, BLACKJACK_VALUE);
        }
        return sum;
    }

    private boolean hasACE() {
        return cards.stream()
            .map(Card::denomination)
            .anyMatch(denomination -> denomination == Denomination.ACE);
    }

    public List<Card> openCards() {
        return Collections.unmodifiableList(cards);
    }

    public List<Card> openInitialCards(int count) {
        return cards.subList(0, Math.min(count, cards.size()));
    }

    public boolean isPossibleToAdd(int threshold) {
        return calculateDenominations() < threshold;
    }

    private boolean isImpossibleToAdd(int threshold) {
        return !isPossibleToAdd(threshold);
    }
}
