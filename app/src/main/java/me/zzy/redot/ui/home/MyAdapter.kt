package me.zzy.redot.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_board.view.*
import me.zzy.redot.R
import me.zzy.redot.ui.design.ScrollTextView
import me.zzy.redot.ui.design.TextSettingActivity
import me.zzy.redot.ui.home.MyAdapter.MyViewHolder

/**
 * @author ZZY
 * 11/16/20.
 */
class MyAdapter     // Provide a suitable constructor (depends on the kind of dataset)
(private val mDataset: Array<String>) : RecyclerView.Adapter<MyViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class MyViewHolder(v: CardView) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var textView: TextView = v.cardText
        var imageView: ImageView = v.imageView
        var cardView: CardView = v.card
        private val mScrollTextView: ScrollTextView = v.findViewById(R.id.myScrollTextView)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_board, parent, false) as CardView

//        v.setOnClickListener(v1 -> {
//            parent.getContext().startActivity(new Intent(parent.getContext(), TextActivity.class));
////            Toast.makeText(parent.getContext(), "AAA", Toast.LENGTH_SHORT).show();
//        });
        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = mDataset[position]
        //        holder.mScrollTextView.setBackgroundColor(Color.TRANSPARENT);
//        holder.mScrollTextView.setScrollTextBackgroundColor();
        holder.cardView.setOnClickListener { v: View -> v.context.startActivity(Intent(v.context, TextSettingActivity::class.java)) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mDataset.size
    }
}