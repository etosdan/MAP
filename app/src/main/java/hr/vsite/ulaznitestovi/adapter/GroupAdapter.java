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
        Group group = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
            viewHolder.groupNameTextView = convertView.findViewById(R.id.groupNameTextView);
            viewHolder.authorNameTextView = convertView.findViewById(R.id.authorNameTextView);
            viewHolder.memberCountTextView = convertView.findViewById(R.id.memberCountTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.groupNameTextView.setText(group.getGroupName());
        viewHolder.authorNameTextView.setText("Author: " + group.getAuthorId());

        // Set the member count
        int memberCount = group.getUserIds().size();
        String memberCountText = "Members: " + memberCount;
        viewHolder.memberCountTextView.setText(memberCountText);

        return convertView;
    }

    private static class ViewHolder {
        TextView groupNameTextView;
        TextView authorNameTextView;
        TextView memberCountTextView;
    }
}
