package marques.ifib;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthenticationInterceptor implements Interceptor {
    /**
     * We're using the Interceptor to set the Authorization field
     * within the HTTP request header. This field consists of two parts: first, the token type which
     * is Bearer for OAuth requests and second, the access token.
     */

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer "+authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}