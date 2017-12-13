package de.fhro.inf.prg3.a10.kitchen;

import java.util.Deque;
import java.util.LinkedList;

import de.fhro.inf.prg3.a10.model.Dish;
import de.fhro.inf.prg3.a10.model.Order;

/**
 * Created by Tobi on 13.12.2017.
 */

public class KitchenHatchImpl implements KitchenHatch{

    private int maxMeals;
    private Deque<Order> orders;
    private Deque<Dish> dishes = new LinkedList<>();

    public KitchenHatchImpl(int maxMeals,Deque<Order> orders){

        this.orders = orders;
        this.maxMeals = maxMeals;

    }



    @Override
    public int getMaxDishes() {
        return maxMeals;
    }

    @Override
    public Order dequeueOrder(long timeout) {

        synchronized (orders){
            if(orders.size() == 0){
                return null;
            }else{
                orders.notifyAll();
                return orders.pop();
            }
        }

    }

    @Override
    public int getOrderCount() {
        return orders.size();
    }

    @Override
    public Dish dequeueDish(long timeout) throws InterruptedException {
        synchronized (dishes){
            if(dishes.size() == 0){
                dishes.wait(timeout);
            }else{
                dishes.notifyAll();
                return dishes.pop();
            }
        }
        return null;
    }

    @Override
    public void enqueueDish(Dish m) throws InterruptedException {
        synchronized (dishes){
            if(dishes.size()>= maxMeals){
                dishes.wait(1000);
            }else{
                dishes.push(m);
                dishes.notifyAll();
            }

        }

    }

    @Override
    public int getDishesCount() {
        return dishes.size();
    }
}
