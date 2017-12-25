package com.fanwe.lib.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络变化监听
 * <br>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public abstract class FNetworkReceiver extends FBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action))
        {
            int type = getNetworkType(context);
            onNetworkChanged(type);
        }
    }

    /**
     * 网络变化监听
     *
     * @param type {@link ConnectivityManager}
     */
    protected abstract void onNetworkChanged(int type);

    @Override
    public void register(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this, filter);
    }

    //----------utils----------

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null)
        {
            return info.isConnected();
        } else
        {
            return false;
        }
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return false;
        } else
        {
            return ConnectivityManager.TYPE_WIFI == networkInfo.getType();
        }
    }

    /**
     * 获得网络类型
     *
     * @param context
     * @return {@link ConnectivityManager}
     */
    public static int getNetworkType(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null)
        {
            return -1;
        } else
        {
            int type = networkInfo.getType();
            return type;
        }
    }
}
