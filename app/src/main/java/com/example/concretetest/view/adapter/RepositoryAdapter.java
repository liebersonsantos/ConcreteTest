package com.example.concretetest.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concretetest.R;
import com.example.concretetest.model.repositories.Item;
import com.example.concretetest.util.ImageUtil;
import com.example.concretetest.view.Navigation;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Item> repositories;
    private Navigation navigation = new Navigation();

    public RepositoryAdapter(List<Item> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new RepositoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repositories.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = navigation.goToPullRequestActivity((Activity) v.getContext(), repositories.get(position));
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return (repositories != null && repositories.size() > 0) ? repositories.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameRepository;
        private TextView descriptionRepository;
        private TextView numberForks;
        private TextView numberStars;
        private TextView userName;
        private ProgressBar progress;
        private CircleImageView imageUser;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameRepository = itemView.findViewById(R.id.repository_name);
            descriptionRepository = itemView.findViewById(R.id.description_repository);
            numberForks = itemView.findViewById(R.id.number_forks);
            numberStars = itemView.findViewById(R.id.number_star);
            userName = itemView.findViewById(R.id.user_name_main);
            progress = itemView.findViewById(R.id.progress);
            imageUser = itemView.findViewById(R.id.circle_image_main);
            cardView = itemView.findViewById(R.id.card_main);
        }

        public void bind(Item item) {

            nameRepository.setText(item.getName());
            descriptionRepository.setText(item.getDescription() + "...");
            numberForks.setText(String.valueOf(item.getForks()));
            numberStars.setText(String.valueOf(item.getStargazersCount()));
            userName.setText(item.getOwner().getLogin());
            ImageUtil.loadImage(item.getOwner().getAvatarUrl(), imageUser, progress);
        }

    }
    public void update(List<Item> repositories) {
        this.repositories.addAll(repositories);
        notifyDataSetChanged();
    }
}