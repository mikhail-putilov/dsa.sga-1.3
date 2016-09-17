package ru.innopolis.mputilov;

/**
 * Created by mputilov on 17/09/16.
 */
class BuyOrder implements Comparable<BuyOrder> {
    private final int highestPrice;
    private final int amount;

    public BuyOrder(int highestPrice, int amount) {
        this.highestPrice = highestPrice;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BuyOrder{" +
                "highestPrice=" + highestPrice +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(BuyOrder o) {
        int res = Integer.compare(highestPrice, o.getHighestPrice());
        if (res != 0) {
            return res;
        }
        return Integer.compare(amount, o.getAmount());
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public int getAmount() {
        return amount;
    }
}
