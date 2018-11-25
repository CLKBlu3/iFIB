package marques.ifib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokensClass {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    @SerializedName("scope")
    @Expose
    private String scope;

    //GETTERS
    public String getAccessToken(){
        return this.accessToken;
    }

    public String getTokenType(){
        return this.tokenType;
    }

    public Integer getExpiresIn(){
        return this.expiresIn;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }

    public String getScope(){
        return this.scope;
    }

    //SETTERS
    public void setAccessToken(String aToken){
        this.accessToken = aToken;
    }

    public void setTokenType(String type){
        this.tokenType = type;
    }

    public void setExpiresIn(Integer expTime){
        this.expiresIn = expTime;
    }

    public void setRefreshToken(String rToken){
        this.refreshToken = rToken;
    }

    public void setScope(String scope){
        this.scope = scope;
    }
}
