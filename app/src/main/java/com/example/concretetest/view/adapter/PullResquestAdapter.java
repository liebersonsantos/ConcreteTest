package com.example.concretetest.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.concretetest.R;
import com.example.concretetest.model.pullRequests.PullResquestResponse;
import com.example.concretetest.util.ImageUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PullResquestAdapter extends RecyclerView.Adapter<PullResquestAdapter.ViewHolder> {

    private List<PullResquestResponse> responseList;

    public PullResquestAdapter(List<PullResquestResponse> responseList){
        this.responseList = responseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pull_request, parent, false);

        return new PullResquestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(responseList.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(responseList.get(position).getHtml_url()));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (responseList != null && responseList.size() > 0 ? responseList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView descriptionText;
        private TextView userName;
        private TextView datePull;
        private ProgressBar progressBar;
        private CircleImageView imageUser;

        public ViewHolder(View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.pull_request_title);
            descriptionText = itemView.findViewById(R.id.pull_resquest_description);
            userName = itemView.findViewById(R.id.user_name_pull);
            datePull = itemView.findViewById(R.id.pull_date);
            progressBar = itemView.findViewById(R.id.progress_pull);
            imageUser = itemView.findViewById(R.id.image_user_pull);
        }

        public void bind(PullResquestResponse resquestResponse){
            titleText.setText(resquestResponse.getTitle());
            descriptionText.setText(resquestResponse.getBody() + "...");
            userName.setText(resquestResponse.getUser().getLogin());

            formattingDate(resquestResponse);
            ImageUtil.loadImage(resquestResponse.getUser().getAvatar_url(), imageUser, progressBar);

        }

        private void formattingDate(PullResquestResponse resquestResponse) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date date = dateFormat.parse(resquestResponse.getCreated_at());//You will get date object relative to server/client timezone wherever it is parsed
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
                String dateStr = formatter.format(date);
                datePull.setText(dateStr);
            }catch(Exception e){
                Log.i("TAG", "bind: " + e.getMessage());
            }
        }

    }

    public void update(List<PullResquestResponse> repositories) {
        this.responseList.clear();
        this.responseList.addAll(repositories);
        notifyDataSetChanged();
    }
}
