package com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.btracsolution.deliverysystem.Model.MemberListResponse;

import java.util.ArrayList;

public class MemberSearchAdapter extends ArrayAdapter<MemberListResponse.MemberData> {
    private ArrayList<MemberListResponse.MemberData> items;
    private ArrayList<MemberListResponse.MemberData> itemsAll;
    private ArrayList<MemberListResponse.MemberData> suggestions;
    private int viewResourceId;
    ItemClickListener listener;

    @SuppressWarnings("unchecked")
    public MemberSearchAdapter(Context context, int viewResourceId,
                               ArrayList<MemberListResponse.MemberData> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<MemberListResponse.MemberData>) items.clone();
        this.suggestions = new ArrayList<MemberListResponse.MemberData>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        MemberListResponse.MemberData product = items.get(position);
        if (product != null) {
            TextView productLabel = (TextView) v.findViewById(android.R.id.text1);
            if (productLabel != null) {
                productLabel.setText(product.getMemberId());
            }
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClcik(product);
            }
        });
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((MemberListResponse.MemberData) (resultValue)).getMemberId();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (MemberListResponse.MemberData product : itemsAll) {
                    if (product.getMemberId().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<MemberListResponse.MemberData> filteredList = (ArrayList<MemberListResponse.MemberData>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (MemberListResponse.MemberData c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

    public interface ItemClickListener {
        void onItemClcik(MemberListResponse.MemberData employee);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

}