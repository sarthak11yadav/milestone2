package net.milestone2.Utilities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class MyResponse {

    private String msg;
    private HttpStatus status;

    public MyResponse(String msg,HttpStatus status) {

        this.msg=msg;
        this.status=status;
    }
}
