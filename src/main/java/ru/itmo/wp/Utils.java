package ru.itmo.wp;
import java.util.function.Predicate;

public class Utils {
    public static Predicate<String> isParsableToLong = new Predicate<String>() {
        public boolean test(String s) {
            try {
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
    };
}
