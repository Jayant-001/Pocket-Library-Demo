package com.jayant.pocketlibrary.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayant.pocketlibrary.R;
import com.jayant.pocketlibrary.dashboard.quoteApi.ApiInterface;
import com.jayant.pocketlibrary.dashboard.quoteApi.QuoteData;
import com.jayant.pocketlibrary.dashboard.quoteApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView quoteText, quoteAuthor;

    ApiInterface apiInterface;
    List<QuoteData> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        quoteText = view.findViewById(R.id.quote_text);
        quoteAuthor = view.findViewById(R.id.quote_author);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>();

        apiInterface.getQuote().enqueue(new Callback<List<QuoteData>>() {
            @Override
            public void onResponse(Call<List<QuoteData>> call, Response<List<QuoteData>> response) {

                if (response.body().size() > 0) {
                    list.addAll(response.body());

                    Random rand = new Random();
                    int i = rand.nextInt(list.size()-1);
                    String quote = list.get(i).getText();

                    String author;
                    if (list.get(i).getAuthor() != null)
                    {
                        author = "Unknown";
//                        Log.e("error", "null author");
                    }
                    else {
                        author = list.get(i).getAuthor();
                    }

                    quoteText.setText(quote);
                    quoteAuthor.setText("- " + author);

                }
            }

            @Override
            public void onFailure(Call<List<QuoteData>> call, Throwable t) {

            }
        });

        return view;
    }
}