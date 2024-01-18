package org.example.java_bank_app.CustomIteratorPackage;


import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class CustomListIterator<T> {
    ObservableList<T> list;
    IntegerProperty iteratorIndex;

    public CustomListIterator(ObservableList<T> collection){
        list = collection;
        iteratorIndex = new SimpleIntegerProperty(0);
    }

    public void setChangeListener(IteratorRunnable iteratorRunnable){
        iteratorIndex.addListener((observableValue, oldValue, newValue) -> {
            if(iteratorRunnable != null && !list.isEmpty()) Platform.runLater(() -> iteratorRunnable.run(getCurrent()));
        });
    }

    public void updateList(ObservableList<T> newList){
        list = newList;
        iteratorIndex.set(0);
    }

    public T getNext(){
        if(!list.isEmpty()){
            iteratorIndex.set(iteratorIndex.get() + 1);
            if(iteratorIndex.get() == list.size()) iteratorIndex.set(0);
            return list.get(iteratorIndex.get());
        }else return null;
    }

    public T getPrevious(){
        if(!list.isEmpty()){
            iteratorIndex.set(iteratorIndex.get() - 1);
            if(iteratorIndex.get() == -1) iteratorIndex.set(list.size() - 1);
            return list.get(iteratorIndex.get());
        }else return null;
    }

    public T getCurrent(){
        if(!list.isEmpty()) return list.get(iteratorIndex.get());
        else return null;
    }

    public int getIteratorIndex() { return this.iteratorIndex.get(); }

    public void remove(T t) {
        if(!list.isEmpty()){
            list.remove(t);
            iteratorIndex.set(iteratorIndex.get() - 1);
        }
    }
}

