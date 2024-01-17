package org.example.java_bank_app.CustomIteratorPackage;


@FunctionalInterface
public interface IteratorRunnable extends Runnable{
    void run(Object currentObject);

    @Override
    default void run(){

    }
}
