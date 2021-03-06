package lotto.domain;

import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoGame {
    private LottosGenerator lottosGenerator;

    public LottoGame(LottosGenerator lottosGenerator) {
        this.lottosGenerator = lottosGenerator;
    }

    public void play() {
        Money purchaseAmount = InputView.inputPurchaseAmount();
        Money manualLottosAmount = InputView.inputManualLottoAmount();

        Lottos lottos = purchaseLottos(purchaseAmount, manualLottosAmount);
        Result result = produceResult(lottos);
        OutputView.printResult(result);
    }

    private Result produceResult(Lottos lottos) {
        WinningLotto winningLotto = InputView.inputWinningLotto();
        WinningRanks winningRanks = compareWithWinningNumbers(lottos, winningLotto);
        return new Result(winningRanks, lottos.toPrice());
    }

    private WinningRanks compareWithWinningNumbers(Lottos lottos, WinningLotto winningLotto) {
        return winningLotto.match(lottos);
    }

    private Lottos purchaseLottos(Money purchaseAmount, Money manualLottosAmount) {
        Lottos manualLottos = generateManualLottos(purchaseAmount, manualLottosAmount);
        Lottos autoLottos = generateAutoLottos(purchaseAmount.toLottosSizeExcept(manualLottosAmount));
        return manualLottos.add(autoLottos);
    }

    private Lottos generateManualLottos(Money purchaseAmount, Money manualLottosAmount) {
        Lottos manualLottos = InputView.inputManualLottos(manualLottosAmount);
        OutputView.printLottosSize(manualLottosAmount.getValue(),
            purchaseAmount.toLottosSizeExcept(manualLottosAmount));
        OutputView.printLottos(manualLottos);
        return manualLottos;
    }

    private Lottos generateAutoLottos(int lottosSize) {
        Lottos lottos = lottosGenerator.generate(lottosSize);
        OutputView.printLottos(lottos);
        return lottos;
    }

}
