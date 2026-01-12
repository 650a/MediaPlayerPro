package fr.xxathyx.mediaplayer.theatre;

public enum ShowRepeat {
    NONE,
    DAILY,
    WEEKLY;

    public static ShowRepeat fromString(String value) {
        if (value == null) {
            return NONE;
        }
        try {
            return ShowRepeat.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
