package edu.uic.cs478.application2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

public class MyListFragment extends ListFragment {
    private ListSelectionListener mListener = null;
    private String type;


    // Callback interface that allows this Fragment to notify the MainActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the Attractions or Restaurants activity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get appropriate name list
        Bundle bundle = this.getArguments();
        if(bundle != null){
            type = bundle.getString("List");

            if(type.equals("AttractionsList")){
                type = "attractions";
            }
            else if(type.equals("RestaurantsList")){
                type = "restaurants";
            }
        }

        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);
    }
    
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {

            // Set the ListSelectionListener for communicating with the QuoteViewerActivity
            // Try casting the containing activity to a ListSelectionListener
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            // Cast failed: This is not going to work because containing activity may not
            // have implemented onListSelection() method
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        if(type.equals("attractions")){
            Log.i("STRINGLIST", "DID IT WORK");
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.my_list_layout, AttractionsActivity.myAttractionsArray));
        }
        else if(type.equals("restaurants")){
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.my_list_layout, RestaurantsActivity.myRestaurantsArray));
        }

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

}
