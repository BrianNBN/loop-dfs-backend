package com.loopdfs.backend.service;

import com.loopdfs.backend.model.Card;
import com.loopdfs.backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    // Retrieve all cards associated with an account
    public List<Card> getCardsByAccount(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    // Retrieve a card by ID
    public Card getCardById(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        return optionalCard.orElse(null);
    }

    // Create a new card
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    // Update an existing card
    public Card updateCard(Long cardId, Card updatedCard) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (optionalCard.isPresent()) {
            Card existingCard = optionalCard.get();
            existingCard.setCardAlias(updatedCard.getCardAlias());
            return cardRepository.save(existingCard);
        } else {
            return null;
        }
    }

    // Delete a card
    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }
}
