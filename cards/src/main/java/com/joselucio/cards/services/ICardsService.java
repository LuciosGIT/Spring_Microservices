package com.joselucio.cards.services;

import com.joselucio.cards.dtos.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);


    boolean deleteCard(String mobileNumber);


}
