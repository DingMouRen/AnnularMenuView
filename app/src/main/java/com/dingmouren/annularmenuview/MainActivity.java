package com.dingmouren.annularmenuview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dingmouren.annularmenu.AnnularMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AnnularMenu menu1,menu2,menu3,menu4;
    private RecyclerView recyclerView;
    private List<Integer> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu1 = (AnnularMenu) findViewById(R.id.menu1);
        menu2 = (AnnularMenu) findViewById(R.id.menu2);
        menu3 = (AnnularMenu) findViewById(R.id.menu3);
        menu4 = (AnnularMenu) findViewById(R.id.menu4);
        menu2.setVisibility(View.INVISIBLE);
        menu3.setVisibility(View.INVISIBLE);
        menu4.setVisibility(View.INVISIBLE);
        menu1.setOnMenuItemClickListener(new AnnularMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this,position+"条目被点击",Toast.LENGTH_SHORT).show();
            }
        });
        initList();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (menu1.isOpen()){
                    menu1.toggle();
                }
                if (menu2.isOpen()){
                    menu2.toggle();
                }
                if (menu3.isOpen()){
                    menu3.toggle();
                }
                if (menu4.isOpen()){
                    menu4.toggle();
                }
            }
        });
    }

    private void initList() {
        mList.clear();
        mList.add(R.mipmap.p1);
        mList.add(R.mipmap.p2);
        mList.add(R.mipmap.p3);
        mList.add(R.mipmap.p4);
        mList.add(R.mipmap.p5);
        mList.add(R.mipmap.p1);
        mList.add(R.mipmap.p2);
        mList.add(R.mipmap.p3);
        mList.add(R.mipmap.p4);
        mList.add(R.mipmap.p5);
        mList.add(R.mipmap.p1);
        mList.add(R.mipmap.p2);
        mList.add(R.mipmap.p3);
        mList.add(R.mipmap.p4);
        mList.add(R.mipmap.p5);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(mList.get(position)).into(holder.img);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView img;
            public ViewHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }
}
