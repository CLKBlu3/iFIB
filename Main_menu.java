package marques.ifib;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TokensClass responseToken;
    private boolean loggedIn;
    SharedPreferences prefs;
    String accessToken;
    UserService uS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        prefs = this.getSharedPreferences("marques.ifib", Context.MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
           this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();


        //Canviar els valors de l'usuari pels adequats
        getUserData();
        getUserFoto();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_horari) {

        } else if (id == R.id.nav_assig) {

        } else if (id == R.id.nav_news) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_log_out) { //LOG OUT
            //revokeToken();
            resetSharedPrefs();
            goToLogActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getUserFoto(){
        accessToken = prefs.getString("accessToken", null);
        final ImageView profileImg = (ImageView) findViewById(R.id.profileImg);

        uS = ServiceGenerator.createService(UserService.class,OAuthParams.clientID,OAuthParams.clientSecret,accessToken,this);
        Call<ResponseBody> imgResponse = uS.getUserFoto();
        imgResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap photoBm = BitmapFactory.decodeStream(response.body().byteStream());
                    profileImg.setImageBitmap(photoBm);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error(-1);
            }
        });
    }

    private void getUserData(){
        accessToken = prefs.getString("accessToken", null);

        //PARAMETRES QUE PODEN CANVIAR PEL LOGIN
        final TextView name = (TextView) findViewById(R.id.nomicognoms);
        final TextView email = (TextView) findViewById(R.id.email);
        //final ImageView profileImg = (ImageView) findViewById(R.id.profileImg);

        uS = ServiceGenerator.createService(UserService.class,OAuthParams.clientID,OAuthParams.clientSecret,accessToken,this);
        Call<User> userResponse = uS.getUserData();
        userResponse.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                     String nameStr = response.body().getNom();
                     nameStr += " " + response.body().getCognoms();
                     String emailStr = response.body().getEmail();
                     name.setText(nameStr);
                     email.setText(emailStr);
                }
                else{
                    if(response.code() == 401) refreshToken();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error(-1);
            }

        });
    }

    private void refreshToken(){
        RacoService refreshService = ServiceGenerator.createService(RacoService.class);
        String refreshToken = prefs.getString("refreshToken", null);
        Call<TokensClass> refreshCall = refreshService.getRefreshToken("refresh_token", refreshToken, OAuthParams.clientID, OAuthParams.clientSecret);
        refreshCall.enqueue(new Callback<TokensClass>() {
            @Override
            public void onResponse(Call<TokensClass> call, Response<TokensClass> response) {
                if(response.isSuccessful()){
                    TokensClass token = response.body();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("accessToken", token.getAccessToken());
                    editor.putString("refreshToken", token.getRefreshToken());
                    editor.apply();
                    getUserData();
                }
                else error(response.code());
            }

            @Override
            public void onFailure(Call<TokensClass> call, Throwable t) {
                error(-1);
            }
        });
    }

    private void resetSharedPrefs(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", null);
        editor.putString("refreshToken", null);
        editor.putBoolean("loggedIn", false);
        editor.apply();
    }

    //NO FUNCIONA XD CAGUNDEU
    private void revokeToken(){
        RacoService racoService = ServiceGenerator.createService(RacoService.class);
        String token, clientId;
        token = prefs.getString("accessToken", null);
        clientId = OAuthParams.clientID;
        Call<TokensClass> revokeAccessToken;
        if(token != null && clientId != null){
            revokeAccessToken = racoService.revokeToken(token, clientId);
            revokeAccessToken.enqueue(new Callback<TokensClass>() {
                @Override
                public void onResponse(Call<TokensClass> call, Response<TokensClass> response) {
                    if(!response.isSuccessful()){
                        error(response.code());
                    }
                }

                @Override
                public void onFailure(Call<TokensClass> call, Throwable t) {

                }
            });
        }

    }

    private void goToLogActivity(){
        Intent intent = new Intent(Main_menu.this, logActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void error(Integer code){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Error: " + code).setTitle(code);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetSharedPrefs();
                goToLogActivity();
            }
        });
    }
}
