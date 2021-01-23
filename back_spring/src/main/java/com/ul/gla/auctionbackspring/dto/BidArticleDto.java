package com.ul.gla.auctionbackspring.dto;

public class BidArticleDto {

    private long id;
    private float amount;
    private String bidder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }
}
