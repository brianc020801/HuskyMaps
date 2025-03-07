package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleExample {
    public static void main(String[] args) {
        List<CharSequence> terms = new ArrayList<>();
        terms.add("alpha");
        terms.add("delta");
        terms.add("do");
        terms.add("cats");
        terms.add("dodgy");
        terms.add("pilot");
        terms.add("dog");

// Choose your Autocomplete implementation.
        Autocomplete autocomplete = new TernarySearchTreeAutocomplete();
        autocomplete.addAll(terms);
// Choose your prefix string.
        CharSequence prefix = "";
        List<CharSequence> matches = autocomplete.allMatches(prefix);
        for (CharSequence match : matches) {
            System.out.println(match);
        }
    }
}
