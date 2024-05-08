package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        //Sorts the list
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        //Finds the index for the first term with the prefix in the sorted list
        int prefixIndex = Collections.binarySearch(terms, prefix, CharSequence::compare);
        /*
            Returns empty list if:
               -There is no term with prefix
               -If there is no prefix
         */
        if(prefixIndex < 0 || prefix == null || prefix.length() == 0){
            return result;
        }
        //Creates a list with starting from the first term with prefix to the end
        List<CharSequence> shortList = terms.subList(prefixIndex, terms.size());
        //Searches the list for all the terms with the prefix and adds each of them to the result list
        for (int i = 0; i < shortList.size(); i++){
            CharSequence part = shortList.get(i).subSequence(0, prefix.length());
            if(CharSequence.compare(prefix, part) == 0){
                result.add(shortList.get(i));
            } else {
                return result;
            }
        }
        return result;
    }
}
