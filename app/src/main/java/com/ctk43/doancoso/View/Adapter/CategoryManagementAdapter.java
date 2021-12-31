package com.ctk43.doancoso.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagementAdapter extends RecyclerView.Adapter<CategoryManagementAdapter.CategoryHolder> implements Filterable {
    private Context mContext;
    private List<Category> categories;
    private List<Category> mlistCategoryOld;
    private CategoryViewModel categoryViewModel;


    public CategoryManagementAdapter(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        //this.categoryViewModel = categoryViewModel;
        this.categories = categories;
        this.mlistCategoryOld = categories;

    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        return new CategoryManagementAdapter.CategoryHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categories.get(position);
        holder.tv_category_name.setText(category.getName());

        /*int numberJobOf = //get jobs number of category
        holder.tv_category_number_job.setText(String.valueOf(numberJobOf));*/

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenDialog(category);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteCategory(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    private void DialogDeleteCategory(Category category) {
        final Dialog dialogYesNo = new Dialog(mContext);
        DialogExtension.dialogYesNo(dialogYesNo, mContext.getString(R.string.confirm_delete), mContext.getString(R.string.message_delete_category));
        Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
        Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
        dialogYesNo.setCancelable(true);
        btn_yes.setOnClickListener(v -> {
            //delete category
            Toast.makeText(mContext, "Hieu friend let delete category hé", Toast.LENGTH_LONG).show();
            dialogYesNo.dismiss();
        });
        btn_no.setOnClickListener(v -> dialogYesNo.dismiss());
        dialogYesNo.show();
    }

    private void onOpenDialog(Category category) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.floating_dialog_add_job_type);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);
        TextView tv_title = dialog.findViewById(R.id.tv_add_category_title);
        tv_title.setText(R.string.category_title);

        EditText edt_job_type_name = dialog.findViewById(R.id.edt_dlg_job_type);
        edt_job_type_name.setText(category.getName());

        Button btn_add_job_type = dialog.findViewById(R.id.btn_dlg_add_job_type);
        btn_add_job_type.setOnClickListener(view -> {
            //xu ly update category
            //categoryViewModel.insert(new Category(edt_job_type_name.getText().toString(), DataLocalManager.getEmail()));
            Toast.makeText(mContext, "Hieu Friend make update hé!", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if(strsearch.isEmpty()){
                    categories = mlistCategoryOld;
                }else{
                    List<Category> list = new ArrayList<>();
                    for (Category j : mlistCategoryOld){
                        if(j.getName().toLowerCase().contains(strsearch.toLowerCase())){
                            list.add(j);
                        }
                    }
                    categories = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = categories;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categories = (List<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        TextView tv_category_name;
        TextView tv_category_number_job;
        SwipeRevealLayout swipeRevealLayout;

        FrameLayout delete;
        FrameLayout update;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.item_category_topic);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            tv_category_number_job =itemView.findViewById(R.id.tv_category_number_job);

            delete = itemView.findViewById(R.id.frm_function_delete);
            update = itemView.findViewById(R.id.frm_function_update);

        }
    }
}