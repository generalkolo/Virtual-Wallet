package com.semanienterprise.virtualwallet.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.semanienterprise.virtualwallet.R;
import com.semanienterprise.virtualwallet.databinding.FragmentFundWalletBinding;
import com.semanienterprise.virtualwallet.presentation.MainActivity;
import com.semanienterprise.virtualwallet.presentation.MainActivityViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundWalletFragment extends Fragment {
    private FragmentFundWalletBinding binding;
    private MainActivityViewModel viewModel;
    private NavController controller;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fund_wallet, container, false);

        viewModel = ((MainActivity) requireActivity()).getMainActivityViewModel();
        controller = NavHostFragment.findNavController(this);

        setOnClickListeners();
        setObservers();
        return binding.getRoot();
    }

    private void setObservers() {
        viewModel.getShowToast().observe(this, showToast -> {
            if (showToast) {
                Toast.makeText(getActivity(), viewModel.getToastMessage(), Toast.LENGTH_LONG).show();
                viewModel.toastShown();
            }
        });
        viewModel.getLoggedInUser().observe(this, user -> {
            binding.accountBalance.setText(getString(R.string.naira_formatter, user.accountBalance.toString()));
            binding.fundsEditText.setText("");
        });
    }

    private void setOnClickListeners() {
        binding.topUpBtn.setOnClickListener(view -> viewModel.topUpUserAccount(Objects.requireNonNull(binding.fundsEditText.getText()).toString()));
        binding.logOutBtn.setOnClickListener(view -> {
            viewModel.navigatedToFundingFragment();
            NavDirections action = FundWalletFragmentDirections.actionFundWalletFragmentToLoginFragment();
            controller.navigate(action);
        });
    }
}
