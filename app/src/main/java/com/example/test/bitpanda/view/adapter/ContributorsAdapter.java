package com.example.test.bitpanda.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.test.bitpanda.R;
import com.example.test.bitpanda.databinding.ContributorListItemBinding;
import com.example.test.bitpanda.service.model.Contributor;
import java.util.List;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.ContributorViewHolder> {

    List<? extends Contributor> contributorsList;

    public ContributorsAdapter() {
    }

    public void setContributorList(final List<? extends Contributor> contributorsList) {
        if (this.contributorsList == null) {
            this.contributorsList = contributorsList;
            notifyItemRangeInserted(0, contributorsList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ContributorsAdapter.this.contributorsList.size();
                }

                @Override
                public int getNewListSize() {
                    return contributorsList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ContributorsAdapter.this.contributorsList.get(oldItemPosition).id ==
                            contributorsList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Contributor contributor = contributorsList.get(newItemPosition);
                    Contributor old = contributorsList.get(oldItemPosition);
                    return contributor.id == old.id
                            && Objects.equals(contributor.login, old.login);
                }
            });
            this.contributorsList = contributorsList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ContributorListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.contributor_list_item,
                        parent, false);

        return new ContributorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        holder.binding.setContributor(contributorsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return contributorsList == null ? 0 : contributorsList.size();
    }

    static class ContributorViewHolder extends RecyclerView.ViewHolder {

        final ContributorListItemBinding binding;

        public ContributorViewHolder(ContributorListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
