package at.irian.cdiatwork.ideafork.ui.jsf.view.model;

public class SelectableEntry<T> {
    private final T entry;
    private boolean selected;

    public SelectableEntry(T entry) {
        this.entry = entry;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public T getEntry() {
        return entry;
    }
}
