package com.healthcamp.healthapp.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.activity.HomeActivity;
import com.healthcamp.healthapp.fragments.SubCategoryFragment;
import com.healthcamp.healthapp.helpers.ApplicationVariables;
import com.healthcamp.healthapp.helpers.FragmentTitle;
import com.healthcamp.healthapp.models.DataModel;
import com.healthcamp.healthapp.models.HomeCategory.MainResult;
import com.healthcamp.healthapp.models.HomeCategory.SubCateogory;
import com.healthcamp.healthapp.utils.FragmentHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ITH-143 on 10-Sep-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private MainResult allCategories;
    private Context context = null;
    public static String TAG = HomeAdapter.class.getSimpleName();
    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView image;

        public HomeViewHolder(View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                int position = getAdapterPosition();
                ArrayList<SubCateogory> subCatList = (ArrayList<SubCateogory>) allCategories.getResult().getCategory().get(position).getSubCategories();
                FragmentTitle.SubCategoryTitle = allCategories.getResult().getCategory().get(position).getName();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ApplicationVariables.SUB_CAT_LISTS, subCatList);
                FragmentHelper.openFragment(new SubCategoryFragment(), TAG, context, bundle);
            }catch (Exception e){
                e.printStackTrace();
            }
            /*FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new SubCategoryFragment();
            fragment.setArguments(bundle);
            ft.replace(R.id.frame_container, fragment);
            ft.commit();*/
        }
    }

    public HomeAdapter(Context context, MainResult allCategories) {
        this.context = context;
        this.allCategories = allCategories;
    }

    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(viewType, parent, false);
        return new HomeAdapter.HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        TextView textViewName = holder.text;
        ImageView imageView = holder.image;
        String name = allCategories.getResult().getCategory().get(position).getName();
        String imageUrl = allCategories.getResult().getCategory().get(position).getImage();
        textViewName.setText(name);
        Picasso.with(this.context).load(imageUrl).into(imageView);
    }

    @Override
    public int getItemCount() {
        return allCategories.getResult().getCategory().size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position % 4) < 2
                ? R.layout.home_horizontal
                : R.layout.home_vertical;
    }

}

/*
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<DataModel> dataSet;

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView text;
     //   TextView textViewVersion;
        ImageView image;

        public HomeViewHolder(View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.text);
          //  this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public HomeAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
*/
/*        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);

        return homeViewHolder;*//*

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(viewType, parent, false);
        return new HomeAdapter.HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        TextView textViewName = holder.text;
       */
/* TextView textViewVersion = holder.textViewVersion;*//*

        ImageView imageView = holder.image;

        textViewName.setText(dataSet.get(position).getName());
       // textViewVersion.setText(dataSet.get(position).getVersion());
        imageView.setImageResource(dataSet.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position % 4) < 2
                ? R.layout.home_horizontal
                : R.layout.home_vertical;
    }

}
*/
