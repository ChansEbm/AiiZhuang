package com.appbaba.platform.ui.activity.user;

import android.databinding.ViewDataBinding;
import android.graphics.Path;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbaba.platform.ActivityInspirationUploadBinding;
import com.appbaba.platform.ItemInspirationDesignViewBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseActivity;
import com.appbaba.platform.entity.comm.InspirationPhotoBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.eum.PhotoPopupOpts;
import com.appbaba.platform.impl.OnPhotoOptsSelectListener;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.widget.PopupWindow.TakePhotoPopupWindow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by ruby on 2016/6/12.
 */
public class InspirationUploadActivity extends BaseActivity {
    private ActivityInspirationUploadBinding binding;

    private CommonBinderAdapter<InspirationPhotoBean> adapter;
    private List<InspirationPhotoBean> list;

    private int height = 0;

    @Override
    protected void InitView() {
          binding = (ActivityInspirationUploadBinding)viewDataBinding;
        binding.recycle.setLayoutManager(new LinearLayoutManager(this));
        binding.recycle.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void InitData() {
        height = MethodConfig.GetHeight(MethodConfig.metrics.widthPixels,16,9);
        list = new ArrayList<>();
        adapter =  new CommonBinderAdapter<InspirationPhotoBean>(this,R.layout.item_inspiration_design_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, InspirationPhotoBean inspirationPhotoBean) {
                ItemInspirationDesignViewBinding itemInspirationDesignViewBinding = (ItemInspirationDesignViewBinding)viewDataBinding;
                Picasso.with(InspirationUploadActivity.this).load(Uri.parse("file://"+inspirationPhotoBean.getImageUrl())).resize(MethodConfig.metrics.widthPixels,height).into(itemInspirationDesignViewBinding.ivItem);
                itemInspirationDesignViewBinding.ivItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                itemInspirationDesignViewBinding.edtItem.setText(inspirationPhotoBean.getDetail());
            }
        };
        binding.recycle.setAdapter(adapter);

    }

    @Override
    protected void InitEvent() {

    }

    @Override
    protected void InitListening() {
        binding.tvAdd.setOnClickListener(this);
          binding.tvUpload.setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
          switch (id)
          {
              case R.id.tv_add:
              {
                      TakePhoto();
              }
                  break;
              case R.id.tv_upload:
              {
                  networkModel.UploadInspiration(MethodConfig.userInfo.getToken(),binding.edtTitle.getText().toString(),binding.edtSub.getText().toString().trim(),
                          binding.edtMark.getText().toString().trim(),list, NetworkParams.CUPCAKE);
              }
                  break;
          }
    }

    public void TakePhoto()
    {
        TakePhotoPopupWindow window = new TakePhotoPopupWindow(this);
        window.setOnPhotoOptsSelectListener(new OnPhotoOptsSelectListener() {
            @Override
            public void onOptsSelect(PhotoPopupOpts opts) {
                if(opts==PhotoPopupOpts.TAKE_PHOTO)
                {
                    GalleryFinal.openCamera(101, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            if(reqeustCode==101 && resultList.size()>0)
                            {
                                InspirationPhotoBean bean = new InspirationPhotoBean();

                                String path = resultList.get(0).getPhotoPath();
                                bean.setImageUrl(path);
                                bean.setDetail("sdfsdf");
                                list.add(bean);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {

                        }
                    });
                }
                else
                {
                    GalleryFinal.openGallerySingle(100, new GalleryFinal.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                            if(reqeustCode==100 && resultList.size()>0)
                            {

                                InspirationPhotoBean bean = new InspirationPhotoBean();
                                String path = resultList.get(0).getPhotoPath();
                                bean.setImageUrl(path);
                                bean.setDetail("sdfsdf");
                                list.add(bean);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onHanlderFailure(int requestCode, String errorMsg) {

                        }
                    });
                }
            }
        });

        window.showAtDefaultLocation();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_inspiration_upload;
    }
}
