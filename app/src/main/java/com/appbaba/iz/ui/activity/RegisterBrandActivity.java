package com.appbaba.iz.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.appbaba.iz.ActivityRegisterBrandsBinding;
import com.appbaba.iz.AppKeyMap;
import com.appbaba.iz.R;
import com.appbaba.iz.base.BaseAty;
import com.appbaba.iz.entity.Base.BaseBean;
import com.appbaba.iz.entity.SellerListBean;
import com.appbaba.iz.eum.NetworkParams;
import com.appbaba.iz.model.BrandsModel;
import com.appbaba.iz.model.RegisterModel;
import com.appbaba.iz.tools.AppTools;
import com.baidu.mapapi.map.Text;

/**
 * Created by ruby on 2016/3/31.
 */
public class RegisterBrandActivity extends BaseAty {
    ActivityRegisterBrandsBinding binding;
    private TextView tv_top_bar_register,tv_top_bar_brand;
    private ViewSwitcher viewSwitcher;

    private RegisterModel registerModel;
    private BrandsModel brandsModel;
    private CountDownTimer countDownTimer;


    @Override
    protected void initViews() {
        binding = (ActivityRegisterBrandsBinding)viewDataBinding;
        tv_top_bar_brand = binding.tvBrandIn;
        tv_top_bar_register = binding.tvRegister;
        viewSwitcher = binding.swBg;

        registerModel = new RegisterModel();
        brandsModel = new BrandsModel();

    }

    @Override
    protected void initEvents() {
        tv_top_bar_register.setOnClickListener(this);
        tv_top_bar_brand.setOnClickListener(this);
        tv_top_bar_register.performClick();

        binding.tvRegisterBrands.setOnClickListener(this);
        binding.tvRegisterMsg.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.btnBrandsIn.setOnClickListener(this);
        binding.includeTopTitle.Back.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_register_brands;
    }

    @Override
    protected void onClick(int id, View view) {

        switch (id)
        {
            case R.id.tv_register:
                viewSwitcher.setDisplayedChild(0);
                tv_top_bar_brand.setSelected(false);
                tv_top_bar_register.setSelected(true);
                break;
            case R.id.tv_brand_in:
                viewSwitcher.setDisplayedChild(1);
                tv_top_bar_brand.setSelected(true);
                tv_top_bar_register.setSelected(false);
                break;
            case R.id.tv_register_brands:
                Intent intent = new Intent(this,TransferActivity.class);
                intent.putExtra("fragment",11);
                startActivityForResult(intent,100);
                break;
            case R.id.tv_register_msg:
                if(TextUtils.isEmpty(binding.edtRegisterPhone.getText().toString()))
                {
                    AppTools.showNormalSnackBar(binding.edtRegisterPhone,"手机不能为空");
                    return;
                }
                else
                {
                    StartCount();
                    networkModel.checkPhone(binding.edtRegisterPhone.getText().toString().trim(),NetworkParams.CHECKPHONE);
                }
                break;
            case R.id.btn_register:
                  if(CheckInput())
                  {
                      registerModel.setNetworkParams(NetworkParams.REGISTER);
                      networkModel.register(registerModel);
                  }
                break;
            case R.id.btn_brands_in:
                if(CheckInput2())
                {
                    brandsModel.setNetworkParams(NetworkParams.BRANDIN);
                    networkModel.brandsin(brandsModel);
                }
                break;
            case R.id._back:
                onBackPressed();
                break;
        }
    }

    public  boolean CheckInput()
    {
        if(binding.tvRegisterBrands.getTag()==null)
        {
            return  false;
        }
        registerModel.setSeller_id((String) binding.tvRegisterBrands.getTag());
        if(TextUtils.isEmpty(binding.edtRegisterUsername.getText().toString()))
        {
            return false;
        }
        registerModel.setNickname(binding.edtRegisterUsername.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterShop.getText().toString()))
        {
            return  false;
        }
        registerModel.setShop_name(binding.edtRegisterShop.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterAddress.getText().toString()))
        {
            return  false;
        }
        registerModel.setAddress(binding.edtRegisterAddress.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterPhone.getText().toString()))
        {
            return  false;
        }
        registerModel.setPhone(binding.edtRegisterPhone.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterCheck.getText().toString()))
        {
            return  false;
        }
        registerModel.setCode(binding.edtRegisterCheck.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterPassword.getText().toString()))
        {
            return  false;
        }
        registerModel.setPassword(binding.edtRegisterPassword.getText().toString());
        if(TextUtils.isEmpty(binding.edtRegisterRePassword.getText().toString()))
        {
            return  false;
        }
        registerModel.setRepassword(binding.edtRegisterRePassword.getText().toString());
        return  true;
    }

    public boolean CheckInput2()
    {
        if(TextUtils.isEmpty(binding.edtBrandsName.getText()))
        {
            return false;
        }
        brandsModel.setName(binding.edtBrandsName.getText().toString().trim());
        if(TextUtils.isEmpty(binding.edtBrandsContract.getText()))
        {
            return false;
        }
        brandsModel.setContract(binding.edtBrandsContract.getText().toString().trim());
        if(TextUtils.isEmpty(binding.edtBrandsMobile.getText()))
        {
            return  false;
        }
        brandsModel.setMobile(binding.edtBrandsMobile.getText().toString().trim());
        brandsModel.setAddress(binding.edtBrandsAddress.getText().toString().trim());
       return  true;
    }

    public  void  StartCount()
    {
        binding.tvRegisterMsg.setEnabled(false);
        countDownTimer = new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvRegisterMsg.setText("  (  "+millisUntilFinished/1000+"  )  ");
            }

            @Override
            public void onFinish() {
                binding.tvRegisterMsg.setEnabled(true);
                binding.tvRegisterMsg.setText(getString(R.string.register_activity_r_btn_message));
            }
        };
        countDownTimer.start();

    }


    @Override
    public void onJsonObjectResponse(Object o, NetworkParams paramsCode) {
        BaseBean bean = (BaseBean)o;
        if(paramsCode==NetworkParams.CHECKPHONE)
        {

            if(bean.getErrorcode()==0)
            {
                networkModel.sendSmsCode(binding.edtRegisterPhone.getText().toString().trim(),NetworkParams.SENDMSG);
            }
            else
            {
                countDownTimer.cancel();
                countDownTimer.onFinish();
                AppTools.showNormalSnackBar(this.getWindow().getDecorView(),bean.getMsg());
            }

        }
        if(paramsCode==NetworkParams.SENDMSG)
        {
            if(bean.getErrorcode()!=0)
            {
                countDownTimer.cancel();
                countDownTimer.onFinish();
            }
            AppTools.showNormalSnackBar(this.getWindow().getDecorView(),bean.getMsg());
        }

        if(paramsCode==NetworkParams.REGISTER)
        {
            Toast.makeText(this,bean.getMsg(),Toast.LENGTH_LONG).show();
            if(bean.getErrorcode()==0)
            {
                AppTools.putStringSharedPreferences("username",binding.edtRegisterPhone.getText().toString().trim());
                onBackPressed();
            }

        }
        if(paramsCode==NetworkParams.BRANDIN)
        {
            Toast.makeText(this,bean.getMsg(),Toast.LENGTH_LONG).show();
//            AppTools.showNormalSnackBar(this.getWindow().getDecorView(),bean.getMsg());
            if(bean.getErrorcode()==0)
            {
                binding.edtBrandsAddress.getText().clear();
                binding.edtBrandsContract.getText().clear();
                binding.edtBrandsMobile.getText().clear();
                binding.edtBrandsName.getText().clear();
                onBackPressed();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 100:
                binding.tvRegisterBrands.setText(data.getStringExtra("name"));
                binding.tvRegisterBrands.setTag(data.getStringExtra("id"));
                break;
        }

    }
}
