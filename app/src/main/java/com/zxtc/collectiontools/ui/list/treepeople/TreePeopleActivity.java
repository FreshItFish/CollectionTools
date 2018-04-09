package com.zxtc.collectiontools.ui.list.treepeople;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.zxtc.collectiontools.R;
import com.zxtc.collectiontools.base.BaseActivity;
import com.zxtc.collectiontools.base.TreePeopleEvent;
import com.zxtc.collectiontools.utils.ConstantUtils;
import com.zxtc.collectiontools.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：KY
 * 创建时间：2018/4/2 09:58
 * 描述: 人员组织树
 */

public class TreePeopleActivity extends BaseActivity {

    private ListView lv;
    private List<Node> mDatas = new ArrayList<>();

    private static final String ALL = "all";
    private static final String STUDENT = "student";
    private static final String PARENT = "parent";
    private static final String TEACHER = "teacher";
    private Context context;
    private SimpleTreeAdapter mAdapter;
    private TextView back,title,sure;

    private String flag;

    @Override
    public int bindLayout() {
        return R.layout.activity_selectreceiver;
    }

    @Override
    public void initView(View mContextView) {
        lv = (ListView) findViewById(R.id.mlistView);
        back = (TextView) findViewById(R.id.base_toolbar_sidemenu);
        title = (TextView) findViewById(R.id.base_toolbar_title);
        sure = (TextView) findViewById(R.id.base_toolbar_subtitle);
    }

    @Override
    public void doBusiness(Context mContext) {
        context = mContext;
        initUserTypes();
        initNetData();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        //取消请求
//        try {
//            executor.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //关闭加载中进度条
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
    }

    private void initUserTypes() {
        Intent intent = getIntent();
        //flag用来标记是谁启动了它，如果flag不为空，Event将flag返回，作为区别的标识
        flag = intent.getStringExtra("flag");
        String type = intent.getStringExtra("type");
        //自己的逻辑。根据传的 type 请求不同的数据 (根据权限区分显示数据)

    }

    private void initNetData() {
        initTitle();

        /**
         * 编写自己的网络请求
         */
        setListData(null);
//        OkGo.get(Urls.TreePeople).execute(new StringDialogCallback(this) {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                try {
//                    ResponseResult<List<TreePeopleEntity>> result = Convert.fromJson(s,
//                            Type.type(ResponseResult.class, Type.type(List.class,TreePeopleEntity.class)));
//                    if (result != null && result.data != null ) {
//                        List<TreePeopleEntity> treePeople = result.data;
//                        setListData(treePeople);
//                    }
//                }catch (Exception e){
//                    showToast("数据操作失败");
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//                showToast("请求失败！");
//                e.printStackTrace();
//            }
//        });



//        //网络请求的服务类，用的 retrofit 框架
//        service = ApiManager.getReceiverSelectService();
//        //RxJava 封装的工具类
//        executor = new CloseableRxServiceExecutor();
//        //当前用户的token
//        token = StringUtils.getToken(this);
//
//        //加载中进度条
//        progressDialog = CustomProgressDialog.create(this, CommonUtils.getResString(R.string.load_data));
//        progressDialog.show();
//
//        //Retrofit + RxJava 网络请求
//        Single<DataModel> single = service.getAppUser(token, userTypes);
//        executor.execute(single, new Action1<DataModel>() {
//            @Override
//            public void call(DataModel dataModel) {
//                setListData(dataModel);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                progressDialog.dismiss();
//                ToastUtils.showToast(CommonUtils.getResString(R.string.request_data_error));
//            }
//        });
    }

    /**
     * 解析服务器返回的数据,让服务器把所有的人都包装到json里返回了
     *
     * 注：
     *    这一步要根据自己的服务器返回数据体将拿到的数据拆分开
     * @param treePeople 数据list
     */
    private void setListData(List<TreePeopleEntity> treePeople) {
//        for (TreePeopleEntity entity : treePeople) {
//            if (null == entity.getPid() ||"".equals(entity.getPid())){
//                //一级部门
//                mDatas.add(new Node(entity.getId(), ALL, entity.getName()));
//            } else {
//                String id = entity.getId(); //当前数据id
//                String pid = entity.getPid(); //当前数据父节点id
//                for (int i = 0; i < treePeople.size(); i++) {
//                    if (pid.equals(treePeople.get(i).getId())) {//遍历查询父节点
//                        mDatas.add(new Node(id, treePeople.get(i).getId(), entity.getName(), 0, entity.getIsPeople()));
//                    }
//                }
//            }
//        }


        //===================================================================

        //创建第二级菜单
        mDatas.add(new Node(STUDENT, ALL, "AA集团",1,0));
        mDatas.add(new Node(PARENT, ALL, "XX集团",0,0));
        mDatas.add(new Node(TEACHER, ALL, "SS集团",0,0));

        mDatas.add(new Node("222", STUDENT, "ee公司",0,0));
        mDatas.add(new Node("2221", "222", "A部门",2,0));
        mDatas.add(new Node("2222", "222", "B部门",2,0));
        mDatas.add(new Node("22211", "2221", "黄佳",0,1));
        mDatas.add(new Node("22212", "2221", "陈才",0,1));
        mDatas.add(new Node("22221", "2222", "李珑",0,1));
        mDatas.add(new Node("22222", "2222", "谢家发",0,1));


        //创建第一级菜单
        mDatas.add(new Node(ALL, "-1", "全部人员"));

        //给 ListView 设置 Adapter
        mAdapter = new SimpleTreeAdapter(lv, context, mDatas, 1, R.mipmap.ic_arrows_down, R.mipmap.ic_arrows_right);
        lv.setAdapter(mAdapter);
    }

    private void initTitle() {
        title.setText("请选择人员");
        sure.setText("确定");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击确定按钮
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter == null) {
                    return;
                }

                List<Node> allNodes = mAdapter.getAllNodes();
                List<String> selectNodesId = new ArrayList<String>();
                List<String> selectNodesName = new ArrayList<String>();
                for (Node node : allNodes) {
                    if (node.isChecked() && (node.getIsPeople() == 1)) {
                        selectNodesId.add(node.getId().toString());
                        selectNodesName.add(node.getName().toString());
                    }
                }

                //去重操作
                List list = ConstantUtils.deleteRepeat(selectNodesId);    //id
//                List listName = MyUtils.deleteRepeat(selectNodesName);  //name
                List listName = selectNodesName;  //name
                //格式化
                //id
                String ids = list.toString();
                String substring = ids.substring(1, ids.length() - 1);
                String result = substring.replace(", ", ",");

                //name
                String names = listName.toString();
                String nameStr = names.substring(1, names.length() - 1);
                String resultName = nameStr.replace(", ", ",");



                //用EventBus发送选中的ids 和 一共选了多少人
                if (flag == null) {
                    ToastUtils.showShort("flag为空！"+resultName);
                    EventBus.getDefault().post(new TreePeopleEvent(result,resultName,list.size()));
                } else {
                    ToastUtils.showShort("您选择了 "+resultName);
                    //flag不为空，返回
                    EventBus.getDefault().post(new TreePeopleEvent(flag,result,resultName,list.size()));
                }

//                finish();
            }
        });
    }

}
