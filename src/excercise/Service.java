package excercise;

public class Service {

    private Menu parent;

     private Service (){}

    public static boolean isNumeric(String str)  // a method that tests if a string is numeric
    {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
