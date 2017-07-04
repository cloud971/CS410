package beginner;

import com.sandwich.koan.Koan;

import java.text.MessageFormat;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;
import static com.sandwich.util.Assert.fail;

public class AboutStrings {

    @Koan
    public void implicitStrings() {
        assertEquals("just a plain ole string".getClass(), "jello".getClass());
    }

    @Koan
    public void newString() {
        // very rarely if ever should Strings be created via new String() in
        // practice - generally it is redundant, and done repetitively can be slow
        String string = new String();
        String empty = "";
        assertEquals(string.equals(empty),new String().equals(empty));
    }

    @Koan
    public void newStringIsRedundant() {
        String stringInstance = "zero";
        String stringReference = new String(stringInstance);
        assertEquals(stringInstance.equals(stringReference), stringReference.equals(stringInstance));
    }

    @Koan
    public void newStringIsNotIdentical() {
        String stringInstance = "zero";
        String stringReference = new String(stringInstance);
        assertEquals(stringInstance == stringReference,stringReference == stringInstance);
    }

    @Koan
    public void stringIsEmpty() {

        assertEquals("".isEmpty(), new String().isEmpty());
        assertEquals("one".isEmpty(), "bob".isEmpty());
        assertEquals(new String().isEmpty(), new String().isEmpty());
        assertEquals(new String("").isEmpty(), new String().isEmpty());
        assertEquals(new String("one").isEmpty(), new String("one").isEmpty());
    }

    @Koan
    public void stringLength() {
        assertEquals("".length(), "".length());
        assertEquals("one".length(), "bob".length());
        assertEquals("the number is one".length(), "the number is two".length());
    }

    @Koan
    public void stringTrim() {
        assertEquals("".trim(),"".trim());
        assertEquals("one".trim(), "one".trim());
        assertEquals(" one more time".trim()," one more time".trim());
        assertEquals(" one more time         ".trim(), " one more time         ".trim());
        assertEquals(" and again\t".trim()," and again\t".trim());
        assertEquals("\t\t\twhat about now?\t".trim(), "\t\t\twhat about now?\t".trim());
    }

    @Koan
    public void stringConcatenation() {
        String one = "one";
        String space = " ";
        String two = "two";
        assertEquals(one + space + two,one + space + two );
        assertEquals(space + one + two, space + one + two);
        assertEquals(two + space + one,two + space + one);
    }

    @Koan
    public void stringUpperCase() {
        String str = "I am a number one!";
        assertEquals(str.toUpperCase(), "I AM A NUMBER ONE!".toUpperCase());
    }

    @Koan
    public void stringLowerCase() {
        String str = "I AM a number ONE!";
        assertEquals(str.toLowerCase(), "i am a number one!".toLowerCase());
    }

    @Koan
    public void stringCompare() {
        String str = "I AM a number ONE!";
        assertEquals(str.compareTo("I AM a number ONE!") == 0, str.compareTo("I AM a number ONE!")==0);
        assertEquals(str.compareTo("I AM a number ONE!") == 0, str.compareTo("I AM a number ONE!")==0);
        assertEquals(str.compareTo("I AM a number ONE!") == 0, str.compareTo("I AM a number ONE!")==0);
    }

    @Koan
    public void stringCompareIgnoreCase() {
        String str = "I AM a number ONE!";
        assertEquals(str.compareToIgnoreCase("I AM a number ONE!") == 0, __);
        assertEquals(str.compareToIgnoreCase("I am a number one!") == 0, __);
        assertEquals(str.compareToIgnoreCase("I AM A NUMBER ONE!") == 0, __);
    }

    @Koan
    public void stringStartsWith() {
        assertEquals("".startsWith("one"), __);
        assertEquals("one".startsWith("one"), __);
        assertEquals("one is the number".startsWith("one"), __);
        assertEquals("ONE is the number".startsWith("one"), __);
    }

    @Koan
    public void stringEndsWith() {
        assertEquals("".endsWith("one"), __);
        assertEquals("one".endsWith("one"), __);
        assertEquals("the number is one".endsWith("one"), __);
        assertEquals("the number is two".endsWith("one"), __);
        assertEquals("the number is One".endsWith("one"), __);
    }

    @Koan
    public void stringSubstring() {
        String str = "I AM a number ONE!";
        assertEquals(str.substring(0), __);
        assertEquals(str.substring(1), __);
        assertEquals(str.substring(5), __);
        assertEquals(str.substring(14, 17), __);
        assertEquals(str.substring(7, str.length()), __);
    }

    @Koan
    public void stringContains() {
        String str = "I AM a number ONE!";
        assertEquals(str.contains("one"), __);
        assertEquals(str.contains("ONE"), __);
    }

    @Koan
    public void stringReplace() {
        String str = "I am a number ONE!";
        assertEquals(str.replace("ONE", "TWO"), __);
        assertEquals(str.replace("I am", "She is"),  __);
    }

    @Koan
    public void stringBuilderCanActAsAMutableString() {
        assertEquals(new StringBuilder("one").append(" ").append("two").toString(), __);
    }

    @Koan
    public void readableStringFormattingWithStringFormat() {
        assertEquals(String.format("%s %s %s", "a", "b", "a"), __);
    }

    @Koan
    public void extraArgumentsToStringFormatGetIgnored() {
        assertEquals(String.format("%s %s %s", "a", "b", "c", "d"), __);
    }

    @Koan
    public void insufficientArgumentsToStringFormatCausesAnError() {
        try {
            String.format("%s %s %s", "a", "b");
            fail("No Exception was thrown!");
        } catch (Exception e) {
            assertEquals(e.getClass(), __);
            assertEquals(e.getMessage(), __);
        }
    }

    @Koan
    public void readableStringFormattingWithMessageFormat() {
        assertEquals(MessageFormat.format("{0} {1} {0}", "a", "b"), __);
    }

    @Koan
    public void extraArgumentsToMessageFormatGetIgnored() {
        assertEquals(MessageFormat.format("{0} {1} {0}", "a", "b", "c"), __);
    }

    @Koan
    public void insufficientArgumentsToMessageFormatDoesNotReplaceTheToken() {
        assertEquals(MessageFormat.format("{0} {1} {0}", "a"), __);
    }
}
