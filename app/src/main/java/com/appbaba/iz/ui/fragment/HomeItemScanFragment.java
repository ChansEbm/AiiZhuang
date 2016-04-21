package com.appbaba.iz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appbaba.iz.FragmentHomeBinding;
import com.appbaba.iz.FragmentHomeTopScanBinding;
import com.appbaba.iz.R;
import com.appbaba.iz.TopTitleBinding;
import com.appbaba.iz.base.BaseFgm;
import com.appbaba.iz.entity.main.album.CasesAttrSelection;
import com.appbaba.iz.method.MethodConfig;
import com.appbaba.iz.ui.activity.album.EffectActivity;
import com.appbaba.iz.ui.activity.album.ProductActivity;
import com.appbaba.iz.ui.fragment.Comm.CommWebviewFragment;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.Size;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ruby on 2016/4/5.
 */
public class HomeItemScanFragment extends BaseFgm {

    private FragmentHomeTopScanBinding topScanBinding;
    private TextView tv_top_simple_title,iv_back;
    private CompoundBarcodeView barcodeView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            if (result.getText() != null) {
               GetID(result.getText());
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public  void  GetID(String path)
    {
        String cases_id = Uri.parse(path).getQueryParameter("cases_id");
        String product_id = Uri.parse(path).getQueryParameter("product_id");
        if(!TextUtils.isEmpty(cases_id))
        {
            CasesAttrSelection selection = new CasesAttrSelection();
            selection.setCateId(cases_id);
            Bundle bundle = new Bundle();
            bundle.putParcelable("selection", selection);
            start(bundle, EffectActivity.class);
            ((Activity)getContext()).finish();
        }
        else if(!TextUtils.isEmpty(product_id))
        {
            CasesAttrSelection selection = new CasesAttrSelection();
            selection.setCateId(product_id);
            Bundle bundle = new Bundle();
            bundle.putParcelable("selection", selection);
            start(bundle, ProductActivity.class);
            ((Activity)getContext()).finish();
        }
        else
        {
            CommWebviewFragment commWebviewFragment = new CommWebviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("which", -1);
            bundle.putString("title", "扫一扫");
            bundle.putString("value", path);
            commWebviewFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.layout_contain, commWebviewFragment).commit();
        }

    }
    @Override
    protected void initViews() {
         topScanBinding = (FragmentHomeTopScanBinding)viewDataBinding;
         barcodeView = topScanBinding.barcodeScanner;
         tv_top_simple_title = topScanBinding.includeTopSimpleTitle.tvTopSimpleTitle;
         iv_back = topScanBinding.includeTopSimpleTitle.Back;
         tv_top_simple_title.setText(R.string.popup_erweima);
         barcodeView.getBarcodeView().setFramingRectSize(new Size(MethodConfig.metrics.widthPixels/2,MethodConfig.metrics.widthPixels/2));
         barcodeView.decodeContinuous(callback);

    }

    @Override
    public void onResume() {
        super.onResume();
         barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            String contents = intent.getStringExtra("SCAN_RESULT");		//图像内容
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");    //图像格式

            Log.e("tt",contents+"   ####    "+format);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // Handle cancel
        }
    }

    @Override
    protected void initEvents() {
       iv_back.setOnClickListener(this);
    }

    @Override
    protected void noNetworkStatus() {

    }

    @Override
    protected void onClick(int id, View view) {
        switch (id)
        {
            case R.id.iv_back:
                ((Activity)getContext()).finish();
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_item_scan;
    }
}
