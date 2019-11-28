package c.java.inheritance;

public class ComplexPolar implements Complex {
	private double mod, arg;
	public ComplexPolar(double mod, double arg) {
		this.mod=mod;
		this.arg=arg;
	}
	@Override
	public double real() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double imaginary() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double modulus() {
		// TODO Auto-generated method stub
		return mod;
	}

	@Override
	public double argument() {
		// TODO Auto-generated method stub
		return arg;
	}

}
