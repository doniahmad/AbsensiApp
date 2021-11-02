package com.example.absensiapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.security.auth.callback.Callback;

import io.realm.Realm;
import io.realm.RealmResults;

public class SiswaAdapter extends RecyclerView.Adapter<SiswaAdapter.MyViewHolder> {

    private List<SiswaModel> siswaModel;
    Context context;
    View view;
    Integer id;
    SiswaModel model;
    RealmHelper realmHelper;

    public SiswaAdapter(Context context, List<SiswaModel> siswaModel, RealmHelper realmHelper){
        this.context = context;
        this.siswaModel = siswaModel;
        this.realmHelper = realmHelper;
    }

    @NonNull
    @Override
    public SiswaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SiswaAdapter.MyViewHolder holder, int position) {
        model = siswaModel.get(position);
        holder.nama.setText(model.getNama());
        holder.absen.setText(String.valueOf(model.getAbsen()));
        holder.kelas.setText(model.getKelas());
    }

    @Override
    public int getItemCount() {
        return siswaModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView nama, absen, kelas;

        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            view = itemView;
            nama = itemView.findViewById(R.id.nama_rv);
            absen = itemView.findViewById(R.id.absen_rv);
            kelas = itemView.findViewById(R.id.kelas_rv);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1, 1, "Delete");
            id = siswaModel.get(getAdapterPosition()).getId();
            Delete.setOnMenuItemClickListener(onClickmenu);
        }

    }
    private final MenuItem.OnMenuItemClickListener onClickmenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Delete data");
                    builder.setMessage("Are you sure ?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            realmHelper.delete(id);
                            notifyItemRemoved(id);
                            notifyItemRangeChanged(id, siswaModel.size());
                            notifyDataSetChanged();
                            Toast.makeText(context,"Posisi"+id,Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
            }
            return true;
        }
    };
}
