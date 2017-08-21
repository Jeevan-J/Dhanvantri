package in.ac.iittp.cse.dhanvantri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.ac.iittp.cse.dhanvantri.adapter.FeedListAdapter;
import in.ac.iittp.cse.dhanvantri.adapter.FeedListAdapter1;
import in.ac.iittp.cse.dhanvantri.app.AppController;
import in.ac.iittp.cse.dhanvantri.data.FeedItem;


public class Main2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private static final String TAG = Main2Activity.class.getSimpleName();
        private ListView listView, listView1;
        private in.ac.iittp.cse.dhanvantri.adapter.FeedListAdapter listAdapter;
        private FeedListAdapter1 listAdapter1;
        private List<FeedItem> feedItems = new ArrayList<FeedItem>();
        private String URL_FEED = "http://saibaba.96.lt/students.php";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
                    listView = (ListView) rootView.findViewById(R.id.listView);
                    listAdapter = new FeedListAdapter(getActivity(), feedItems);
                    listView.setAdapter(listAdapter);
                    freshRequest();
                    listView.setClickable(true);
                    if(listAdapter.getCount()>0) {
                        Log.d("Feedlist_data", listAdapter.getItem(1).toString());
                    }
                    return rootView;
                case 2:
                    View rootView1 = inflater.inflate(R.layout.fragment_main2_2, container, false);
                    listView1 = (ListView) rootView1.findViewById(R.id.listView1);
                    listAdapter1 = new FeedListAdapter1(getActivity(),feedItems);
                    listView1.setAdapter(listAdapter1);
                    freshRequest();
                    listView1.setClickable(true);
                    return rootView1;
                case 3:
                    return  inflater.inflate(R.layout.fragment_map, container, false);
            }

            return null;
        }
        private void freshRequest(){
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }


        private void parseJsonFeed(JSONObject response) {
            try {
                JSONArray feedArray = response.getJSONArray("feed");
                feedItems.clear();
                for (int i = feedArray.length()-1; i >=0; i--) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);

                    FeedItem item = new FeedItem();
                    item.setId(feedObj.getInt("id"));
                    item.setName(feedObj.getString("name"));
                    item.setSymptoms(feedObj.getString("symptoms"));
                    item.setPrescriptions(feedObj.getString("prescriptions"));
                    feedItems.add(item);

                }
                Log.d("feeditems",feedItems.toString());
                // notify data changes to list adapater
                switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                    case 1:
                        listAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        listAdapter1.notifyDataSetChanged();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //if(position<=2){
                return PlaceholderFragment.newInstance(position + 1);
            //}else{
            //    return Map.newInstance("Map","Nearby Hospitals");
            //}
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "లక్షణాలు";
                case 1:
                    return "మందులు";
                case 2:
                    return "హాస్పిటల్స్";
            }
            return null;
        }
    }


    public void show(View view) {
        Log.d("onClick", "Button is Clicked");
        Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }
}
