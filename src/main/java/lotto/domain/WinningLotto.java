package lotto.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WinningLotto {
    private static final int DEFAULT_WINNING_TICKET_SIZE = 0;
    private static final int WINNING_AMOUNT = 1;

    private Lotto lottoNumbers;
    private LottoNumber bonusNumber;

    public WinningLotto(Set<LottoNumber> lottoNumbers, LottoNumber bonusNumber) {
        this.lottoNumbers = new Lotto(lottoNumbers);
        this.bonusNumber = bonusNumber;
    }

    WinningRanks match(Lottos lottos) {
        Supplier<Stream<Rank>> ranks = matchRanks(lottos);
        Map<Rank, Integer> winningRanks = calculateWinningRanks(ranks);
        return new WinningRanks(winningRanks);
    }

    private Map<Rank, Integer> calculateWinningRanks(Supplier<Stream<Rank>> ranks) {
        Map<Rank, Integer> winningRanks = new HashMap<>();

        ranks.get()
            .forEach(rank -> winningRanks.put(rank,
                Optional.ofNullable(winningRanks.get(rank)).orElse(DEFAULT_WINNING_TICKET_SIZE)
                    + WINNING_AMOUNT));
        return winningRanks;
    }

    private Supplier<Stream<Rank>> matchRanks(Lottos lottos) {
        return () -> lottos.getLottos()
            .stream()
            .map(this::countMatchNumber)
            .map(matchNumber -> Rank.valueOf(matchNumber, matchBonusNumber(bonusNumber)));
    }

    private int countMatchNumber(Lotto lotto) {
        return (int)lottoNumbers.getLottoNumbers().stream()
            .filter(lotto::contains)
            .count();
    }

    private boolean matchBonusNumber(LottoNumber bonusNumber) {
        return lottoNumbers.contains(bonusNumber);
    }
}
