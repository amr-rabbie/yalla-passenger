package com.asi.yalla_passanger_eg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class ChangePassword extends AppCompatActivity
{


    EditText oldPass,newPass;
    FancyButton change;
    LinearLayout linearChangePasss;
    RotateLoading rotateloading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ini();
    }

    private void ini() {
        oldPass=  findViewById(R.id.et_old_password);
        newPass=  findViewById(R.id.et_password);
        change=  findViewById(R.id.btn_change_pass);
        rotateloading=  findViewById(R.id.rotateloading);
        rotateloading.setVisibility(View.GONE);
        linearChangePasss=  findViewById(R.id.linearChangePasss);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changePassRequest();
            }
        });
    }


    private void changePassRequest() {

        linearChangePasss.setVisibility(View.GONE);
        rotateloading.setVisibility(View.VISIBLE);
        rotateloading.start();
        final String TAG="ASI";
        Map<String, String> postParam= new HashMap<String, String>();
       //postParam.put("UserId",Constants.getUserId(ChangePassword.this));
        postParam.put("OldPassword",Constants.convertPassMd5(oldPass.getText().toString().trim()));
        postParam.put("NewPassword",Constants.convertPassMd5(newPass.getText().toString()));
        postParam.put("OrginalNewPassword",newPass.getText().toString());
        postParam.put("UserId",new SQLiteHandler(ChangePassword.this).getUserDetails().get("uid"));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL+"PassengerChangePassword", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        rotateloading.setVisibility(View.GONE);
                        rotateloading.stop();
                        Log.d(TAG, response.toString());
                        try {

                            String Flag=response.getString("Flag");
                            if (Flag.equals(Constants.PASSWORD_CHANGED))
                            {
                                new SweetAlertDialog(ChangePassword.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.passchange))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                                        {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog)
                                            {
                                                startActivity(new Intent(ChangePassword.this,MainActivity.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }else if (Flag.equals(Constants.OLD_PASS_INCORRECT))
                            {
                                new SweetAlertDialog(ChangePassword.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.oldpassnotcorrect))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                                linearChangePasss.setVisibility(View.VISIBLE);
                            }else if(Flag.equals(Constants.INVALID_REQUEST))
                            {
                                Toast.makeText(ChangePassword.this,getResources().getString(R.string.thereisanerror),Toast.LENGTH_LONG).show();
                                linearChangePasss.setVisibility(View.VISIBLE);
                            }else if(Flag.equals(Constants.INVALID_USER))
                            {
                                Toast.makeText(ChangePassword.this,getResources().getString(R.string.thereisanerror),Toast.LENGTH_LONG).show();
                                linearChangePasss.setVisibility(View.VISIBLE);
                            }
                            Log.e("FLAG-->",Flag);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                rotateloading.setVisibility(View.GONE);
                rotateloading.stop();
                // hideProgressDialog();
            }
        }) {



            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders()
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,"TAG");
    }
}
