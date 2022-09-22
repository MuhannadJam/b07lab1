class Polynomial {
	
	double [] coefficients = new double[100];

	public Polynomial()
	{
		coefficients[0] = 0;
	}
	
	public Polynomial(double [] values)
	{
		for (int i = 0; i < values.length; i++)
		{
			coefficients[i] = values[i];
		}
	}

	
	public Polynomial add(Polynomial p)
	{
		double [] sum = new double[100];
		
		for (int i = 0; i < coefficients.length; i++)
		{
			sum[i] = coefficients[i] + p.coefficients[i];
		}
		
		Polynomial pnew = new Polynomial(sum);

		return pnew;
	}

	public double evaluate(double x){
		double result = 0;
		
		for (int i = 0; i < coefficients.length; i++){
			result += coefficients[i]*Math.pow(x, i);
		}
		
		return result;
	
	}

	public boolean hasRoot(double x){

		if (this.evaluate(x) == 0){
			return true;
		}

		return false;

	}

}