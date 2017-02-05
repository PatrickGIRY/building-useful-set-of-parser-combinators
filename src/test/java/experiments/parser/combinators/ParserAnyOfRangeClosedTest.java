package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static experiments.parser.combinators.InputBuilder.anInput;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserAnyOfRangeClosedTest {

    @Property
    public void should_always_return_success_when_input_start_by_lowercase(@InRange(minInt = 'a', maxInt = 'z') int startChar, String remaining) throws Exception {

        String input = anInput().withChar(startChar).withRemaining(remaining);
        Parser<Integer> startByLowercase = Parser.anyOfRangeClosed('a', 'z');
        Result<Integer> result = startByLowercase.apply(input);

        assertThat(result).isEqualTo(Result.success(startChar, remaining));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_lowercase(@CodePoint int startChar, String remaining) throws Exception {
        Assume.assumeTrue(startChar < 'a' || startChar > 'z');

        String input = anInput().withChar(startChar).withRemaining(remaining);
        Parser<Integer> startByLowercase = Parser.anyOfRangeClosed('a', 'z');
        Result<Integer> result = startByLowercase.apply(input);

        assertThat(result).isEqualTo(Result.failure("Expecting z, got %c", startChar));
    }

    @Property
    public void should_always_return_success_when_input_start_by_digit(@InRange(minInt = '0', maxInt = '9') int startChar, String remaining) throws Exception {

        String input = anInput().withChar(startChar).withRemaining(remaining);
        Parser<Integer> startByDigit = Parser.anyOfRangeClosed('0', '9');
        Result<Integer> result = startByDigit.apply(input);

        assertThat(result).isEqualTo(Result.success(startChar, remaining));
    }

    @Property
    public void should_always_return_failure_when_input_not_start_by_digit(@CodePoint int startChar, String remaining) throws Exception {
        Assume.assumeTrue(startChar < '0' || startChar > '9');

        String input = anInput().withChar(startChar).withRemaining(remaining);
        Parser<Integer> startByDigit = Parser.anyOfRangeClosed('0', '9');
        Result<Integer> result = startByDigit.apply(input);

        assertThat(result).isEqualTo(Result.failure("Expecting 9, got %c", startChar));
    }
}
