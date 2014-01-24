package com.example.myapp;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class Opencvpart extends Activity implements CvCameraViewListener2 {
	
	public native int convertNativeGray(long matAddrRgba, long matAddrGray);

    private Mat mRgba;
    private Mat mGray;
    
    private CameraBridgeViewBase mOpenCvCameraView;
    
    private static final String TAG = "OCVSample::NDK";
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                	System.loadLibrary("nativegray");
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opencvpart);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //setContentView(R.layout.activity_opencvpart);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.opencv_part_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
	}
	
	@Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    public void onCameraViewStarted(int width, int height) {
    	mRgba = new Mat();
    	mGray = new Mat();
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        convertNativeGray(mRgba.getNativeObjAddr(), mGray.getNativeObjAddr());
        return mGray;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opencvpart, menu);
		return true;
	}

}
