package c.java.inheritance;

public class ComplexRect implements Complex{
	private double im, re;
	
	public ComplexRect(double re, double im) {
		this.im=im;
		this.re=re;
	}
	@Override
	public double real() {
		// TODO Auto-generated method stub
		return re;
	}

	@Override
	public double imaginary() {
		// TODO Auto-generated method stub
		return im;
	}

	@Override
	public double modulus() {
		// TODO Auto-generated method stub
		return Math.atan2(im, re);
	}

	@Override
	public double argument() {
		// TODO Auto-generated method stub
		return Math.sqrt(re*re+im*im);
	}
}
