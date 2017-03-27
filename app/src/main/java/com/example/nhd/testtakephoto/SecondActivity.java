package com.example.nhd.testtakephoto;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * 调用takephoto裁剪功能
 */
public class SecondActivity extends TakePhotoActivity implements View.OnClickListener {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.pic).setOnClickListener(this);
        findViewById(R.id.photo).setOnClickListener(this);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void onClick(View v) {
        TakePhoto takePhoto = getTakePhoto();
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        switch (v.getId()) {
            case R.id.pic:
                //调用相册并且裁剪
                takePhoto.onPickMultipleWithCrop(1, getCropOptions());
                break;
            case R.id.photo:
                //调用相机并且裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        TImage tImage = result.getImage();
        Glide.with(this).load(new File(tImage.getOriginalPath())).into(image);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    private CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }
}
