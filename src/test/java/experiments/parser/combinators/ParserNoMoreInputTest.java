package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserNoMoreInputTest {

    @Property
    public void should_always_return_a_failure_when_input_is_null_or_empty(@CodePoint int charToMatch, String input) throws Exception {
        Assume.assumeTrue(input == null || input.isEmpty());

        Parser<Integer> startByChar = Parser.pChar(charToMatch);
        assertThat(startByChar.apply(input)).isEqualTo(Result.failure("No more input"));
    }
}
