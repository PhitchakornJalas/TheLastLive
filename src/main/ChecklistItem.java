package main;

public class ChecklistItem {
	public String text;
    public int maxCount, count;
    boolean counted = false;

    public ChecklistItem(String text, int maxCount, int count) {
        this.text = text;
        this.maxCount = maxCount;
        this.count = count;
    }
}
