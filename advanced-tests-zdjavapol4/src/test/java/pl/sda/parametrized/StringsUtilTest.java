package pl.sda.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class StringsUtilTest {


    @ParameterizedTest

    //given
    @CsvSource(value = {"test;TEST", "  kot ;KOT", "test ;TEST"}, delimiter = ';')
    public void shouldTrimAndUpperCaseInput(String input, String expected) {

        //when

        String actual = StringsUtil.toUpperCase(input);

        //then
        assertEquals(expected, actual);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1, delimiter = ',', lineSeparator = ";")
    public void shouldTrimAndUpperCaseInputWithCSVFile(String input, String expected) {

        //when

        String actual = StringsUtil.toUpperCase(input);

        //then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void shouldBeBlankForNullOrEmptyString(String input){
        assertTrue(StringsUtil.isBlank(input));
    }

}