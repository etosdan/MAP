package hr.vsite.ulaznitestovi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.models.Group;

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Context context, List<Group> groups) {
        super(context, 0, groups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Group group = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
        }

        // Lookup view for data population
        TextView groupNameTextView = convertView.findViewById(R.id.groupNameTextView);
        TextView groupAuthorTextView = convertView.findViewById(R.id.authorNameTextView);

        // Populate the data into the template view using the data object
        groupNameTextView.setText(group.getGroupName());
        groupAuthorTextView.setText("Author: " + group.getAuthorId());

        // Return the completed view to render on screen
        return convertView;
    }
}
