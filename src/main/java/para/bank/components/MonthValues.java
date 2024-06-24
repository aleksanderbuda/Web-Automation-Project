package para.bank.components;

import lombok.Getter;

@Getter
public enum MonthValues {

    ALL("All"),
    JANUARY("january"),
    FEBRUARY("february"),
    MARCH("march"),
    APRIL("april"),
    MAY("may"),
    JUNE("june"),
    JULY("july"),
    AUGUST("august"),
    SEPTEMBER("september"),
    OCTOBER("october"),
    NOVEMBER("november"),
    DECEMBER("december");

    private final String monthValue;

    MonthValues(String monthValue) {
        this.monthValue = monthValue;
    }
}
