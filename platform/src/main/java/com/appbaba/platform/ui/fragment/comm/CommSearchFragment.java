package com.appbaba.platform.ui.fragment.comm;

import android.content.ClipData;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.appbaba.platform.FragmentCommSearchBinding;
import com.appbaba.platform.ItemSearchListBinding;
import com.appbaba.platform.R;
import com.appbaba.platform.adapters.CommonBinderAdapter;
import com.appbaba.platform.adapters.CommonBinderHolder;
import com.appbaba.platform.base.BaseFragment;
import com.appbaba.platform.entity.Base.BaseBean;
import com.appbaba.platform.entity.comm.BaseHotWordsBean;
import com.appbaba.platform.entity.comm.SearchListBean;
import com.appbaba.platform.eum.NetworkParams;
import com.appbaba.platform.impl.SearchCallBack;
import com.appbaba.platform.method.MethodConfig;
import com.appbaba.platform.method.SpaceItemDecoration;
import com.appbaba.platform.widget.TagGroup;
import com.google.gson.JsonArray;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/6/14.
 */
public class CommSearchFragment extends BaseFragment {

    private FragmentCommSearchBinding binding;
    private List<SearchListBean> list;
    private List<TagGroup.TagView> styleList,spaceList;
    private TagGroup.TagView sort;
    private String word="";
    public SearchCallBack callBack;

    private CommonBinderAdapter<SearchListBean> adapter;

    @Override
    protected void InitView() {
        binding = (FragmentCommSearchBinding)viewDataBinding;
        binding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycle.addItemDecoration(new SpaceItemDecoration(10));
    }

    @Override
    protected void InitData() {

        styleList = new ArrayList<>();
        spaceList = new ArrayList<>();

        list = new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            SearchListBean searchListBean = new SearchListBean();
            switch (i)
            {
                case 0:
                    searchListBean.setName("排序");
                    break;
                case 1:
                    searchListBean.setName("风格");
                    break;
                case 2:
                    searchListBean.setName("空间");
                    break;
            }
            searchListBean.setItems(new ArrayList<SearchListBean.Item>());
            list.add(searchListBean);
        }
        adapter = new CommonBinderAdapter<SearchListBean>(getContext(),R.layout.item_search_list_view,list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int position, SearchListBean searchListBean) {
                final ItemSearchListBinding listBinding = (ItemSearchListBinding)viewDataBinding;
                listBinding.tvItemName.setText(searchListBean.getName());
                 final List<SearchListBean.Item> items =  searchListBean.getItems();
                final List<String> titles = new ArrayList<>();
                for(int i=0;i<items.size();i++)
                {
                    titles.add(items.get(i).getTitle());
                }
                listBinding.tagGroup.setTags(titles);
                listBinding.tagGroup.setTag(R.string.tag_value,position);
                listBinding.tvItemAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isvisible = listBinding.tagGroup.getVisibility() == View.GONE ? true : false;
                        if(isvisible)
                        {
                            listBinding.tvItemAll.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_more_arrow_down,0);
                        }
                        else
                        {
                            listBinding.tvItemAll.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_more_arrow_up,0);
                        }
                        listBinding.tagGroup.setVisibility(isvisible ? View.VISIBLE : View.GONE);
                    }
                });
                listBinding.tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {
                        int index = (int)listBinding.tagGroup.getTag(R.string.tag_value);
                        int where = listBinding.tagGroup.tagNames.indexOf(tag);
                        switch (index)
                        {
                            case 0:
                                if(sort!=null)
                                {
                                    sort.setChecked(false);
                                }
                                sort = listBinding.tagGroup.getTagAt(where);
                                break;
                            case 1:
                                if(styleList.contains(listBinding.tagGroup.getTagAt(where)))
                                {
                                    styleList.remove(listBinding.tagGroup.getTagAt(index));
                                }
                                else
                                {
                                   styleList.add(listBinding.tagGroup.getTagAt(where));
                                }
                                break;
                            case 2:
                                if(spaceList.contains(listBinding.tagGroup.getTagAt(where)))
                                {
                                    spaceList.remove(listBinding.tagGroup.getTagAt(where));
                                }
                                else
                                {
                                    spaceList.add(listBinding.tagGroup.getTagAt(where));
                                }
                                break;
                        }
                    }
                });
            }
        };
        binding.recycle.setAdapter(adapter);
        if(MethodConfig.hotWordsBean==null)
        {
            networkModel.BaseHotWords(NetworkParams.CUPCAKE);
        }
        else
        {
            BaseHotWordsBean baseHotWordsBean = MethodConfig.hotWordsBean;
            binding.tagGroup.setTagsAll(baseHotWordsBean.getKeywords_list());
            list.get(0).getItems().addAll(baseHotWordsBean.getSort_list());
            list.get(1).getItems().addAll(baseHotWordsBean.getStyle_list());
            list.get(2).getItems().addAll(baseHotWordsBean.getSpace_list());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void InitEvent() {
          binding.tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
              @Override
              public void onTagClick(String tag) {
                  word = tag;
                    GoToSearch();
              }
          });

    }

    @Override
    protected void InitListening() {
        binding.tvReset.setOnClickListener(this);
        binding.tvSure.setOnClickListener(this);
        binding.getRoot().setOnClickListener(this);
    }

    @Override
    protected void OnClick(int id, View view) {
        switch (id)
        {
            case R.id.tv_reset:
                styleList.clear();
                spaceList.clear();
                binding.edtSearch.getText().clear();
                sort=null;
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_sure:
                word = binding.edtSearch.getText().toString();
                GoToSearch();
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    public void onJsonObjectSuccess(BaseBean baseBean, NetworkParams paramsCode) {
        if(baseBean.getErrorcode()==0)
        {
            if(paramsCode==NetworkParams.CUPCAKE)
            {
                BaseHotWordsBean baseHotWordsBean = (BaseHotWordsBean)baseBean;
                MethodConfig.hotWordsBean = baseHotWordsBean;
               // List<SearchListBean.Item> items = new ArrayList<>();
//                for(int i =0;i<baseHotWordsBean.getKeywords_list().size();i++)
//                {
//                    SearchListBean.Item item = new SearchListBean.Item();
//                    item.setId("");
//                    item.setTitle(baseHotWordsBean.getKeywords_list().get(i));
//                    items.add(item);
//                }
                binding.tagGroup.setTagsAll(baseHotWordsBean.getKeywords_list());
                list.get(0).getItems().addAll(baseHotWordsBean.getSort_list());
                list.get(1).getItems().addAll(baseHotWordsBean.getStyle_list());
                list.get(2).getItems().addAll(baseHotWordsBean.getSpace_list());
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void GoToSearch()
    {
        List<String> list1 = new ArrayList<>();
        for(int i=0;i<styleList.size();i++)
        {
            int index  = (int)styleList.get(i).getTag(R.string.tag_value);
            list1.add(MethodConfig.hotWordsBean.getStyle_list().get(index).getId());
        }
       List<String> list2 = new ArrayList<>();
        for(int i=0;i<spaceList.size();i++)
        {
            int index  = (int)spaceList.get(i).getTag(R.string.tag_value);
            list2.add(MethodConfig.hotWordsBean.getSpace_list().get(index).getId());
        }

        String strSort="";
        if(sort!=null) {
            int index = (int) sort.getTag(R.string.tag_value);

          strSort  = MethodConfig.hotWordsBean.getSort_list().get(index).getName();
        }

       if(callBack!=null)
       {
           callBack.Update(word,strSort,list1,list2);
           binding.tvReset.performClick();
       }

    }
}
