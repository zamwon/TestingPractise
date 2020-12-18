package pl.sda.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArraysUtilTest {


    @ParameterizedTest
    @NullAndEmptySource
    public void shouldReturnFalseWhenNotEmpty(List<String> values){
        assertFalse(ArraysUtil.isValid(values));
    }
}