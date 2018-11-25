package marques.ifib;

import android.net.wifi.p2p.WifiP2pManager;
import android.graphics.Bitmap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface UserService {
    //Crides a la API que retornen dades de l'usuari

    @Headers("Accept: application/json")
    @GET("jo/")
    Call<User> getUserData(
    );

    @Headers("Accept: image/jpeg")
    @GET("jo/foto.jpg")
    Call<ResponseBody> getUserFoto(
    );
}
