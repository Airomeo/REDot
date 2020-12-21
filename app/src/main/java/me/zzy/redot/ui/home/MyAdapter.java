package me.zzy.redot.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import me.zzy.redot.R;
import me.zzy.redot.ui.design.MScrollTextView;
import me.zzy.redot.ui.design.TextSettingActivity;

/**
 * @author ZZY
 * 11/16/20.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public CardView cardView;
        private MScrollTextView mScrollTextView;

        public MyViewHolder(CardView v) {
            super(v);
            textView = v.findViewById(R.id.cardText);
            imageView = v.findViewById(R.id.imageView);
            cardView = v.findViewById(R.id.card);
            mScrollTextView = v.findViewById(R.id.myScrollTextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_board, parent, false);

//        v.setOnClickListener(v1 -> {
//            parent.getContext().startActivity(new Intent(parent.getContext(), TextActivity.class));
////            Toast.makeText(parent.getContext(), "AAA", Toast.LENGTH_SHORT).show();
//        });
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);
//        holder.mScrollTextView.setBackgroundColor(Color.TRANSPARENT);
//        holder.mScrollTextView.setScrollTextBackgroundColor();
        holder.cardView.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), TextSettingActivity.class)));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}

