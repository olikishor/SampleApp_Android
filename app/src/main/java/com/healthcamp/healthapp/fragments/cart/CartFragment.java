package com.healthcamp.healthapp.fragments.cart;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcamp.healthapp.R;
import com.healthcamp.healthapp.activity.HomeActivity;
import com.healthcamp.healthapp.adapter.CartFragmentAdapter;
import com.healthcamp.healthapp.adapter.CategoryFragmentAdapter;
import com.healthcamp.healthapp.adapter.DetailsFragmentPageAdapter;
import com.healthcamp.healthapp.fragments.ListDataFragment;
import com.healthcamp.healthapp.helpers.DatabaseHelper;
import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.utils.FragmentHelper;
import com.healthcamp.healthapp.utils.SwipeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements View.OnClickListener {

    public static String TAG = CartFragment.class.getSimpleName();
    DatabaseHelper databaseHelper;
    private FragmentManager fragmentManager;
    RecyclerView mRecyclerView;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cart_recycle_view);

        databaseHelper = new DatabaseHelper(mRecyclerView.getContext());
        ArrayList<CartModel> allLists = databaseHelper.getAllCartItems();
        CartFragmentAdapter adapter = new CartFragmentAdapter(mRecyclerView.getContext(), allLists);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        setSwipeForRecyclerView();
        fragmentManager = ((AppCompatActivity) mRecyclerView.getContext()).getSupportFragmentManager();
        Button b = (Button) view.findViewById(R.id.btn_checkOut);
        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_checkOut:
                Fragment fragment = new CheckoutFragment();
                FragmentHelper.openFragment(fragment, TAG, v.getContext());
               /* FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, fragment, "CartFragment");
                transaction.addToBackStack("");
                transaction.commit();*/
                break;
        }
    }

    private void setSwipeForRecyclerView() {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, getActivity()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                CartFragmentAdapter adapter = (CartFragmentAdapter) mRecyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                CartFragmentAdapter adapter = (CartFragmentAdapter) mRecyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //set swipe label
        swipeHelper.setLeftSwipeLable("Archive");
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(getActivity(), R.color.button_grey));
    }


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response

                        try {

                            JSONObject o = new JSONObject(response);
                            JSONArray values = o.getJSONArray("flights");

                            for (int i = 0; i < values.length(); i++) {

                                JSONObject sonuc = values.getJSONObject(i);


                            }


                        } catch (JSONException ex) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        rq.add(stringRequest);
        // Inflate the layout for this fragment
        return view;
    }*/
}

