package com.smallworld.data;

import lombok.Data;

@Data
public class Transaction {
    // Represent your transaction data here.
    private int mtn;

    private double amount;

    private String senderFullName;

    private int senderAge;

    private String beneficiaryFullName;

    private int beneficiaryAge;

    private String issueId;

    private boolean issueSolved;

    private String issueMessage;

}
