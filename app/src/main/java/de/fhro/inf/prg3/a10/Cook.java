package de.fhro.inf.prg3.a10;

import de.fhro.inf.prg3.a10.kitchen.KitchenHatch;
import de.fhro.inf.prg3.a10.model.Dish;
import de.fhro.inf.prg3.a10.model.Order;

/**
 * Created by Tobi on 13.12.2017.
 */

public class Cook implements Runnable{

    private String name;
    private ProgressReporter progressReporter;
    private KitchenHatch kitchenHatch;

    public Cook(String name,KitchenHatch kitchenHatch,ProgressReporter progressReporter){
        this.name = name;
        this.kitchenHatch = kitchenHatch;
        this.progressReporter = progressReporter;

    }

    @Override
    public void run() {

        System.out.println("Koch "+name+" ist zur Arbeit erschienen");
        while(kitchenHatch.getOrderCount() > 0) {
            Order order = kitchenHatch.dequeueOrder(1000);
            System.out.println("Koch "+name+" erhaelt Bestellung " + order.getMealName());
            Dish dish = new Dish(order.getMealName());
            try {
                Thread.sleep(dish.getCookingTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                kitchenHatch.enqueueDish(dish);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressReporter.updateProgress();
        }
        progressReporter.notifyCookLeaving();
        System.out.println("Koch "+name+" geht nach Hause");
    }
}
