package com.cookandroid.lastproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    memoData dbHelper;
    RecyclerView recyView;
    RecyclerAdapter recyAdapter;
    Button btnAdd;
    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new memoData(MainActivity.this);
        memoList = dbHelper.selectALL();

        recyView = findViewById(R.id.recyView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyView.setLayoutManager(linearLayoutManager);

        recyAdapter = new RecyclerAdapter(memoList);
        recyView.setAdapter(recyAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemo.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String subText = data.getStringExtra("sub");
            String mainText = data.getStringExtra("main");

            Memo memo = new Memo(title, mainText, subText, 0);
            recyAdapter.addItem(memo);
            recyAdapter.notifyDataSetChanged();

            dbHelper.insertMemo(memo);
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            int seq = data.getIntExtra("seq", -1);
            String updatedTitle = data.getStringExtra("title");
            String updatedMainText = data.getStringExtra("main");
            String updatedSubText = data.getStringExtra("sub");

            if (seq != -1) {
                Memo updatedMemo = new Memo(seq, updatedTitle, updatedMainText, updatedSubText, 0);
                dbHelper.updateMemo(updatedMemo);
                memoList = dbHelper.selectALL();  // 갱신된 리스트 불러오기
                recyAdapter.updateData(memoList);
                recyAdapter.notifyDataSetChanged();
            }
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
        private List<Memo> listdata;

        public RecyclerAdapter(List<Memo> listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup v, int viewType) {
            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.memo_list, v, false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Memo memo = listdata.get(position);

            holder.title.setTag(memo.getSeq());
            holder.title.setText(memo.getTitle());
            String mainMain = memo.getMaintext();
            if (mainMain.length() > 20) {
                mainMain = mainMain.substring(0, 20) + "...";
            }
            holder.maintext.setText(mainMain);
            holder.subtext.setText(memo.getSubtext());

        }

        void addItem(Memo memo) {
            listdata.add(memo);
        }

        void removeItem(int position) {
            listdata.remove(position);
        }

        void updateData(List<Memo> newList) {
            listdata = newList;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView maintext;
            private TextView subtext;
            private ImageView img;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.item_title);
                maintext = itemView.findViewById(R.id.item_mainText);
                subtext = itemView.findViewById(R.id.item_subtext);
                img = itemView.findViewById(R.id.item_image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Memo memo = listdata.get(position);
                            Intent intent = new Intent(view.getContext(), ReadMemo.class);
                            intent.putExtra("seq", memo.getSeq());
                            intent.putExtra("title", memo.getTitle());
                            intent.putExtra("subtext", memo.getSubtext());
                            intent.putExtra("maintext", memo.getMaintext());
                            startActivityForResult(intent, 1);
                        }
                    }
                });

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        int seq = (int) title.getTag();
                        if (position != RecyclerView.NO_POSITION) {
                            new AlertDialog.Builder(view.getContext())
                                    .setMessage("해당 메모를 삭제하시겠습니까?")
                                    .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dbHelper.deleteMemo(seq);
                                            removeItem(position);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("취소", null)
                                    .show();
                        }
                    }
                });
            }
        }
    }
}
