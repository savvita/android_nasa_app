package com.savita.nasa_app.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.savita.nasa_app.NeoFragment;
import com.savita.nasa_app.net.NeoController;

import java.util.function.Consumer;

public class NeoPageAdapter extends FragmentStateAdapter {
    private Consumer<Integer> onSeeAllClicked;
    public NeoPageAdapter(@NonNull FragmentActivity fragmentActivity, Consumer<Integer> onSeeAllClicked) {
        super(fragmentActivity);
        this.onSeeAllClicked = onSeeAllClicked;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        NeoFragment fragment = NeoFragment.newInstance(position);
        fragment.setOnSeeAllClicked(this.onSeeAllClicked);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NeoController.items.size();
    }
}
