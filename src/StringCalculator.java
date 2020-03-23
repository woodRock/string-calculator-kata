import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class StringCalculator {

    private static final Integer THRESHOLD = 1001;
    private static final String STARTS = "//";
    private static final String ENDS = "]\n";
    private static final String OPEN_TAG = "[";
    private static final String CLOSE_TAG = "]";

    private Integer callCount = 0;
    private String delimiter = ",";
    private String numbers;

    public static void main(String[] args){
        String input = "//[**][%%]\n1**3%%2";
        try {
            System.out.println(new StringCalculator().Add(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int Add(String input) throws Exception {
        if (input.equals(""))
            return 0;
        this.numbers = input;
        delimiter();

        String regex = String.format("[\n%s]+", delimiter);
        List<String> strings = Arrays.asList(this.numbers.split(regex));
        List<Integer> integers = convertStringListToIntList(strings, Integer::parseInt);

        containsNegative(integers);
        integers = filterLargeNumbers(integers);
        increaseCallCount();
        return sum(integers);
    }

    private void delimiter() {
        if (!numbers.startsWith(STARTS))
            return;
        if (nCharDelimiters(numbers)) {
            int dStart = (STARTS + OPEN_TAG).length();
            int dEnd = numbers.indexOf(CLOSE_TAG);
            if (multipleDelimiters(numbers)){
                delimiter = delimitersToString(getDelimiters(numbers));
                numbers = numbers.substring(numbers.indexOf("\n")+1);
            } else {
                delimiter = numbers.substring(dStart, dEnd);
                numbers = numbers.substring(dEnd + ENDS.length());
            }
        } else if (numbers.startsWith(STARTS)) {
            delimiter = " " + numbers.charAt(STARTS.length());
            this.numbers = numbers.substring(STARTS.length() + 2);
        }
    }

    private static String getDelimiters(String input){
        return input.substring(STARTS.length(), input.indexOf("\n"));
    }

    public static String delimitersToString(String numbers){
        return String.join("|", Arrays.asList(numbers.replace('[',',').replace(']',',').substring(1).split(",")));
    }

    private static boolean multipleDelimiters(String input) {
        return (input.charAt(input.indexOf(CLOSE_TAG) + 1) + "").equals(OPEN_TAG);
    }

    private static boolean nCharDelimiters(String input) {
        return input.startsWith(STARTS + OPEN_TAG);
    }

    private static int sum(List<Integer> integers) {
        return integers.stream().mapToInt(i -> i).sum();
    }

    private static List<Integer> filterLargeNumbers(List<Integer> integers) {
        return integers.stream().filter(i -> i < THRESHOLD).collect(Collectors.toList());
    }

    private static void containsNegative(List<Integer> integers) throws Exception {
        List<Integer> negatives = integers.stream().filter(i -> i < 0).collect(Collectors.toList());
        if (negatives.size() > 0)
            throw new Exception("negatives are not allowed: " + formatList(negatives));
    }

    public static <T, U> List<U> convertStringListToIntList(List<T> listOfString, Function<T, U> function) {
        return listOfString.stream().map(function).collect(Collectors.toList());
    }

    private static String formatList(List<Integer> negatives){
        return negatives.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public Integer getCallCount() {
        return callCount;
    }

    private void increaseCallCount(){
        this.callCount = this.callCount + 1;
    }

}
