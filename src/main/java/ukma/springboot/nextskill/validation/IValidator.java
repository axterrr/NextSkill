package ukma.springboot.nextskill.validation;

public interface IValidator<T>{
    boolean isValid(T value);
}
