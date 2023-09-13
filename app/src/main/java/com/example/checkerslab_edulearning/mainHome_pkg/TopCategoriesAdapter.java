package com.example.checkerslab_edulearning.mainHome_pkg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_standards_adapter;
import com.example.checkerslab_edulearning.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class TopCategoriesAdapter extends RecyclerView.Adapter<TopCategoriesAdapter.ViewHolder> {


    private final List<TopCategoriesModel> catItems;
    Context context;

    public TopCategoriesAdapter(List<TopCategoriesModel> catItems, Context context) {
        this.catItems = catItems;
        this.context = context;
    }

    @NonNull
    @Override
    public TopCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_cateogies_sl_layout, null, false);
        return new TopCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCategoriesAdapter.ViewHolder holder, int position) {
        final TopCategoriesModel categoryItemModel = catItems.get(position);

        String[] cardColors = {"#FAF4E4", "#D6ECD6", "#F8EBE7"};
        int colorIndex = position % cardColors.length;
        String cardColorCode = cardColors[colorIndex];// Add your color resource IDs
        int cardColor = Color.parseColor(cardColorCode);

        // Set the background color of the CardView
        holder.layout.setBackgroundColor(cardColor);

        holder.catName.setText(categoryItemModel.getCatName());
        Glide.with(holder.itemView)
                .load(categoryItemModel.getCatImgUrl())
                .fitCenter()
                .into(holder.catImage);
    }

    @Override
    public int getItemCount() {
        return catItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImage;
        TextView catName;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.top_categories_image_id);
            catName = itemView.findViewById(R.id.top_categories_name_id);
            layout = itemView.findViewById(R.id.top_categories_layout_id);
        }
    }
}

//
//    @Override
//    public TopCategoriesAdapter.SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_cateogies_sl_layout, null);
//        return new TopCategoriesAdapter.SliderAdapterViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(TopCategoriesAdapter.SliderAdapterViewHolder viewHolder, int position) {
//
//
//        final TopCategoriesModel categoryItemModel = catItems.get(position);
//
//        viewHolder.catName.setText(categoryItemModel.getCatName());
//        Glide.with(viewHolder.itemView)
//                .load(categoryItemModel.getCatImgUrl())
//                .fitCenter()
//                .into(viewHolder.catImage);
//    }
//
//    @Override
//    public int getCount() {
//        return catItems.size();
//    }
//
//    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
//        // Adapter class for initializing
//        // the views of our slider view.
//        View itemView;
//        ImageView catImage;
//        TextView catName;
//
//        public SliderAdapterViewHolder(View itemView) {
//            super(itemView);
//            catImage = itemView.findViewById(R.id.top_categories_image_id);
//            catName=itemView.findViewById(R.id.top_categories_name_id);
//            this.itemView = itemView;
//
//        }
 //   }






