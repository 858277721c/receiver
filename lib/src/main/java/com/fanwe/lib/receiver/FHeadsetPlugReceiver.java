package com.fanwe.lib.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 耳机插拔监听
 */
public abstract class FHeadsetPlugReceiver extends FBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.hasExtra("state"))
        {
            int state = intent.getIntExtra("state", 0);
            onHeadsetPlugChange(state == 1);
        }
    }

    /**
     * 耳机插拔回调
     *
     * @param plug true-耳机插入
     */
    protected abstract void onHeadsetPlugChange(boolean plug);

    @Override
    public void register(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        context.registerReceiver(this, filter);
    }
}
