package io.bootify.my_app.exception;

public class OtpExpiredException extends Throwable {
    public OtpExpiredException(String otpHasExpired)   {
        super(otpHasExpired);
    }
}
