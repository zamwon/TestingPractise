package pl.sda.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {

    @ParameterizedTest
    @EnumSource(value = TemperatureConverter.class)
    public void shouldConvertTemperatureAndExpectValueGreaterThen_Minus273_15(TemperatureConverter temperatureConverter){
        assertTrue(temperatureConverter.convertTemp(0) >= -273.15);
    }

    @ParameterizedTest
    @EnumSource(value = TemperatureConverter.class, names = {"CELSIUS_KELVIN"}, mode = EnumSource.Mode.EXCLUDE)
    public void shouldConvertTemperatureAndExpectValueGreaterThen_Minus273_15V2(TemperatureConverter temperatureConverter){
        assertTrue(temperatureConverter.convertTemp(0) >= -273.15);
    }
    @ParameterizedTest
    @EnumSource(value = TemperatureConverter.class, names = {"^.*CELSIUS*."}, mode = EnumSource.Mode.MATCH_ALL)
    public void shouldConvertTemperatureAndExpectValueGreaterThen_Minus273_15V3(TemperatureConverter temperatureConverter){
        assertTrue(temperatureConverter.convertTemp(0) >= -273.15);
    }
}