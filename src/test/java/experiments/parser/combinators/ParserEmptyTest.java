package experiments.parser.combinators;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assume;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class ParserEmptyTest {

    @Property
    public void should_always_return_failure_whatever_the_non_null_or_empty_input(String input) throws Exception {
        Assume.assumeTrue(input != null && !input.isEmpty());

        Parser<?> parser = Parser.empty();
        assertThat(parser.apply(input)).isEqualTo(Result.failure("Empty input expected"));
    }

    @Property
    public void should_always_return_success_with_input_as_remaining_when_input_is_null_or_empty(String input) throws Exception {
        Assume.assumeTrue(input == null || input.isEmpty());

        Parser<?> parser = Parser.empty();
        assertThat(parser.apply(input)).isEqualTo(Result.success(null, input));
    }
}
