package app.services;

import java.util.regex.Pattern;

public class RegexService {
    private static final String nameRegex = "^[a-zA-Z][a-zA-Z0-9-_]{1,45}$";
    private static final String emailRegex = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private static final String dateRegex = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";
    private static final String zpRegex = "^[1-9]\\d{5,7}";

    public RegexService () {

    }

    public boolean match (int regexType ,String inputString) {
        switch (regexType) {
            case 1:
                return matchname(inputString);
            case 2:
                return matchemail(inputString);
            case 3:
                return matchdate(inputString);
            case 4:
                return matchzp(inputString);
            default:
            throw new IllegalArgumentException(regexType + " is not supported here");
        }
    }

    public boolean matchname(String inputString) {
        return Pattern.matches(nameRegex, inputString);
    }

    public boolean matchemail(String inputString) {
        return Pattern.matches(emailRegex, inputString);
    }

    public boolean matchdate(String inputString) {
        return Pattern.matches(dateRegex, inputString);
    }

    public boolean matchzp(String inputString) {
        return Pattern.matches(zpRegex, inputString);
    }
}
