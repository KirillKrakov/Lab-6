package common.communication;

/**
 * Enum of response codes
 */
public enum ResponseCode {
    OK,
    ERROR;
    public static ResponseCode outOfString(String arg) {
        for (ResponseCode x : new ResponseCode[]{ResponseCode.OK, ResponseCode.ERROR}) {
            if (x.toString() == arg) {
                return x;
            }
        }
        return null;
    }
}