package com.smallworld;

import com.smallworld.data.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TransactionDataFetcher.class})
public class TransactionDataFetcherTest {

    @Autowired
    private TransactionDataFetcher dataFetcher;

    @BeforeEach
    public void setUp() {
        dataFetcher = new TransactionDataFetcher();
    }

    @Test
    public void testGetTotalTransactionAmount() {
        double totalAmount = dataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(4371.37, totalAmount, 0.0);
    }

    @Test
    public void testGetTotalTransactionAmountSentBy() {
        double totalAmount = dataFetcher.getTotalTransactionAmountSentBy("Billy Kimber");
        Assertions.assertEquals(459.09, totalAmount, 0.0);
    }

    @Test
    public void testGetMaxTransactionAmount() {
        double maxAmount = dataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(985.0, maxAmount, 0.0);
    }

    @Test
    public void testCountUniqueClients() {
        long uniqueClients = dataFetcher.countUniqueClients();
        Assertions.assertEquals(14, uniqueClients);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        boolean hasOpenIssues = dataFetcher.hasOpenComplianceIssues("Grace Burgess");
        Assertions.assertTrue(hasOpenIssues);
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        Map<String, Transaction> transactionsByBeneficiary = dataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertEquals(10, transactionsByBeneficiary.size());
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();
        Assertions.assertEquals(5, unsolvedIssueIds.size());
        Assertions.assertTrue(unsolvedIssueIds.contains(15));
    }

    @Test
    public void testGetAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();
        Assertions.assertEquals(3, solvedIssueMessages.size());
        Assertions.assertTrue(solvedIssueMessages.contains("Never gonna let you down"));
        Assertions.assertTrue(solvedIssueMessages.contains("Never gonna give you up"));
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
        Assertions.assertEquals(3, top3Transactions.size());
        Assertions.assertEquals(985.0, top3Transactions.get(0).getAmount(), 0.0);
        Assertions.assertEquals(666.0, top3Transactions.get(1).getAmount(), 0.0);
        Assertions.assertEquals(666.0, top3Transactions.get(2).getAmount(), 0.0);
    }

    @Test
    public void testGetTopSender() {
        Assertions.assertTrue(dataFetcher.getTopSender().isPresent());
        Assertions.assertEquals("Grace Burgess", dataFetcher.getTopSender().get());
    }



}
