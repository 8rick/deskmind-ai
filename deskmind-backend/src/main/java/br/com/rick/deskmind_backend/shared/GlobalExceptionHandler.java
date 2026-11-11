package br.com.rick.deskmind_backend.shared;

import br.com.rick.deskmind_backend.ticket.InvalidTicketStateException;
import br.com.rick.deskmind_backend.ticket.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ProblemDetail handleTicketNotFoundException(TicketNotFoundException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Chamado não encontrado");
        return problem;
    }

    @ExceptionHandler(InvalidTicketStateException.class)
    public ResponseEntity<ProblemDetail> handleInvalidTicketState(InvalidTicketStateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Regra de negócio violada");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
