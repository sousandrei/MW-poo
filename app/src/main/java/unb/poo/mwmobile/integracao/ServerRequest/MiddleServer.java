package unb.poo.mwmobile.integracao.ServerRequest;


import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import unb.poo.mwmobile.config.MiddleServerConfig;

/**
 * Classe para fazer o request ao server
 * que faz o intermedio entre a aplicação e
 * o CPD.
 *
 * Created by scartezini on 27/10/2015.
 */
public class MiddleServer {

    private static final String TAG = "MiddleServer";
    private static MiddleServer instance;


    private RequestQueue mRequestQueue;
    private Context mContext;


    private MiddleServer(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * @param context
     *  </n>
     *      Contexto da aplicacao é para durar esse sington   </n>
     *      o tempo que dura a aplicacao  </n>
     * @return
     */
    public static MiddleServer getInstance(Context context){
        if(instance == null){
            instance = new MiddleServer(context.getApplicationContext());
        }
        return (instance);
    }


    public RequestQueue getRequestQueue(){
        if( mRequestQueue == null ){
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return(mRequestQueue);
    }

    public <T> void addRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }

    public void execute( final Transaction transaction, final String tag,
                         final String header, HashMap<String,String> params){

        Gson gson = new Gson();


        final CustomRequestJsonObject request = new CustomRequestJsonObject(
                Request.Method.POST,
                MiddleServerConfig.URL + header,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "String" + response);
                        transaction.doAfter(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG,"onErrorResponse(): " + volleyError);
                        transaction.doAfter(null);
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy());

        addRequestQueue(request);
    }
}