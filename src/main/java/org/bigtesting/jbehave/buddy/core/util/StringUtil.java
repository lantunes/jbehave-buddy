package org.bigtesting.jbehave.buddy.core.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class StringUtil {

    public static String[] getSortedStrings(Set<String> strings) {
        String[] items = strings.toArray(new String[strings.size()]);
        Arrays.sort(items, new StringComparator());
        return items;
    }

    private static final class StringComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
}
