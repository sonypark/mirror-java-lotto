package lotto.domain;

import java.util.Objects;

import lotto.exception.InvalidLottoNumberException;

public class LottoNumber {
    static final int MAX = 45;
    static final int MIN = 1;
    private int value;

    private LottoNumber(int value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(int value) {
        if (value < MIN || MAX < value) {
            throw new InvalidLottoNumberException("유효하지 않은 로또 번호입니다");
        }
    }

    public static LottoNumber create(int value) {
        return new LottoNumber(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LottoNumber))
            return false;
        LottoNumber that = (LottoNumber)o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
