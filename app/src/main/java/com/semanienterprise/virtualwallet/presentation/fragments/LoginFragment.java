package com.semanienterprise.virtualwallet.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.semanienterprise.virtualwallet.R;
import com.semanienterprise.virtualwallet.databinding.FragmentLoginBinding;
import com.semanienterprise.virtualwallet.presentation.MainActivity;
import com.semanienterprise.virtualwallet.presentation.MainActivityViewModel;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private MainActivityViewModel viewModel;
    private NavController controller;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        viewModel = ((MainActivity) requireActivity()).getMainActivityViewModel();

        controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        setObservers();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClickListeners();
    }

    private void setObservers() {
        viewModel.getShowToast().observe(this, showToast -> {
            if (showToast) {
                Toast.makeText(getActivity(), viewModel.getToastMessage(), Toast.LENGTH_LONG).show();
                viewModel.toastShown();
            }
        });
        viewModel.getNavigateToFundingFragment().observe(this, toNavigate -> {
            if (toNavigate) {
                //Navigate To fund Fragment
                controller.navigate(LoginFragmentDirections.actionLoginFragmentToFundWalletFragment());
            }
        });
    }

    private void setOnClickListeners() {
        binding.loginBtn.setOnClickListener(view -> viewModel.isUsernameAndPasswordValid(Objects.requireNonNull(binding.usernameEditText.getText()).toString(),
                Objects.requireNonNull(binding.passwordEditText.getText()).toString()));
    }
}