package com.estimote.proximity.estimote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.proximity.R;

import java.util.ArrayList;
import java.util.List;

public class ProximityContentAdapter extends BaseAdapter {

    private Context context;

    public ProximityContentAdapter(Context context) {
        this.context = context;
    }

    private List<ProximityContent> nearbyContentList = new ArrayList<>();

    void setNearbyContent(List<ProximityContent> nearbyContent) {
        this.nearbyContentList = nearbyContent;
    }

    @Override
    public int getCount() {
        return nearbyContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;

            convertView = inflater.inflate(R.layout.content_view, parent, false);
        }

        ProximityContent content = nearbyContentList.get(position);

        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);

        title.setText(content.getTitle());
        subtitle.setText(content.getSubtitle());

        convertView.setBackgroundColor(Utils.getEstimoteColor(content.getTitle()));

        return convertView;
    }


}
