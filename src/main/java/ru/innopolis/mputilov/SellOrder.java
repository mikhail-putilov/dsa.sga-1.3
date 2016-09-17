package ru.innopolis.mputilov;

/**
 * Created by mputilov on 17/09/16.
 */
class SellOrder implements Comparable<SellOrder> {
    private final int lowestPrice;
    private final int amount;

    SellOrder(int lowestPrice, int amount) {
        this.lowestPrice = lowestPrice;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SellOrder{" +
                "lowestPrice=" + lowestPrice +
                ", amount=" + amount +
                '}';
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(SellOrder o) {
        int res = Integer.compare(lowestPrice, o.getLowestPrice());
        if (res != 0) {
            return res;
        }
        return Integer.compare(amount, o.getAmount());
    }
}
