package br.com.gori.scb.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.gori.scb.domain.exception.EntidadeNaoEncontradaException;
import br.com.gori.scb.domain.exception.NegocioException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = createListFieldProblem(ex.getBindingResult().getAllErrors());
		
		var problema = createBodyProblem(status.value(),"Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente",campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		var problema = createBodyProblem(status.value(),ex.getMessage(),null);
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		var problema = createBodyProblem(status.value(),ex.getMessage(),null);
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	private List<Problema.Campo> createListFieldProblem(List<ObjectError> errors){
		List<Problema.Campo> campos = new ArrayList<>();
		
		for (ObjectError error : errors) {
			String nome = ((FieldError) error).getField();
			//String mensagem = error.getDefaultMessage(); //Valor de mensagem default da validacao
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale()); //Valor de mensagem do arquivo message.properties
			
			campos.add(new Problema.Campo(nome,mensagem));
		}
		
		return campos;
	}
	
	private Problema createBodyProblem(int status, String titulo, List<Problema.Campo> campos) {

		Problema problema = new Problema();
		problema.setStatus(status);
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(titulo);
		problema.setCampos(campos);
		
		return problema;
		
	}
}