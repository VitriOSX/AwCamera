package com.lucabarbara.awcamera.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.lucabarbara.awcamera.R;
import com.lucabarbara.awcamera.ui.adapter.GalleryAdapter;
import com.lucabarbara.awcamera.utils.PermissionsDelegate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luca1897 on 23/06/17.
 */

public class GalleryFragment extends Fragment {

    @BindView(R.id.grid_gallery)
    GridView mGridGallery;
    @BindView(R.id.parallax_header_imageview)
    ImageView mImageParallaxHeader;

    private GalleryAdapter galleryAdapter;
    private List<String> images = new ArrayList<>();

    private static final String ARG_SECTION_NUMBER = "section_number";

    public final int REQUEST_PERMISSION_EXTERNAL_STORAGE = 0;
    private PermissionsDelegate permissionsDelegate;
    public GalleryFragment() {
    }

    public static GalleryFragment newInstance(int sectionNumber) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this,rootView);
        permissionsDelegate = new PermissionsDelegate(getActivity());
        initGridGallery();
        return rootView;
    }

    private void initGridGallery()
    {
        ViewCompat.setNestedScrollingEnabled(mGridGallery,true);

        galleryAdapter = new GalleryAdapter(this, images);
        try {
            setImagesOnAdapter();
        }catch(SecurityException e){
            requestPermissionExternalStorage();
        }

        mGridGallery.setAdapter(galleryAdapter);
    }

    public void setImagesOnAdapter()
    {
        images.clear();
        images.add("ic_gallery");
        images.addAll(getCameraImages());
        if(images.size()>1)
        {
            galleryAdapter.setImageSelected(1);
            setImageParallaxHeader(images.get(1));
        }
        galleryAdapter.notifyDataSetChanged();
    }

    public void setImageParallaxHeader(String image)
    {
        Picasso.with(getActivity()).load(image).fit().centerCrop()
                .into(mImageParallaxHeader);
    }

    /**
     * find last 7 picture from ic_gallery
     */
    public List<String> getCameraImages() {
        List<String> imgs = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = getActivity().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        int i = 0;
        if(cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                imgs.add("file:" + cursor.getString(dataColumn));
                i++;
            } while (cursor.moveToNext() && i < 30);
        }
        return imgs;
    }

    /**
     * check external storage and camera permission
     */
    public void requestPermissionExternalStorage()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_EXTERNAL_STORAGE);
            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initGridGallery();
            } else {
                // User refused to grant permission.
            }
        }
    }

}