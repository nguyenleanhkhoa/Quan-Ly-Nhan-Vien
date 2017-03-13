package com.example.user.qlnhanvien;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 3/12/2017.
 */

public class NhanvienAdapter extends ArrayAdapter<Nhanvien> {
    Activity context;
    int resource;
    List<Nhanvien> objects;
    public NhanvienAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Nhanvien> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView txtma= (TextView) row.findViewById(R.id.txtma);
        TextView txtten= (TextView) row.findViewById(R.id.txttennv);
        TextView txtchucvu= (TextView) row.findViewById(R.id.txtchucvu);
        TextView txtluong= (TextView) row.findViewById(R.id.txtluong);
        Nhanvien nv=this.objects.get(position);
        txtma.setText(nv.getMa());
        txtten.setText(nv.getTen());
        txtchucvu.setText(nv.getChucvu());
        txtluong.setText(nv.getLuong());
        return row;
    }
}
