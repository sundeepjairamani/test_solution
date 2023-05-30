package com.smallworld;

import com.google.gson.Gson;
import com.smallworld.data.Transaction;

import java.util.*;
import java.io.FileReader;
import java.io.IOException;

public class TransactionDataFetcher {

    private List<Transaction> transactions;

    public TransactionDataFetcher() {
        transactions = readTransactionData();
    }

    private List<Transaction> readTransactionData() {
        // Read the transaction data from the JSON file
        try (FileReader reader = new FileReader("transactions.json")) {
            Transaction[] transactionArray = new Gson().fromJson(reader, Transaction[].class);
            return Arrays.asList(transactionArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        double totalAmount = 0;
        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }
        return totalAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        double totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getSenderFullName().equals(senderFullName)) {
                totalAmount += transaction.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        double maxAmount = Double.MIN_VALUE;
        for (Transaction transaction : transactions) {
            maxAmount = Math.max(maxAmount, transaction.getAmount());
        }
        return maxAmount;
    }

     /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        Set<String> uniqueClients = new HashSet<>();
        for (Transaction transaction : transactions) {
            uniqueClients.add(transaction.getSenderFullName());
            uniqueClients.add(transaction.getBeneficiaryFullName());
        }
        return uniqueClients.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        for (Transaction transaction : transactions) {
            if ((transaction.getSenderFullName().equals(clientFullName) || transaction.getBeneficiaryFullName().equals(clientFullName))
                    && transaction.getIssueId() != null && !transaction.isIssueSolved()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        Map<String, Transaction> transactionsByBeneficiary = new HashMap<>();
        for (Transaction transaction : transactions) {
            transactionsByBeneficiary.put(transaction.getBeneficiaryFullName(), transaction);
        }
        return transactionsByBeneficiary;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = new HashSet<>();
        for (Transaction transaction : transactions) {
            if (transaction.getIssueId() != null && !transaction.isIssueSolved()) {
                unsolvedIssueIds.add(Integer.parseInt(transaction.getIssueId()));
            }
        }
        return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getIssueMessage() != null && transaction.isIssueSolved()) {
                solvedIssueMessages.add(transaction.getIssueMessage());
            }
        }
        return solvedIssueMessages;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> sortedTransactions = new ArrayList<>(transactions);
        sortedTransactions.sort(Comparator.comparingDouble(Transaction::getAmount).reversed());
        return sortedTransactions.subList(0, Math.min(sortedTransactions.size(), 3));
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        Map<String, Double> senderTotalAmounts = new HashMap<>();
        for (Transaction transaction : transactions) {
            senderTotalAmounts.merge(transaction.getSenderFullName(), transaction.getAmount(), Double::sum);
        }
        return senderTotalAmounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }


    public static void main(String[] args) {
        TransactionDataFetcher t=new TransactionDataFetcher();
        System.out.println("Sum of the amounts of all transactions= " + t.getTotalTransactionAmount() +"\n");
        System.out.println("Sum of the amounts of all transactions sent by Billy Kimber= " + t.getTotalTransactionAmountSentBy("Billy Kimber") +"\n");
        System.out.println("Highest transaction amount= "+ t.getMaxTransactionAmount() +"\n");
        System.out.println("Counts the number of unique clients that sent or received a transaction= " + t.countUniqueClients() + "\n");
        System.out.println("Client (Grace Burgess) has at least one transaction with a compliance issue that has not been solved= "+t.hasOpenComplianceIssues("Grace Burgess") + "\n");
        System.out.println("All transactions indexed by beneficiary name= " +t.getTransactionsByBeneficiaryName() + "\n");
        System.out.println("All open compliance issues= "+t.getUnsolvedIssueIds() +"\n");
        System.out.println("List of all solved issue messages: "+t.getAllSolvedIssueMessages() +"\n");
        System.out.println("3 transactions with the highest amount sorted by amount descending= "+t.getTop3TransactionsByAmount() +"\n");
        System.out.println("senderFullName of the sender with the most total sent amount= " +t.getTopSender());
    }


}
