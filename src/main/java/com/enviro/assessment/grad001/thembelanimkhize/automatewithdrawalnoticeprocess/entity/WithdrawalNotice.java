package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class WithdrawalNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming auto-increment
    private int withdrawalNoticeId;

    @ManyToOne
    @JoinColumn(name = "InvestorID", nullable = false)
    private Investor investor;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal withdrawalAmount;

    @Column(nullable = false)
    private Date withdrawalDate;
    @Column(nullable = false)
    private String bankAccountNumber;
    @Column(nullable = false)
    private String bankAccountHolderName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Map to VARCHAR for limited values
    private WithdrawalStatus status;

    public WithdrawalNotice() {
    }

    public WithdrawalNotice(int withdrawalNoticeId,Investor investor,
                            Product product, BigDecimal withdrawalAmount, Date withdrawalDate,
                            String bankAccountNumber, String bankAccountHolderName,
                            WithdrawalStatus status) {
        this.withdrawalNoticeId = withdrawalNoticeId;
        this.investor = investor;
        this.product = product;
        this.withdrawalAmount = withdrawalAmount;
        this.withdrawalDate = withdrawalDate;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountHolderName = bankAccountHolderName;
        this.status = status;
    }

    public int getWithdrawalNoticeId() {
        return withdrawalNoticeId;
    }

    public void setWithdrawalNoticeId(int withdrawalNoticeId) {
        this.withdrawalNoticeId = withdrawalNoticeId;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public void setBankAccountHolderName(String bankAccountHolderName) {
        this.bankAccountHolderName = bankAccountHolderName;
    }

    public WithdrawalStatus getStatus() {
        return status;
    }

    public void setStatus(WithdrawalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WithdrawalNotice{" +
                "withdrawalNoticeId=" + withdrawalNoticeId +
                ", investor=" + investor +
                ", product=" + product +
                ", withdrawalAmount=" + withdrawalAmount +
                ", withdrawalDate=" + withdrawalDate +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankAccountHolderName='" + bankAccountHolderName + '\'' +
                ", status=" + status +
                '}';
    }

    // Enum for WithdrawalStatus with allowed values
    public enum WithdrawalStatus {
        PENDING, APPROVED, REJECTED
    }
}