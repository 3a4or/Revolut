package com.example.mohamedashour.test.ui.activities.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mohamedashour.test.R;
import com.example.mohamedashour.test.data.models.CurrencyModel;
import com.example.mohamedashour.test.utils.interfaces.RecyclerViewClickListener;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>{

    Context context;
    List<CurrencyModel> list;
    RecyclerViewClickListener recyclerViewClickListener;

    public CurrenciesAdapter(Context context, List<CurrencyModel> list, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_key)
        TextView keyTextView;
        @BindView(R.id.img_flag)
        CircleImageView flagImageView;
        @BindView(R.id.et_val)
        EditText valEditText;
        @BindView(R.id.ll_container)
        LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_currency, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.keyTextView.setText(list.get(i).getKey());
        Glide.with(context).load(list.get(i).getFlag()).placeholder(R.mipmap.ic_launcher).into(viewHolder.flagImageView);
        viewHolder.container.setOnClickListener(view -> {
            if (i != 0) {
                recyclerViewClickListener.OnItemClick(i, "swap", false);
            }
        });
        if (i == 0) {
            viewHolder.valEditText.setText(String.valueOf(list.get(i).getVal()));
            viewHolder.valEditText.addTextChangedListener(new TextWatcher() {
                boolean mToggle = false;
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int x, int x1, int x3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (mToggle) {
                        if (!viewHolder.valEditText.getText().toString().equals(String.valueOf(list.get(i).getVal()))){
                            if (!viewHolder.valEditText.getText().toString().equals("")) {
                                recyclerViewClickListener.OnItemClick(i, viewHolder.valEditText.getText().toString(), true);
                            }
                        }
                    } else {
                        mToggle = true;
                    }
                }
            });
        } else {
            viewHolder.valEditText.setText(String.valueOf(list.get(i).getVal()));
        }
    }

    public void swapItem(int fromPosition, CurrencyModel oldModel){
        list.remove(fromPosition);
        list.add(0, oldModel);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
