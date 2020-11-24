package com.gtw.crowd.entity.vo;

/**
 * @author
 * @create 2020-11-21-4:26
 */
public class DetailReturnVO {

    private Integer returnId;

    private Integer supportMoney;

    private Integer signalPurchase;

    private Integer purchase;

    private Integer supproterCount;

    private Integer freight;

    private Integer returnDate;

    private String content;

    public DetailReturnVO() {
    }

    public DetailReturnVO(Integer returnId, Integer supportMoney, Integer signalPurchase, Integer purchase, Integer supproterCount, Integer freight, Integer returnDate, String content) {
        this.returnId = returnId;
        this.supportMoney = supportMoney;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
        this.supproterCount = supproterCount;
        this.freight = freight;
        this.returnDate = returnDate;
        this.content = content;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getSupproterCount() {
        return supproterCount;
    }

    public void setSupproterCount(Integer supproterCount) {
        this.supproterCount = supproterCount;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Integer returnDate) {
        this.returnDate = returnDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

