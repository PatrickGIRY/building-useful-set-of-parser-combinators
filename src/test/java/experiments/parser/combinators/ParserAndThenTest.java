package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static experiments.parser.combinators.InputBuilder.anInput;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserAndThenTest {

    @Property
    public void should_always_return_success_when_input_start_by_2_given_characters(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String remaining
    ) throws Exception {

        String input = anInput().withChar(charToMatch1).withChar(charToMatch2).withRemaining(remaining);
        Parser<Pair<Integer, Integer>> parse2Chars = Parser.pChar(charToMatch1).andThen(Parser.pChar(charToMatch2));
        Result<Pair<Integer, Integer>> result = parse2Chars.apply(input);
        assertThat(result).isEqualTo(Result.success(Pair.of(charToMatch1, charToMatch2), remaining));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_first_given_character(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String input
    ) throws Exception {
        Assume.assumeTrue(input != null && !input.isEmpty());

        int firstChar = input.codePointAt(0);
        Assume.assumeTrue(firstChar != charToMatch1);

        Parser<Pair<Integer, Integer>> parse2Chars = Parser.pChar(charToMatch1).andThen(Parser.pChar(charToMatch2));
        Result<Pair<Integer, Integer>> result = parse2Chars.apply(input);
        assertThat(result).isEqualTo(Result.failure("Expecting %c, got %c", charToMatch1, firstChar));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_second_given_character(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String remaining
    ) throws Exception {
        Assume.assumeTrue(remaining != null && !remaining.isEmpty());

        int secondChar = remaining.codePointAt(0);
        Assume.assumeTrue(secondChar != charToMatch2);

        String input = anInput().withChar(charToMatch1).withRemaining(remaining);

        Parser<Pair<Integer, Integer>> parse2Chars = Parser.pChar(charToMatch1).andThen(Parser.pChar(charToMatch2));
        Result<Pair<Integer, Integer>> result = parse2Chars.apply(input);
        assertThat(result).isEqualTo(Result.failure("Expecting %c, got %c", charToMatch2, secondChar));
    }
}
