package com.savita.nasa_app.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.savita.nasa_app.ApodFragment;
import com.savita.nasa_app.net.ApodController;

import java.util.function.Consumer;

public class ApodPageAdapter extends FragmentStateAdapter {
    private Consumer<Integer> onSaveClicked;
    public ApodPageAdapter(@NonNull FragmentActivity fragmentActivity, Consumer<Integer> onSaveClicked) {
        super(fragmentActivity);
        this.onSaveClicked = onSaveClicked;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ApodFragment fragment = ApodFragment.newInstance(position);
        fragment.setOnSaveClicked(this.onSaveClicked);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return ApodController.items.size();
    }
}
