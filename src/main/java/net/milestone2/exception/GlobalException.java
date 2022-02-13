package net.milestone2.exception;


import net.milestone2.Utilities.MyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyResponse> ResourceNotFoundException(ResourceNotFoundException resourceNotFoundException)
    {
        MyResponse res=new MyResponse(resourceNotFoundException.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(res);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MyResponse> BadRequest(BadRequestException badRequestException){
        MyResponse res=new MyResponse(badRequestException.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(res);
    }



}
