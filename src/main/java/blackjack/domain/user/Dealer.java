package blackjack.domain.user;

import blackjack.domain.Card;
import blackjack.domain.GameResult;
import java.util.List;

public class Dealer extends Participant {

    private static final int DEALER_DISTRIBUTE_CARD_THRESHOLD = 16;

    public Dealer() {
        super();
    }

    @Override
    public List<Card> openInitialCards() {
        return List.of(super.cards.getFirst());
    }

    @Override
    public boolean isPossibleToAdd() {
        return super.calculateDenominations() <= DEALER_DISTRIBUTE_CARD_THRESHOLD;
    }

    public GameResult judgePlayerResult(final Player player) {
        if (player.isBust()) {
            return GameResult.LOSE;
        }
        if (player.isBlackjack() && super.isBlackjack()) {
            return GameResult.DRAW;
        }
        if (player.isBlackjack() || super.isBust()) {
            return GameResult.WIN;
        }
        return GameResult.FromDenominationsSum(this, player);
    }
}
