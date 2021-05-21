package com.jayant.pocketlibrary.dashboard.quoteApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("quotes")
    Call<List<QuoteData>> getQuote();
}
