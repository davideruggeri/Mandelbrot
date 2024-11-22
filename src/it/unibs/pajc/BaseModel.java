package it.unibs.pajc;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class BaseModel {
    private ArrayList<ChangeListener> listeners = new ArrayList<>();

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }
    protected void fireValueChanged(ChangeEvent e) {
        for (ChangeListener l : listeners) {
            l.stateChanged(e);
        }
    }
}
