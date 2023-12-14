import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @ControllerAdvice
// public class NotFoundExceptionHandler {

//     @ExceptionHandler(NoHandlerFoundException.class)
//     public void handleNotFoundException(HttpServletResponse response) throws IOException {
//         response.sendRedirect("/404.html");
//     }
// }