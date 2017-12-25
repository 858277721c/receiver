package com.fanwe.receiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fanwe.lib.receiver.FHeadsetPlugReceiver;
import com.fanwe.lib.receiver.FNetworkReceiver;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadsetPlugReceiver.register(this);
        mNetworkReceiver.register(this);
    }

    private FHeadsetPlugReceiver mHeadsetPlugReceiver = new FHeadsetPlugReceiver()
    {
        @Override
        protected void onHeadsetPlugChange(boolean plug)
        {
            Log.i(TAG, "onHeadsetPlugChange:" + plug);
        }
    };

    private FNetworkReceiver mNetworkReceiver = new FNetworkReceiver()
    {
        @Override
        protected void onNetworkChanged(int type)
        {
            Log.i(TAG, "onNetworkChanged:" + type);
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHeadsetPlugReceiver.unregister(this);
        mNetworkReceiver.unregister(this);
    }
}
