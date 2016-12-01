package ca.ljz.demo.unwrappers;

public interface ExceptionUnwrapper<T extends Throwable> {
	public T unwrap(Throwable t);
}
