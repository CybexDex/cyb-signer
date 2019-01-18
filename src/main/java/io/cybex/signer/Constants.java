package io.cybex.signer;

public class Constants {
    public static class ReturnCode {
        public static String RES_KEY_STATUS = "Status";
        public static String RES_KEY_MSG = "Message";
        public static String RES_KEY_CODE = "Code";
        public static String RES_VALUE_ERROR = "Error";
        public static String RES_VALUE_FAILED = "Failed";
        public static String RES_VALUE_SUCC = "Successful";
    }

    public static class TransactionType {
        public static final String NewLimitOrder = "NewLimitOrder";
        public static final String Cancel = "Cancel";
        public static final String CancelAll = "CancelAll";
    }

    public static class TOPICS {
        public static final String INIT_REF_DATA = "INIT_REF_DATA";
    }

    public static class SIDE {
        public static final String BUY = "buy";
        public static final String SELL = "sell";
    }
}
