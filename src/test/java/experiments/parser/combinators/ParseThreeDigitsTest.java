package experiments.parser.combinators;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseThreeDigitsTest {

    private Parser<Integer> parseADigit;
    private Parser<Pair<Pair<Integer, Integer>, Integer>> parseThreeDigits;
    private Function<Pair<Pair<Integer, Integer>, Integer>, String> transformPair;

    @Before
    public void setUp() throws Exception {
        parseADigit = Parser.anyOfRangeClosed('0', '9');

        parseThreeDigits = parseADigit.andThen(parseADigit).andThen(parseADigit);

        transformPair = pairOfPair -> IntStream.of(pairOfPair.first.first, pairOfPair.first.second, pairOfPair.second)
                .mapToObj(codePoint -> String.valueOf(codePoint - '0')).collect(Collectors.joining());
    }

    @Test
    public void should_parse_three_digits() throws Exception {
        assertThat(parseThreeDigits.apply("123A")).isEqualTo(Result.success(Pair.of(Pair.of('1', '2'), '3'), "A"));
    }

    @Test
    public void should_parse_three_digits_as_string() throws Exception {

        Parser<String> parseThreeDigitsAsString = parseThreeDigits.map(transformPair);

        assertThat(parseThreeDigitsAsString.apply("123A"))
                .isEqualTo(Result.success("123", "A"));
    }
    
    @Test
    public void should_parse_three_digits_fail_when_the_input_not_start_by_the_expected_digits() throws Exception {

        Parser<String> parseThreeDigitsAsString = parseThreeDigits.map(transformPair);

        assertThat(parseThreeDigitsAsString.apply("Z23A"))
                .isEqualTo(Result.failure("Expecting %c, got %c", '9', 'Z'));
    }

    @Test
    public void should_parse_three_digits_as_integer() throws Exception {

        Parser<Integer> parseThreeDigitsAsInteger = parseThreeDigits
                .map(transformPair).map(Integer::valueOf);

        assertThat(parseThreeDigitsAsInteger.apply("123A"))
                .isEqualTo(Result.success(123, "A"));
    }

}
