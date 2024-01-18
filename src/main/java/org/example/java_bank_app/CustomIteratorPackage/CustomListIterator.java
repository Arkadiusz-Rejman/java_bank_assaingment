package org.example.java_bank_app.CustomIteratorPackage;


import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class CustomListIterator<T> {
    ObservableList<T> list;
    IntegerProperty iteratorIndex;
    ObjectProperty<T> currentObject;

    public CustomListIterator(ObservableList<T> collection){
        list = collection;
        iteratorIndex = new SimpleIntegerProperty(0);
        currentObject = new SimpleObjectProperty<>(!list.isEmpty() ? list.get(iteratorIndex.get()) : null);


        iteratorIndex.addListener((observableValue, oldValue, newValue) -> Platform.runLater(() -> {
            if(!list.isEmpty()) this.currentObject.set(list.get(iteratorIndex.get()));
            else this.currentObject.set(null);
        }));

        list.addListener((ListChangeListener<T>) change -> {
            while (change.next()) {
                int addedSubListSize = change.getAddedSubList().size();
                int index = Math.max(0, Math.min(iteratorIndex.get(), addedSubListSize - 1));
                currentObject.set(addedSubListSize > 0 ? list.get(index) : null);
                iteratorIndex.set(addedSubListSize > 0 ? index : 0);
            }
        });
    }

    public void setIteratorChangeListener(IteratorRunnable iteratorRunnable){
        iteratorIndex.addListener((observableValue, oldValue, newValue) -> {
            if(iteratorRunnable != null && !list.isEmpty()) Platform.runLater(() -> iteratorRunnable.run(getCurrent()));
        });
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

    public ObjectProperty<T> getCurrentObjectProperty() { return this.currentObject; }

    public int getIteratorIndex() { return this.iteratorIndex.get(); }

}

