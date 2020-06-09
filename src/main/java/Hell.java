import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;

public class Hell {
    public static void main(String[] args){
        // Get api key from https://app.platerecognizer.com/start/ and replace MY_API_KEY
        String token = "81edc6948e107234a051807144b8659816e56cea";
        String file = "C:\\Users\\Sameen\\Desktop\\20.jpg";

        try{
            kong.unirest.HttpResponse<String> response = Unirest.post("https://api.platerecognizer.com/v1/plate-reader/")
                    .header("Authorization", "Token "+token)
                    .field("upload", new File(file))
                    .asString();
            System.out.println("Recognize:");
            System.out.println(((kong.unirest.HttpResponse) response).getBody().toString());
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            HttpResponse<String> response = Unirest.get("https://api.platerecognizer.com/v1/statistics/")
                    .header("Authorization", "Token "+token)
                    .asString();
            System.out.println("Usage:");
            System.out.println(((kong.unirest.HttpResponse) response).getBody().toString());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
