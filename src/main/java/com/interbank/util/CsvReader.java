package com.interbank.util;

import com.interbank.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvReader {
    public List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Saltar cabecera

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0].trim());
                String type = fields[1].trim();
                double amount = Double.parseDouble(fields[2].trim());

                transactions.add(new Transaction(id, type, amount));
            }

        } catch (Exception e) {
            log.error("Error al leer el archivo CSV: {}", e.getMessage());
        }

        return transactions;
    }
}
