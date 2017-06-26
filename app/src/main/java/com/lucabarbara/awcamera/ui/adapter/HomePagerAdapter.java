package com.lucabarbara.awcamera.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lucabarbara.awcamera.ui.fragment.GalleryFragment;
import com.lucabarbara.awcamera.ui.fragment.PhotoFragment;
import com.lucabarbara.awcamera.ui.fragment.VideoFragment;

/**
 * Created by luca1897 on 23/06/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public GalleryFragment mGalleryFragment;
    public PhotoFragment mPhotoFragment;
    public VideoFragment mVideoFragment;

    public HomePagerAdapter(FragmentManager fm) {

        super(fm);
        if(mGalleryFragment ==null)
            mGalleryFragment = GalleryFragment.newInstance(0);
        if(mPhotoFragment ==null)
            mPhotoFragment = PhotoFragment.newInstance(1);
        if(mVideoFragment ==null)
            mVideoFragment = VideoFragment.newInstance(2);
    }


    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position)
        {
            case 0:
                if(mGalleryFragment ==null)
                    mGalleryFragment = GalleryFragment.newInstance(0);
                return mGalleryFragment;
            case 1:
                if(mPhotoFragment==null)
                    mPhotoFragment = PhotoFragment.newInstance(1);
                return mPhotoFragment;
            case 2:
                if(mVideoFragment==null)
                    mVideoFragment = VideoFragment.newInstance(0);
                return mVideoFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Gallery";
            case 1:
                return "Photo";
            case 2:
                return "Video";
        }
        return null;
    }
}