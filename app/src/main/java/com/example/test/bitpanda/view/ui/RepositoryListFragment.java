package com.example.test.bitpanda.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.bitpanda.R;
import com.example.test.bitpanda.databinding.FragmentRepositoryListBinding;
import com.example.test.bitpanda.dagger.Injectable;
import com.example.test.bitpanda.service.model.Repository;
import com.example.test.bitpanda.view.adapter.RepositoryAdapter;
import com.example.test.bitpanda.view.callback.RepositoryClickCallback;
import com.example.test.bitpanda.viewmodel.RepositoryListViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class RepositoryListFragment extends Fragment implements Injectable {

    private RepositoryAdapter repositoryAdapter;
    private FragmentRepositoryListBinding binding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository_list, container, false);

        repositoryAdapter = new RepositoryAdapter(repositoryClickCallback);
        binding.projectList.setAdapter(repositoryAdapter);
        binding.setIsLoading(true);

        return (View) binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RepositoryListViewModel viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(RepositoryListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(RepositoryListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRepositoryListObservable().observe(this, new Observer<List<Repository>>() {
            @Override
            public void onChanged(@Nullable List<Repository> repositories) {
                if (repositories != null) {
                    binding.setIsLoading(false);
                    repositoryAdapter.setRepositoryList(repositories);
                    Log.e("size", String.valueOf(repositories.size()));
                }
            }
        });
    }


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private final RepositoryClickCallback repositoryClickCallback = new RepositoryClickCallback() {
        @Override
        public void onClick(Repository repository) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((Dashboard) getActivity()).show(repository);
                Log.e("Repository Click: ", repository.full_name);
            }
        }
    };
}
