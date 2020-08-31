package edu.uic.cs478.application2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class AttractionsActivity extends AppCompatActivity implements MyListFragment.ListSelectionListener {

    public static String[] myAttractionsArray;
    public static String[] myAttractionsWebsiteArray;

    // Create appropriate fragment for websites and list
    private MyBrowserFragment myBrowserFragment;
    private MyListFragment myListFragment;

    FragmentManager myFragmentManager;

    private FrameLayout myListFrameLayout, myBrowserFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    // Bundles to pass information to fragments
    Bundle fragmentBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get string arrays for list and website
        myAttractionsArray = getResources().getStringArray(R.array.attractionsArray);
        myAttractionsWebsiteArray = getResources().getStringArray(R.array.attractionsWebsiteArray);

        myBrowserFragment = new MyBrowserFragment();
        fragmentBundle = new Bundle();
        fragmentBundle.putString("Website", "AttractionsWebsite");
        myBrowserFragment.setArguments(fragmentBundle);




        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        myFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

        myListFragment = new MyListFragment();
        fragmentBundle.putString("List", "AttractionsList");
        myListFragment.setArguments(fragmentBundle);

        fragmentTransaction.replace(
                R.id.ListFramelayoutID,
                myListFragment
        );


        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        myFragmentManager.addOnBackStackChangedListener(
                // UB 2/24/2019 -- Use support version of Listener
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
        });


        setContentView(R.layout.activity_main);
    }

    // Changes layout when it detects a change of orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setLayout();
    }


    // Changes layout according to any action and orientation
    private void setLayout(){
        // Get references to the MyListFragment and to the WebsiteFragment
        myListFrameLayout = (FrameLayout) findViewById(R.id.ListFramelayoutID);
        myBrowserFrameLayout = (FrameLayout) findViewById(R.id.BrowserFramelayoutID);

        // Determine whether the QuoteFragment has been added
        if (!myBrowserFragment.isAdded()) {

            // Make the TitleFragment occupy the entire layout
            myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            myBrowserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        }
        else {
            // Change layout according to Landscape Mode
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // Make the TitleLayout take 1/3 of the layout's width
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the QuoteLayout take 2/3's of the layout's width
                myBrowserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
            // Change layout according to Portrait Mode
            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                // Make the TitleLayout take 0 of the layout's width
                myListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 0f));

                // Make the QuoteLayout take entire of the layout's width
                myBrowserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
            }
        }

    }


    // Implement Java interface ListSelectionListener defined in MyListFragment
    // Called by MyListFragment when the user selects an item in the MyListFragment
    @Override
    public void onListSelection(int index) {
        if(!myBrowserFragment.isAdded()){
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();

            // Add MyBrowserFragment to the layout
            fragmentTransaction.add(R.id.BrowserFramelayoutID, myBrowserFragment);

            // Add this FragmentTransaction to backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force android to execute the committed FragmentTransaction
            myFragmentManager.executePendingTransactions();

        }

        if(myBrowserFragment.getShownIndex() != index){
            // Tell myBrowserFragment to get website at position index and display using webview
            myBrowserFragment.showQuoteAtIndex(index);
        }

    }

    // Function to populate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.option_menu,menu);
        return true;
    }

    // Function to define action of each option
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle option item
        switch (item.getItemId()) {
            case R.id.AttractionsOptionID:
                Intent AttractionsIntent = new Intent(this, AttractionsActivity.class);
                this.startActivity(AttractionsIntent);
                break;
            case R.id.RestaurantsOptionsID:
                myFragmentManager.popBackStack();
                Intent RestaurantsIntent = new Intent(this, RestaurantsActivity.class);
                this.startActivity(RestaurantsIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}