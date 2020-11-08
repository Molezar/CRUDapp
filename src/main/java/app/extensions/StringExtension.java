package app.extensions;

public final class StringExtension {
    public static boolean isNullOrEmpty (String str) {
        if(str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
