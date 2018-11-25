package marques.ifib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class logActivity extends AppCompatActivity {
    private TokensClass responseToken;
    private boolean loggedIn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        prefs = this.getSharedPreferences("marques.ifib", Context.MODE_PRIVATE);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //REQUIRED INTENT TO GET THE AUTHORIZATION CODE
                Intent intent =  new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(ServiceGenerator.API_BASE_URL + "o/authorize/" + "?client_id=" + OAuthParams.clientID + "&redirect_uri=" + OAuthParams.redirectUri + "&response_type=" + OAuthParams.responseType + "&state=" + OAuthParams.getRandomString())
                );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Uri uri = getIntent().getData();
        //Check the URI we received corresponds to our callback
        if(uri != null && uri.toString().startsWith(OAuthParams.redirectUri)){
            Log.d("uri: ", "URI: " + uri.toString());
            //Correct callback --> proceed to catch the auth code.
            String code = uri.getQueryParameter("code");
            String state = uri.getQueryParameter("state");
            //Log.d("the two states", OAuthParams.state + " " + state + " the code is: " + code);
            if(code != null && state != null && OAuthParams.state.equals(state)){
                RacoService rs = ServiceGenerator.createService(RacoService.class);
                //Fem la crida del RacoService per obtenir el ACCESS TOKEN
                Call<TokensClass> cAS = rs.getAccesToken("authorization_code", code, OAuthParams.redirectUri, OAuthParams.clientID, OAuthParams.clientSecret);
                Log.d("Acces Token", "rebut el token: " + cAS);
                cAS.enqueue(new Callback<TokensClass>() {
                    @Override
                    public void onResponse(Call<TokensClass> call, Response<TokensClass> response){
                        Log.d("response", "response is " + response);
                        if(response.isSuccessful()){
                            Log.d("response", "Succesful");
                            responseToken = response.body();
                            loggedIn = true;
                            saveTokens();
                            goToMain();
                        }
                        Log.d("response", "UNSuccesful");
                    }

                    @Override
                    public void onFailure(Call<TokensClass> call, Throwable t) {
                        Log.d("response", "Failure");
                    }
                });
            }
        }
    }

    private void saveTokens(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", this.responseToken.getAccessToken());
        editor.putString("refresh_token", this.responseToken.getRefreshToken());
        editor.putBoolean("loggedIn", this.loggedIn);
        editor.apply();
    }
    private void goToMain(){
        Intent intent = new Intent(logActivity.this, Main_menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
