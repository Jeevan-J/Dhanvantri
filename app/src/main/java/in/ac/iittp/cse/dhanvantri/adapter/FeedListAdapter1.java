package in.ac.iittp.cse.dhanvantri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.ac.iittp.cse.dhanvantri.R;
import in.ac.iittp.cse.dhanvantri.data.FeedItem;

/**
 * Created by jeevan on 12-08-2017.
 */

public class FeedListAdapter1 extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    public FeedListAdapter1(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert inflater != null;
            convertView = inflater.inflate(R.layout.feed_item1, parent,false);
        }


        TextView name = (TextView) convertView.findViewById(R.id.name1);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);

        FeedItem item = feedItems.get(position);

        name.setText(item.getName());


        /*// Chcek for empty status message
        if (!TextUtils.isEmpty(item.getSymptoms())) {
            statusMsg.setText(item.getSymptoms());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }*/

		// Checking for null feed url
		if (item.getPrescriptions() != null) {
			url.setText(item.getPrescriptions() );
			url.setVisibility(View.VISIBLE);
		} else {
			// url is null, remove from the view
			url.setVisibility(View.GONE);
		}


        return convertView;
    }

}
