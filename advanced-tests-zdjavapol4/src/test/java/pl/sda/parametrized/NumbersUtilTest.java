package pl.sda.parametrized;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NumbersUtilTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 77, 411, 943, 123})
    public void shouldReturnTrueForOddNumbers(int input) {
        assertTrue(NumbersUtil.isOdd(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {12, 32, 4, 778, 414, 944, 122})
    public void shouldReturnFalseForEvenNumbers(int input) {
        assertFalse(NumbersUtil.isOdd(input));
    }


    @ParameterizedTest
    @MethodSource(value = "provideNumbersWithInfoAboutParity")
    public void shouldReturnExpectedValueForGivenNumbers(int input, boolean expected) {
        assertEquals(expected, NumbersUtil.isOdd(input));
    }

    private static Stream<Arguments> provideNumbersWithInfoAboutParity() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(12, false),
                Arguments.of(13, true),
                Arguments.of(136, false)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(NumberWithInfoAboutParityProvider.class)
    public void shouldReturnExpectedValueForGivenNumbers_V2(int input, boolean expected) {
        assertEquals(expected, NumbersUtil.isOdd(input));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForZeroAsSecondArgument(){
        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy( () -> NumbersUtil.divide(10,0))
                .withMessage("dividend can't be 0");
    }

    //todo
    @Test
    public void shouldDivideTwoNumbers(){
        assertEquals(2.0f, NumbersUtil.divide(6.0f, 3.0f));
    }
}