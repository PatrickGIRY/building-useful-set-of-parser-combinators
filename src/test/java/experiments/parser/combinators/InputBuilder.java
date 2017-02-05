package experiments.parser.combinators;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class InputBuilder {

    private Collection<String> charsToMatch = new ArrayList<>();

    public static InputBuilder anInput() {
        return new InputBuilder();
    }

    private InputBuilder() {}

    public InputBuilder withChar(int charToMatch) {
        this.charsToMatch.add(String.valueOf((char)charToMatch));
        return this;
    }

    public String withRemaining(String remaining) {
        return charsToMatch.stream().collect(joining()) + remaining;
    }
}
