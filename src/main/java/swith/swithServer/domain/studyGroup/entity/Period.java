package swith.swithServer.domain.studyGroup.entity;

public enum Period {
    NONE(""),
    ONE_MONTH("1개월"),
    THREE_MONTHS("3개월"),
    SIX_MONTHS("6개월"),
    ONE_YEAR("1년"),
    UNDECIDED("미정");

    private final String label;

    Period(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Period fromLabel(String label) {
        if (label == null || label.isEmpty()) {
            return NONE;
        }

        for (Period period : Period.values()) {
            if (period.label.equals(label)) {
                return period;
            }
        }
        throw new IllegalArgumentException("Invalid period label: " + label);
    }
}