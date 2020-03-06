package lotto.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import lotto.exception.InvalidLottoNumberException;

class LottoNumberTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 45})
    @DisplayName("값이 정상 범위에 있을 때 로또 번호 생성")
    void constructWithValidValue(int value) {
        assertThat(LottoNumber.create(value)).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46})
    @DisplayName("값이 비정상 범위에 있을 때 로또 번호 생성")
    void constructWithInvalidValue(int value) {
        assertThatThrownBy(() -> LottoNumber.create(value))
            .isInstanceOf(InvalidLottoNumberException.class)
            .hasMessage("유효하지 않은 로또 번호입니다");
    }
}
