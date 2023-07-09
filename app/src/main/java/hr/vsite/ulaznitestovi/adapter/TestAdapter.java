package hr.vsite.ulaznitestovi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.models.Test;

public class TestAdapter extends ArrayAdapter<Test> {

    private static class ViewHolder {
        TextView testNameTextView;
        TextView testAuthorTextView;
    }

    public TestAdapter(Context context, List<Test> tests) {
        super(context, 0, tests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Test test = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_test, parent, false);
            viewHolder.testNameTextView = convertView.findViewById(R.id.testNameTextView);
            viewHolder.testAuthorTextView = convertView.findViewById(R.id.testAuthorTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.testNameTextView.setText(test.getTestName());
        viewHolder.testAuthorTextView.setText(test.getAuthorId());

        return convertView;
    }
}
