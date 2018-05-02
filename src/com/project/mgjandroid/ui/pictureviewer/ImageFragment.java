package com.project.mgjandroid.ui.pictureviewer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.ImageCache;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by yuandi on 2016/11/14.
 */

public class ImageFragment extends Fragment {

    private static final String IMAGE_URL = "image";
    private static final String RESOURCE_ID = "res_Id";
    private PhotoView image;
    private ProgressBar mProgressBar;
    private String imageUrl;
    private int resId;

    public ImageFragment() {

    }

    public static ImageFragment newInstance(String param1, int resId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, param1);
        args.putInt(RESOURCE_ID, resId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE_URL);
            resId = getArguments().getInt(RESOURCE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        image = (PhotoView) view.findViewById(R.id.iv_photo);
        mProgressBar = (ProgressBar) view.findViewById(R.id.photoview_progressbar);
        displayImage();
        image.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    private void displayImage() {
        try {
            loadUrl();
        } catch (OutOfMemoryError e) {
            ImageCache.getInstance().clearMemory();
            try {
                loadUrl();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void loadUrl() {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(resId)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }
}
