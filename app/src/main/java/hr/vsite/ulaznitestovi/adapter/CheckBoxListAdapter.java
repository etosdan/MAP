package hr.vsite.ulaznitestovi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.models.User;

public class CheckBoxListAdapter extends RecyclerView.Adapter<CheckBoxListAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;
    private Map<String, Boolean> checkedItems;

    public CheckBoxListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.checkedItems = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        String fullName = user.getName() + " " + user.getSurname();
        holder.textView.setText(fullName);
        holder.checkBox.setChecked(checkedItems.containsKey(user.getUserId()));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedItems.put(user.getUserId(), true);
            } else {
                checkedItems.remove(user.getUserId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public List<String> getSelectedUserIds() {
        return new ArrayList<>(checkedItems.keySet());
    }

    public void setChecked(User user, boolean checked) {
        if (checked) {
            checkedItems.put(user.getUserId(), true);
        } else {
            checkedItems.remove(user.getUserId());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
            checkBox = itemView.findViewById(R.id.itemCheckBox);
        }
    }
}
