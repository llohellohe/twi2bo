package yangqi.oauth1;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by yangqi on 1/11/15.
 */
public class OAuthRequest {
    private Map<String, String> parameters;
    private String              httpMethod;
    private String              httpUrl;


    public OAuthRequest(String httpUrl, String httpMethod) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getSignatureBaseString(){
        StringBuffer signature = new StringBuffer();
        signature.append(httpMethod.toUpperCase()).append("&");
        signature.append(OAuthUtil.percentString(httpUrl)).append("&");
        signature.append(OAuthUtil.percentString(getSignableParameters()));
        return signature.toString();
    }

    private String getSignableParameters(){
        if(parameters==null||parameters.size()==0){
           return "";
        }
        Map<String,String>signatureParameterMap=new HashMap<>(parameters);
        signatureParameterMap.remove("oauth_signature");
        return OAuthUtil.buildHttpQuery(signatureParameterMap);
    }

    public String buildAuthorizationHeader(){
        StringBuffer oauthHeader=new StringBuffer();
        oauthHeader.append("OAuth ");

        for(Map.Entry<String,String> entry:parameters.entrySet()){
           if(!entry.getKey().startsWith("oauth_")){
              continue;
           }
            oauthHeader.append(OAuthUtil.percentString(entry.getKey())).append("=\"");
            oauthHeader.append(OAuthUtil.percentString(entry.getValue())).append("\",");
        }
        String header=oauthHeader.toString();
        return header.substring(0,header.length()-1);

    }

    public String buildGetQuery(){
        return OAuthUtil.buildHttpQuery(parameters);
    }


}
