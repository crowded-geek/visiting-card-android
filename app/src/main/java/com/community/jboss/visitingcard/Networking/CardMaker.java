package com.community.jboss.visitingcard.Networking;

import com.community.jboss.visitingcard.VisitingCard.Card;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CardMaker {
    @POST("card")
    Call<Card> postCard(@Body Card card);
}
