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
import java.util.List;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.models.User;

public class CheckBoxListAdapter extends RecyclerView.Adapter<CheckBoxListAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;
    private boolean[] checkedItems;

    public CheckBoxListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.checkedItems = new boolean[userList.size()];
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
        holder.checkBox.setChecked(checkedItems[position]);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    checkedItems[adapterPosition] = isChecked;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public boolean[] getCheckedItems() {
        return checkedItems;
    }

    public List<String> getSelectedUserIds() {
        List<String> selectedUserIds = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            if (checkedItems[i]) {
                selectedUserIds.add(userList.get(i).getUserId());
            }
        }
        return selectedUserIds;
    }
    public List<User> getSelectedUsers() {
        List<User> selectedUsers = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            if (checkedItems[i]) {
                selectedUsers.add(userList.get(i));
            }
        }
        return selectedUsers;
    }
    public void setChecked(User user, boolean checked) {
        int position = userList.indexOf(user);
        if (position != -1) {
            checkedItems[position] = checked;
            notifyItemChanged(position);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
            checkBox = itemView.findViewById(R.id.itemCheckBox);
        }
    }
}
