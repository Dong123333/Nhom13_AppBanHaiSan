package com.example.nhom13_appbanhaisan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nhom13_appbanhaisan.Fragment.CancelledFragment;
import com.example.nhom13_appbanhaisan.Fragment.DeliveredFragment;
import com.example.nhom13_appbanhaisan.Fragment.DeliveryFragment;
import com.example.nhom13_appbanhaisan.Fragment.WaitForConfirmationFragment;
import com.example.nhom13_appbanhaisan.Fragment.WaitingForDeliveryFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new WaitForConfirmationFragment();
            case 1: return new WaitingForDeliveryFragment();
            case 2: return new DeliveryFragment();
            case 3: return new DeliveredFragment();
            case 4: return new CancelledFragment();
            default:
                return new WaitForConfirmationFragment();
        }

    }
    @Override
    public int getItemCount() {
        return 5;
    }
}
