package app.services;

import java.util.regex.Pattern;

public class RegexService {
    private static final String nameRegex = "^[a-zA-Z][a-zA-Z0-9-_]{1,45}$";
    private static final String emailRegex = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private static final String dateRegex = "(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)";
    private static final String zpRegex = "^[1-9][0-9]{4,6}";

    public RegexService() {
    }

    public boolean matchName(String inputString) {
        return Pattern.matches(nameRegex, inputString);
    }

    public boolean matchEmail(String inputString) {
        return Pattern.matches(emailRegex, inputString);
    }

    public boolean matchDate(String inputString) {
        return Pattern.matches(dateRegex, inputString);
    }

    public boolean matchZp(String inputString) {
        return Pattern.matches(zpRegex, inputString);
    }
}
