package c.java.inheritance;

public interface Complex {
	double real();
	double imaginary();
	double modulus();
	double argument();
	static Complex fromRect(double re, double im) {
		return new ComplexRect(re, im);
	}
	static Complex fromPolar(double mod, double arg) {
		return new ComplexPolar(mod, arg);
	}
	default boolean isReal() {
		return imaginary()==0;
	}
}
