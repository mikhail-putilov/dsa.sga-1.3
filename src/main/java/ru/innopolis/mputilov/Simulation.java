package ru.innopolis.mputilov;

import java.security.SecureRandom;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

import static ru.innopolis.mputilov.Helper.sleepWithIgnorance;

/**
 * Created by mputilov on 17/09/16.
 */
public class Simulation {
    private Queue<BuyOrder> buyOrderQueue = new PriorityBlockingQueue<>();
    private Queue<SellOrder> sellOrderQueue = new PriorityBlockingQueue<>();

    public void run() {
        Thread inputThread = new Thread(this::inputThread);
        inputThread.run();
        for (int i = 0; i < 100; i++) {
            sleepWithIgnorance(100L);
            tryToFindMatchingOrders();
        }
        System.out.println("Simulation is complete!");
    }

    private void tryToFindMatchingOrders() {
        boolean isFirstAttemptFail = true;
        while (true) {
            BuyOrder buyOrder = buyOrderQueue.peek(); //булку куплю за 20 р и меньше
            SellOrder sellOrder = sellOrderQueue.peek(); // булку продам за 19 р и больше
            if (buyOrder == null || sellOrder == null) {
                break;
            }
            if (sellOrder.getLowestPrice() <= buyOrder.getHighestPrice()) { // если продать готовы
                if (buyOrder.getAmount() <= sellOrder.getAmount()) { //если продать можем всё
                    buyOrderQueue.poll(); // удовлетворяем полностью ордер на покупку
                    SellOrder partiallySold = sellOrderQueue.poll(); //перевставляем ордер на продажу
                    if (Integer.compare(partiallySold.getAmount(), buyOrder.getAmount()) != 0) {
                        sellOrderQueue.add(new SellOrder(partiallySold.getLowestPrice(), sellOrder.getAmount() - buyOrder.getAmount()));
                        System.out.format("Fully bought %s\n", buyOrder);
                        continue;
                    }
                    System.out.format("Fully bought %s | Fully sold %s\n", buyOrder, sellOrder);
                } else {
                    sellOrderQueue.poll(); // удовлетворяем полностью ордер на продажу
                    BuyOrder partiallyBought = buyOrderQueue.poll(); //перевставляем ордер на покупку
                    buyOrderQueue.add(new BuyOrder(partiallyBought.getHighestPrice(), buyOrder.getAmount() - sellOrder.getAmount()));
                    System.out.format("Fully sold %s\n", sellOrder);
                }
                isFirstAttemptFail = false;
            } else {
                if (isFirstAttemptFail) {
                    int spread = sellOrder.getLowestPrice() - buyOrder.getHighestPrice();
                    System.out.format("Cannot buy/sell anything. Current spread is: %d\n", spread);
                }
                break;
            }
        }
    }

    public void inputThread() {
        Random sr = new Random(0L);
        for (int i = 0; i < 10; i++) {
            sellOrderQueue.add(new SellOrder(sr.nextInt(100), sr.nextInt(50)));
            buyOrderQueue.add(new BuyOrder(sr.nextInt(100), sr.nextInt(50)));
            System.out.format("Added %s %s\n", sellOrderQueue.peek(), buyOrderQueue.peek());
            sleepWithIgnorance(400L);
        }
    }

}
