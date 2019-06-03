package com.example.test.bitpanda.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.bitpanda.R;
import com.example.test.bitpanda.Utils.UtilsGson;
import com.example.test.bitpanda.databinding.FragmentRepositoryDetailsBinding;
import com.example.test.bitpanda.dagger.Injectable;
import com.example.test.bitpanda.service.model.Contributor;
import com.example.test.bitpanda.service.model.Repository;
import com.example.test.bitpanda.view.adapter.ContributorsAdapter;
import com.example.test.bitpanda.viewmodel.ContributorsListViewModel;
import com.example.test.bitpanda.viewmodel.RepositoryDetailsViewModel;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class RepositoryDetailsFragment  extends Fragment implements Injectable {

    private static final String KEY_PROJECT_ID = "repository_id";
    private FragmentRepositoryDetailsBinding binding;
    private ContributorsAdapter contributorsAdapter;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository_details, container, false);

        contributorsAdapter=new ContributorsAdapter();
        binding.contributorsList.setAdapter(contributorsAdapter);
        binding.setIsLoading(true);

        // Create and set the adapter for the RecyclerView.
        return (View) binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            //Loading selected repo list
            final RepositoryDetailsViewModel viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(RepositoryDetailsViewModel.class);

            viewModel.setRepositoryID(getArguments().getString(KEY_PROJECT_ID));
            String Repos = getArguments().get("selected_repo").toString();
            viewModel.setRepository(UtilsGson.getGsonParser().fromJson(Repos, Repository.class));
            binding.setRepositoryDetailsViewModel(viewModel);
            observeViewModel(viewModel);

            //Loading contributor list
            final ContributorsListViewModel contributorsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContributorsListViewModel.class);
            contributorsListViewModel.setContributorLoginId(UtilsGson.getGsonParser().fromJson(Repos, Repository.class).owner.login);
            contributorsListViewModel.setContributorRepoName(UtilsGson.getGsonParser().fromJson(Repos, Repository.class).name);
            observeViewModel(contributorsListViewModel);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }



    private void observeViewModel(RepositoryDetailsViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getObservableRepository().observe(this, new Observer<Repository>() {
            @Override
            public void onChanged(@Nullable Repository repository) {
                if (repository != null) {
                    binding.setIsLoading(false);
                    viewModel.setRepository(repository);
                }else
                {
                    Toast.makeText(getActivity(), "No results found..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void observeViewModel(ContributorsListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getContributorsListObservable().observe(this, new Observer<List<Contributor>>() {
            @Override
            public void onChanged(@Nullable List<Contributor> contributors) {
                if (contributors != null) {
                    binding.setIsLoading(false);
                    contributorsAdapter.setContributorList(contributors);
                    Log.e("contributors size", String.valueOf(contributors.size()));
                }else
                {
                    Toast.makeText(getActivity(), "No results found..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /** Creates project fragment for specific repositoryId */
    public static RepositoryDetailsFragment forProject(Repository repository) {
        RepositoryDetailsFragment fragment = new RepositoryDetailsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PROJECT_ID, repository.full_name);
        args.putString("selected_repo", UtilsGson.getGsonParser().toJson(repository));
        fragment.setArguments(args);
        return fragment;
    }

}

