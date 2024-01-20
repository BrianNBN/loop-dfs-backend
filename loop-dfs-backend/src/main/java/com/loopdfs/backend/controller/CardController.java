package com.loopdfs.backend.controller;

import com.loopdfs.backend.model.Card;
import com.loopdfs.backend.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // CRUD operations
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    //Retrieve all cards associated with an account
    @GetMapping("/{accountId}")
    public List<Card> getCardsByAccount(@PathVariable Long accountId) {
        return cardService.getCardsByAccount(accountId);
    }

    //Create a new Card
    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card createdCard = cardService.createCard(card);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    // Update an existing card
    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(@PathVariable Long cardId, @RequestBody Card card) {
        Card updatedCard = cardService.updateCard(cardId, card);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }


    // Delete a card
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}