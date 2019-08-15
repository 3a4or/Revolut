package com.example.mohamedashour.test.ui.activities.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.mohamedashour.test.MyApplication;
import com.example.mohamedashour.test.R;
import com.example.mohamedashour.test.data.models.CurrencyModel;
import com.example.mohamedashour.test.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    @BindView(R.id.rv_main_currencies)
    RecyclerView currenciesRecyclerView;
    CurrenciesAdapter adapter;
    @BindView(R.id.tv_main_no_data)
    TextView noDataTextView;

    MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter = new MainPresenter(this);
        AppUtils.initVerticalRV(currenciesRecyclerView, MyApplication.getContext(), 1);
        presenter.getCurrencies("EUR", 1.0);
    }

    @Override
    public void receiveCurrencies(List<CurrencyModel> list) {
        if (list.size() > 0) {
            noDataTextView.setVisibility(View.GONE);
            adapter = new CurrenciesAdapter(this, list, (position, amount, repeat) -> {
                if (amount.equals("swap")) {
                    presenter.unSubscribeFromObservable();
                    adapter.swapItem(position, list.get(position));
                    currenciesRecyclerView.scrollToPosition(0);
                } else {
                    if (repeat) {
                        presenter.subscribeFromObservable();
                        presenter.getCurrenciesWithoutRepeat(list.get(position).getKey(), Double.parseDouble(amount));
                        adapter.clearAdapter();
                    }
                }
            });
            currenciesRecyclerView.setAdapter(adapter);
        } else {
            currenciesRecyclerView.setAdapter(null);
            noDataTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unSubscribeFromObservable();
    }
}
