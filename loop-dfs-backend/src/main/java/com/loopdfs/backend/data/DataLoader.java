package com.loopdfs.backend.data;

import com.github.javafaker.Faker;
import com.loopdfs.backend.model.Account;
import com.loopdfs.backend.model.Card;
import com.loopdfs.backend.repository.AccountRepository;
import com.loopdfs.backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void run(String... args) {
        generateFakeData();
    }

    private void generateFakeData() {
        Faker faker = new Faker();

        // Generate fake accounts
        for (int i = 0; i < 2; i++) {
            Account account = new Account();
            account.setIban(faker.finance().iban());
            account.setBicSwift(faker.finance().bic());
            account.setClientId((long) (i + 1)); // Incrementing client ID
            accountRepository.save(account);

            // Generate fake cards associated with each account
            Card card = new Card();
            card.setCardAlias(faker.name().username());
            card.setAccountId(account.getAccountId());
            card.setTypeOfCard(faker.options().option("Virtual", "Physical"));
            cardRepository.save(card);
        }
    }
}
