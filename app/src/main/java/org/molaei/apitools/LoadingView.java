package org.molaei.apitools;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.wang.avi.AVLoadingIndicatorView;

import org.molaei.api.Loading;

public class LoadingView implements Loading {
    private Dialog dialog;
    private AVLoadingIndicatorView av;

    public LoadingView(Activity activity)
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        av=(AVLoadingIndicatorView)dialog.findViewById(R.id.avi);
    }

    @Override
    public void start() {
        if(!dialog.isShowing()) {
            dialog.show();
            av.show();
        }
    }

    @Override
    public void stop() {
        if((dialog!=null) && dialog.isShowing()) {
            dialog.dismiss();
            av.hide();
        }
    }
}
