package com.interbank;

import com.interbank.model.Transaction;
import com.interbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class InterbankApplication implements CommandLineRunner {

    private final TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(InterbankApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Ejecutando la aplicación...");
        String csvPath = "./data.csv";
        List<Transaction> transactions = transactionService.loadTransactions(csvPath);
        transactionService.generateReport(transactions);
    }
}
