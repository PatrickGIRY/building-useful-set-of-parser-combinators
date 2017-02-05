package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static experiments.parser.combinators.InputBuilder.anInput;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserInputStartByCharacterTest {

    @Property
    public void should_always_return_success_when_input_start_by_given_character(
            @CodePoint int charToMatch,
            String remaining
    ) throws Exception {
        String input = anInput().withChar(charToMatch).withRemaining(remaining);

        Parser<Integer> startByChar = Parser.pChar(charToMatch);
        assertThat(startByChar.apply(input)).isEqualTo(Result.success(charToMatch, remaining));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_given_character(
            @CodePoint int charToMatch,
            String input) throws Exception {
        Assume.assumeTrue(input != null && !input.isEmpty());

        int firstChar = input.codePointAt(0);
        Assume.assumeTrue(firstChar != charToMatch);
        Parser<Integer> startByChar = Parser.pChar(charToMatch);
        assertThat(startByChar.apply(input)).isEqualTo(Result.failure("Expecting %c, got %c", charToMatch, firstChar));
    }
}
