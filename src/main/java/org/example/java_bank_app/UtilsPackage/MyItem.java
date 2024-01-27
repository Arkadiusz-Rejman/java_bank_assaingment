package org.example.java_bank_app.UtilsPackage;

public class MyItem {
    private final int value;
    private final String displayText;

    public MyItem(int value, String displayText) {
        this.value = value;
        this.displayText = displayText;
    }

    public int getItemValue() {
        return value;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
