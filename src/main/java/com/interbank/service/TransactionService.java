package com.interbank.service;

import com.interbank.model.Transaction;
import com.interbank.util.CsvReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CsvReader csvReader;

    public List<Transaction> loadTransactions(String path) {
        return csvReader.readTransactions(path);
    }

    public void generateReport(List<Transaction> transactions) {
        double totalCredit = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Crédito"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalDebit = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Débito"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalCredit - totalDebit;

        Transaction maxTransaction = transactions.stream()
                .max(Comparator.comparingDouble(Transaction::getAmount))
                .orElse(null);

        long creditCount = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Crédito"))
                .count();

        long debitCount = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("Débito"))
                .count();

        System.out.println("Reporte de Transacciones");
        System.out.println("---------------------------------------------");
        System.out.printf("Balance Final: %.2f%n", balance);
        if (maxTransaction != null) {
            System.out.printf("Transacción de Mayor Monto: ID %d - %.2f%n", maxTransaction.getId(), maxTransaction.getAmount());
        }
        System.out.printf("Conteo de Transacciones: Crédito: %d Débito: %d%n", creditCount, debitCount);
    }
}
