package com.zxtc.collectiontools.ui.function.upload;


import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.zxtc.collectiontools.utils.Convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 *  基础包装与实际数据 未分离的 JsonCallBack
 *  例子：
 *      public class LzyResponse<T> {
 *          public int code;
 *          public String msg;
 *          public List<Data> data;
 *
 *          public class Data{
 *              public long id;
 *              public String name;
 *              public int age;
 *          }
 *       }
 *
 */

public abstract class JsonLinkCallback<T> extends AbsCallback<T>{

    @Override
    public T convertSuccess(Response response) throws Exception {
        //以下代码是通过泛型解析实际参数,泛型必须传

        //com.lzy.demo.callback.DialogCallback<com.lzy.demo.model.Login> 得到类的泛型，包括了泛型参数
        Type genType = getClass().getGenericSuperclass();
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.Login
        Type type = params[0];

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        //有数据类型，表示有data
        T data = Convert.fromJson(jsonReader, type);
        response.close();
        return data;
    }



//    private IJson myJson;

//    public void setMyClickListenr(IJson<T> myJson) {
//        this.myJson = myJson;
//    }
//    public interface IJson<T> {
//        void Success(Response response,T t);
//        void Error(Call call, Response response, Exception e);
//    }
}
