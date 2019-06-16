package com.example.campus;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campus.bean.User;
import com.example.campus.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CjQueryActivity extends AppCompatActivity {
    private TextView tv_welcome;
    private ListView listView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cjquery_activity);

        tv_welcome = findViewById(R.id.welcome_who);
        listView = findViewById(R.id.cjquery_listview);
        context = this;

        query();
    }

    public void query(){
        Constant.user = User.getCurrentUser(User.class);//取用缓存的数据
        if(Constant.user == null){
            Toast.makeText(CjQueryActivity.this, "未登录，请先登录后再进行查询！", Toast.LENGTH_SHORT).show();
        }else{
            tv_welcome.setText(Constant.user.getStudentname()+"的成绩单");
            BmobQuery<com.example.campus.Grade> bmobQuery = new BmobQuery<>();
            bmobQuery.findObjects(new FindListener<com.example.campus.Grade>() {
                @Override
                public void done(List<com.example.campus.Grade> list, BmobException e) {
                    if (e == null) {
                        if(list.isEmpty()){
                            Toast.makeText(CjQueryActivity.this, "未查询到数据！", Toast.LENGTH_SHORT).show();
                        }else {
                            List<com.example.campus.Grade> grades = new ArrayList<>();
                            for(com.example.campus.Grade g: list){
                                String str = g.getStudentid().getObjectId().replaceAll(" ", "");
                                if(str.equals(Constant.user.getObjectId())){
                                    grades.add(g);
                                }
                            }
                            listView.setAdapter(new MyAdapter(context, grades));
                        }
                    } else {
                        Toast.makeText(CjQueryActivity.this, String.valueOf(e.getErrorCode()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<com.example.campus.Grade> list;


        public MyAdapter(Context context , List<com.example.campus.Grade> list){

            this.mInflater = LayoutInflater.from(context);
            this.list = list;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = mInflater.inflate(R.layout.cjquery_listview_item, null);
            TextView tv1= convertView.findViewById(R.id.listview_item_label);
            TextView tv2= convertView.findViewById(R.id.listview_item_info);
            TextView tv3= convertView.findViewById(R.id.listview_item_credit);

            tv1.setText(list.get(position).getClassname());
            tv2.setText(list.get(position).getCj());
            tv3.setText(list.get(position).getCredit());
            return convertView;
        }

    }
}
