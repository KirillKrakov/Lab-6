package common.communication;

import java.io.Serializable;

/**
 * Class for get response value.
 */
public class Response implements Serializable {
    private ResponseCode responseCode;
    private String responseBody;

    public Response(ResponseCode responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    /**
     * @return Response сode.
     */
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @return Response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + ", " + responseBody + "]";
    }

    public byte[] getBytes() {
        return (responseCode.toString() + "~~~" + responseBody).getBytes();
    }

    public static Response outOfString(String arg) {
        String[] response = arg.split("~~~");
        if (response.length > 1) {
            return new Response(ResponseCode.outOfString(response[0]), response[1]);
        } else {
            return new Response(ResponseCode.outOfString(response[0]), "");
        }
    }
}
