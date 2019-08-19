package com.peiyu.mem.domian.entity;

/**
 * @Author 900045
 * Created by Administrator on 2016/12/13.
 * 商品送券的实体
 */
public class GoodsForCoupon {
    /**
     * 商家id
     */
    private long vendorId;
    /**
     * 会员号
     */
    private String memNo;
    /**
     * 机构
     */
    private String organCode;
    /**
     * 门店
     */
    private String storeCode;
    /**
     * 商家一级品类编码
     */
    private String firstIcatCode;
    /**
     * 商家二级品类编码
     */
    private String secondIcatCode;
    /**
     * 商家三级品类编码
     */
    private String thirdIcatCode;
    /**
     * 商家四级品类编码
     */
    private String fourthIcatCode;
    /**
     * 一级品牌
     */
    private String firstBrandCode;
    /**
     * 二级品牌
     */
    private String secondBrandCode;
    /**
     * 三级品牌
     */
    private String thirdBrandCode;
    /**
     * 供应商
     */
    private String supplierCode;
    /**
     * sku编码(商品编码)
     */
    private String skuCode;
    /**
     * 单价
     */
    private double money;
    /**
     * 实际支付金额
     */
    private double realPayMoney;

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getFirstIcatCode() {
        return firstIcatCode;
    }

    public void setFirstIcatCode(String firstIcatCode) {
        this.firstIcatCode = firstIcatCode;
    }

    public String getSecondIcatCode() {
        return secondIcatCode;
    }

    public void setSecondIcatCode(String secondIcatCode) {
        this.secondIcatCode = secondIcatCode;
    }

    public String getThirdIcatCode() {
        return thirdIcatCode;
    }

    public void setThirdIcatCode(String thirdIcatCode) {
        this.thirdIcatCode = thirdIcatCode;
    }

    public String getFourthIcatCode() {
        return fourthIcatCode;
    }

    public void setFourthIcatCode(String fourthIcatCode) {
        this.fourthIcatCode = fourthIcatCode;
    }

    public String getFirstBrandCode() {
        return firstBrandCode;
    }

    public void setFirstBrandCode(String firstBrandCode) {
        this.firstBrandCode = firstBrandCode;
    }

    public String getSecondBrandCode() {
        return secondBrandCode;
    }

    public void setSecondBrandCode(String secondBrandCode) {
        this.secondBrandCode = secondBrandCode;
    }

    public String getThirdBrandCode() {
        return thirdBrandCode;
    }

    public void setThirdBrandCode(String thirdBrandCode) {
        this.thirdBrandCode = thirdBrandCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRealPayMoney() {
        return realPayMoney;
    }

    public void setRealPayMoney(double realPayMoney) {
        this.realPayMoney = realPayMoney;
    }
}
