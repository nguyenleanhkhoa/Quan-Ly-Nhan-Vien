package com.example.user.qlnhanvien;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listnhanvien;
    ArrayList<Nhanvien>dsNhanvien;
    NhanvienAdapter adapterNV;
    final String DATABASE_NAME="NhanVien.sqlite";
    SQLiteDatabase database;

    EditText edtma,edtten,edtcv,edtsal,edtmaup,edttenup,edtchucvuup,edtluongup;
    Button btnadd,btncancel,btnsave,btncancelup;
    String vitrima="";
    String vitriten="";
    String vitrichucvu="";
    String vitriluong="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        ShowDatabase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shownDialogAdd();
        ClickOnList();
        registerForContextMenu(listnhanvien);
        LongCLick();
    }
    public void ClickOnList() {
        listnhanvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Nhanvien item=dsNhanvien.get(position);
                AlertDialog.Builder sh=new AlertDialog.Builder(MainActivity.this);
                sh.setTitle("Informaition!");
                sh.setMessage("Mã: "+item.getMa()+"\nTên: "+item.getTen()+"\nChức vụ: "+item.getChucvu()+"\nLuong: "+item.getLuong());
                sh.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                sh.create().show();
            }
        });
    }
    private void LongCLick() {
        listnhanvien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Nhanvien nvf=dsNhanvien.get(position);
                vitrima=nvf.getMa();
                vitriten=nvf.getTen();
                vitrichucvu=nvf.getChucvu();
                vitriluong=nvf.getLuong();
                return false;
            }
        });
    }
    private void shownDialogAdd() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dia=new Dialog(MainActivity.this);
                dia.setContentView(R.layout.activity_add);
                dia.setTitle("Add new memeber");
                dia.show();
                edtma= (EditText) dia.findViewById(R.id.edtid);
                edtten= (EditText) dia.findViewById(R.id.edtname);
                edtcv= (EditText) dia.findViewById(R.id.edtchucvu);
                edtsal= (EditText) dia.findViewById(R.id.edtluong);
                btnadd= (Button) dia.findViewById(R.id.btnadd);
                btncancel= (Button) dia.findViewById(R.id.btncancel);
                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database=Database.initDatabase(MainActivity.this,DATABASE_NAME);
                        Cursor cursor=database.rawQuery("Select * from Nhanvien",null);
                        String addma=edtma.getText().toString();
                        String addten=edtten.getText().toString();
                        String addchucvu=edtcv.getText().toString();
                        String addluong=edtsal.getText().toString();

                        while(cursor.moveToNext()){
                            if(addma.equalsIgnoreCase(cursor.getString(0))){
                                AlertDialog.Builder thongbao=new AlertDialog.Builder(MainActivity.this);
                                thongbao.setTitle("Alert!");
                                thongbao.setMessage("Mã đã có không thể thêm!");
                                thongbao.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                thongbao.create().show();
                            }else  if(addma.equalsIgnoreCase("") ||addten.equalsIgnoreCase("")||addchucvu.equalsIgnoreCase("")||addluong.equalsIgnoreCase("")){
                                Toast.makeText(MainActivity.this, "Chưa nhập xong !!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                ContentValues value= new ContentValues();
                                value.put("MA",addma);
                                value.put("TEN",addten);
                                value.put("CHUCVU",addchucvu);
                                value.put("LUONG",addluong);
                                database=Database.initDatabase(MainActivity.this,DATABASE_NAME);
                                database.insert("Nhanvien",null,value);
                                dia.dismiss();
                                ShowDatabase();
                            }
                        }

                    }
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dia.dismiss();
                    }
                });
            }
        });
    }
    public void ShowDatabase() {
        database=Database.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("Select * from Nhanvien",null);
        adapterNV.clear();
        while (cursor.moveToNext()){
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String chucvu=cursor.getString(2);
            String luong=cursor.getString(3);
            Nhanvien nv=new Nhanvien();
            nv.setMa(ma);
            nv.setTen(ten);
            nv.setChucvu(chucvu);
            nv.setLuong(luong);
            adapterNV.add(nv);
        }
        adapterNV.notifyDataSetChanged();
    }

    private void addControl() {
        listnhanvien= (ListView) findViewById(R.id.listNV);
        dsNhanvien=new ArrayList<>();
        adapterNV=new NhanvienAdapter(MainActivity.this,R.layout.item,dsNhanvien);
        listnhanvien.setAdapter(adapterNV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_item,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final String maid=vitrima;
        database=Database.initDatabase(MainActivity.this,DATABASE_NAME);
        if(item.getItemId()==R.id.btndelete){
            final AlertDialog.Builder del=new AlertDialog.Builder(MainActivity.this);
            del.setMessage("bạn có chắc muốn xóa nhân viên này !");
            del.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    database.delete("Nhanvien","ma=?",new String[]{maid});
                    ShowDatabase();
                    Toast.makeText(MainActivity.this, "Đã xóa nhân viên có mã là "+maid, Toast.LENGTH_SHORT).show();
                }
            });
            del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                }
            });
            del.create().show();

        }else if(item.getItemId()==R.id.btnEdit){

            final Dialog dialog=new Dialog(MainActivity.this);
            dialog.setTitle("Update Nhân viên");
            dialog.setContentView(R.layout.activity_update);
            edtmaup= (EditText) dialog.findViewById(R.id.edtidupdate);
            edttenup= (EditText) dialog.findViewById(R.id.edtnameupdate);
            edtchucvuup= (EditText) dialog.findViewById(R.id.edtchucvuupdate);
            edtluongup= (EditText) dialog.findViewById(R.id.edtluongupdate);
            btnsave= (Button) dialog.findViewById(R.id.btnsave);
            btncancelup= (Button) dialog.findViewById(R.id.btncancelup);
            dialog.show();

            final String updma=vitrima;
            String updten=vitriten;
            String updchucvu=vitrichucvu;
            String updluong=vitriluong;
            edtmaup.setText(updma);
            edttenup.setText(updten);
            edtchucvuup.setText(updchucvu);
            edtluongup.setText(updluong);
            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String updama=edtmaup.getText().toString();
                    String updaTen=edttenup.getText().toString();
                    String updaChucvu=edtchucvuup.getText().toString();
                    String updaLuong=edtluongup.getText().toString();
                    ContentValues values1=new ContentValues();
                        values1.put("MA",updama);
                        values1.put("TEN",updaTen);
                        values1.put("CHUCVU",updaChucvu);
                        values1.put("LUONG",updaLuong);
                        database.update("Nhanvien",values1,"ma=?",new String[]{updma});
                        ShowDatabase();
                    dialog.dismiss();
                }
            });
            btncancelup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return super.onContextItemSelected(item);
    }
}
