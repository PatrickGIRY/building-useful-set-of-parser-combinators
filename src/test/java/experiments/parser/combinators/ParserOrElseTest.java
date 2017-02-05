package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static experiments.parser.combinators.InputBuilder.anInput;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserOrElseTest {

    @Property
    public void should_always_return_success_when_input_start_by_given_first_character(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String remaining
    ) throws Exception {
        String input = anInput().withChar(charToMatch1).withRemaining(remaining);

        Parser<Integer> parseCharToMatch1OrElseCharToMatch2 = Parser.pChar(charToMatch1).orElse(Parser.pChar(charToMatch2));
        assertThat(parseCharToMatch1OrElseCharToMatch2.apply(input)).isEqualTo(Result.success(charToMatch1, remaining));
    }

    @Property
    public void should_always_return_success_when_input_start_by_given_second_character(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String remaining
    ) throws Exception {
        String input = anInput().withChar(charToMatch2).withRemaining(remaining);

        Parser<Integer> parseCharToMatch1OrElseCharToMatch2 = Parser.pChar(charToMatch1).orElse(Parser.pChar(charToMatch2));
        assertThat(parseCharToMatch1OrElseCharToMatch2.apply(input)).isEqualTo(Result.success(charToMatch2, remaining));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_given_character(
            @CodePoint int charToMatch1,
            @CodePoint int charToMatch2,
            String input) throws Exception {
        Assume.assumeTrue(input != null && !input.isEmpty());

        int firstChar = input.codePointAt(0);
        Assume.assumeTrue(firstChar != charToMatch1 && firstChar != charToMatch2);
        Parser<Integer> parseCharToMatch1OrElseCharToMatch2 = Parser.pChar(charToMatch1).orElse(Parser.pChar(charToMatch2));
        assertThat(parseCharToMatch1OrElseCharToMatch2.apply(input)).isEqualTo(Result.failure("Expecting %c, got %c", charToMatch2, firstChar));
    }
}