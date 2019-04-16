package com.example.anpuservice.vo;

import java.io.Serializable;

public class ResponseBodyVO implements Serializable {
    private static final long serialVersionUID = -8145865776285690954L;
    public static final int DEFAULT_STATUS_CODE = 200;
    private static final String DEFAULT_MESSAGE = "success";
    private int code;
    private String message;
    private Object data;

    public ResponseBodyVO() {
    }

    private ResponseBodyVO(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseVOBuilder response() {
        return new ResponseVOBuilder();
    }

    public static ResponseVOBuilder response(int status_code, String message) {
        return new ResponseVOBuilder(status_code, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class ResponseVOBuilder {
        private int code = DEFAULT_STATUS_CODE;
        private String message = DEFAULT_MESSAGE;
        private Object data;

        public ResponseVOBuilder() {
            super();
        }

        public ResponseVOBuilder(int code, String message) {
            super();
            this.code = code;
            this.message = message;
        }

        public ResponseVOBuilder setode(int code) {
            this.code = code;
            return this;
        }

        public ResponseVOBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseVOBuilder setData(Object data) {
            this.data = data;
            return this;
        }

        public ResponseBodyVO build() {
            return new ResponseBodyVO(code, message, data);
        }
    }

    @Override
    public String toString() {
        return "ResponseBodyVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}