package marques.ifib;

import android.media.session.MediaSession;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RacoService {
    //API TOKENS USAGE
    //USE CALL<> for ASYNC responses --> prevents UI from freezing6

    @POST("o/token")
    @FormUrlEncoded
    Call<TokensClass> getAccesToken(
        @Field("grant_type") String grantType,
        @Field("redirect_uri") String redirectUri,
        @Field("code") String code,
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret
    );

    @POST("o/token")
    @FormUrlEncoded
    Call<TokensClass> getRefreshToken(
            @Field("grant_type") String grantType,
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @POST("o/revoke_token")
    @FormUrlEncoded
    Call<TokensClass> revokeToken(
            @Field("token") String token,
            @Field("client_id") String clientId
    );
}
