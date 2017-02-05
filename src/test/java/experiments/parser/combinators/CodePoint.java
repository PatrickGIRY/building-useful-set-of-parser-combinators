package experiments.parser.combinators;

import com.pholser.junit.quickcheck.generator.GeneratorConfiguration;
import com.pholser.junit.quickcheck.generator.InRange;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@GeneratorConfiguration
@InRange(minInt = Character.MIN_VALUE, maxInt = Character.MAX_VALUE) @interface CodePoint {
}
