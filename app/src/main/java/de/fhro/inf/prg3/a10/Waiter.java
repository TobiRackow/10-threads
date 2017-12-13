package de.fhro.inf.prg3.a10;

import de.fhro.inf.prg3.a10.kitchen.KitchenHatch;
import de.fhro.inf.prg3.a10.model.Dish;
import de.fhro.inf.prg3.a10.model.Order;

/**
 * Created by Tobi on 13.12.2017.
 */

public class Waiter implements Runnable {


    private String name;
    private ProgressReporter progressReporter;
    private KitchenHatch kitchenHatch;

    public Waiter(String name,KitchenHatch kitchenHatch,ProgressReporter progressReporter){
        this.name = name;
        this.kitchenHatch = kitchenHatch;
        this.progressReporter = progressReporter;

    }
    @Override
    public void run() {

        synchronized (this){
        try {
            wait(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        while(kitchenHatch.getDishesCount() > 0 && kitchenHatch.getOrderCount() > 0) {

            Dish dish = null;
            try {
                dish = kitchenHatch.dequeueDish(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiter "+name+" liefert Essen " + dish.getMealName());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressReporter.updateProgress();
        }
        progressReporter.notifyWaiterLeaving();
        System.out.println("Waiter "+name+" geht nach Hause");
    }
}
