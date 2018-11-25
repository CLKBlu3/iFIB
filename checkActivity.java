package marques.ifib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class checkActivity extends AppCompatActivity {
    // TEMPORAL ACTIVITY --> CHECKS IF USER IS ALREADY LOGGED IN.
    // IF LOGGED IN --> JUMPS DIRECT TO MAIN MENU
    // OTHERWISE --> JUMPS TO LOG ACTIVITY, AND LET'S HIM LOG IN
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        prefs = this.getSharedPreferences("marques.ifib", Context.MODE_PRIVATE);
        Intent intent = nextActivity();
        changeActivity(intent);
    }

    private Intent nextActivity(){
        if(prefs.getBoolean("loggedIn", true)) return new Intent(checkActivity.this, logActivity.class);
        return new Intent(checkActivity.this, Main_menu.class);
    }

    private void changeActivity(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
