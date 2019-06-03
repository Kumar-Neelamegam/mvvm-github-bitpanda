package com.example.test.bitpanda.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.test.bitpanda.R;
import com.example.test.bitpanda.databinding.RepositoryListItemBinding;
import com.example.test.bitpanda.service.model.Repository;
import com.example.test.bitpanda.view.callback.RepositoryClickCallback;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    List<? extends Repository> repositoryList;

    @Nullable
    private final RepositoryClickCallback repositoryClickCallback;

    public RepositoryAdapter(@Nullable RepositoryClickCallback repositoryClickCallback) {
        this.repositoryClickCallback = repositoryClickCallback;
    }

    public void setRepositoryList(final List<? extends Repository> repositoryList) {
        if (this.repositoryList == null) {
            this.repositoryList = repositoryList;
            notifyItemRangeInserted(0, repositoryList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RepositoryAdapter.this.repositoryList.size();
                }

                @Override
                public int getNewListSize() {
                    return repositoryList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RepositoryAdapter.this.repositoryList.get(oldItemPosition).id ==
                            repositoryList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Repository repository = repositoryList.get(newItemPosition);
                    Repository old = repositoryList.get(oldItemPosition);
                    return repository.id == old.id
                            && Objects.equals(repository.git_url, old.git_url);
                }
            });
            this.repositoryList = repositoryList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RepositoryListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.repository_list_item,
                        parent, false);

        binding.setCallback(repositoryClickCallback);

        return new RepositoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.binding.setRepository(repositoryList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return repositoryList == null ? 0 : repositoryList.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        final RepositoryListItemBinding binding;

        public RepositoryViewHolder(RepositoryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
