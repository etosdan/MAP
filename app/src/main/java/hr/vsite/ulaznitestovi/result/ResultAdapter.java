package hr.vsite.ulaznitestovi.result;// ResultAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import hr.vsite.ulaznitestovi.R;

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private List<Result> resultList;

    public ResultAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_result, parent, false);
        }

        Result result = resultList.get(position);

        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView resultStatusTextView = convertView.findViewById(R.id.resultStatusTextView);

        userNameTextView.setText(result.getUserName());
        resultStatusTextView.setText(result.getResultStatus());

        return convertView;
    }
}
