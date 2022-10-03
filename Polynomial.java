import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

class Polynomial{
	
	double [] coefficients = new double[100];
	int [] exponent = new int[100];

	public Polynomial()
	{
		coefficients[0] = 0;
		exponent[0] = 0; 
	}
	
	public Polynomial(double [] values, int [] power)
	{
		for (int i = 0; i < values.length; i++)
		{
			coefficients[i] = values[i];
			exponent[i] = power[i];
		}
	}

	public Polynomial(File f) throws Exception{
		
		FileReader input = new FileReader(f);
		BufferedReader reading = new BufferedReader(input);
		String line = reading.readLine();
		String [] line2 = line.split("[+-]");
		int current = 0;
		String sign = "";
		if (!line.substring(0, 1).equals("+") && !line.substring(0, 1).equals("-"))
		{
			sign = sign.concat("n");
		}
		else
		{
			line2 = line.substring(1).split("[+-]");
		}
		for (int i = 0; i < line.length(); i++)
		{
			if (line.substring(i, i + 1).equals("+") || line.substring(i, i + 1).equals("-"))
			{
				sign = sign.concat(line.substring(i, i + 1));
			}
		}
		for(String x:line2)
		{
			if (x.contains("x"))
			{
				String [] line3 = x.split("x");
				if (!sign.substring(current, current + 1).equals("n"))
				{
					coefficients[current] = Double.parseDouble(sign.substring(current, current + 1).concat(line3[0]));
				}
				else
				{
					coefficients[current] = Double.parseDouble(line3[0]);
				}
				exponent[current] = Integer.parseInt(line3[1]);
				current++;
			}
			else
			{
				if (!sign.substring(current, current + 1).equals("n"))
				{
					coefficients[current] = Double.parseDouble(sign.substring(current, current + 1).concat(x));
				}
				else
				{
					coefficients[current] = Double.parseDouble(x);
				}
				exponent[current] = 0;
				current++;
			}

		}
		input.close();
		
		int index = 0;
		for (int i = 0; i < coefficients.length; i++)
		{
			if (coefficients[i] == 0)
			{
				index = i;
				break;
			}
		}

		double finalc [] = new double[index];
		int finale [] = new int[index];

		for (int i = 0; i < index; i++)
		{
			finalc[i] = coefficients[i];
			finale[i] = exponent[i];
		}

		coefficients = finalc.clone();
		exponent = finale.clone();
	}

	
	public Polynomial add(Polynomial p)
	{
		if (coefficients[0] == 0)
		{
			return p;
		}

		if (p.coefficients[0] == 0){
			return this;
		}


		Polynomial pnew = new Polynomial(coefficients, exponent);

		int current = 0;

		for (int n = 0; n < pnew.coefficients.length; n++)
		{
			if (pnew.coefficients[n] == 0) {
				current = n;
				break;
			}
		}

		int max = 0;
		for (int z = 0; z < p.coefficients.length; z++)
		{
			if (p.coefficients[z] == 0) {
				max = z;
				break;
			}
		} 
		
		for(int i = 0; i < max; i++)
		{
			boolean found = false;
			for(int j = 0; j < current; j++)
			{
				if (p.exponent[i] == pnew.exponent[j])
				{
					pnew.coefficients[j] += p.coefficients[i];
					found = true;
				}
			}

			if (found == false)
			{
				pnew.coefficients[current] = p.coefficients[i];
				pnew.exponent[current] = p.exponent[i];
				current++;
			}
		}

		Polynomial result = new Polynomial();
		int index = 0;

		for(int a = 0; a < pnew.coefficients.length; a++)
		{
			if(pnew.coefficients[a] != 0)
			{
				result.coefficients[index] = pnew.coefficients[a];
				result.exponent[index] = pnew.exponent[a];
				index++;
			}
		}

		double finalc [] = new double[index];
		int finale [] = new int[index];

		for (int j = 0; j < finalc.length; j++)
		{
			finalc[j] = result.coefficients[j];
			finale[j] = result.exponent[j];
		}

		result.coefficients = finalc.clone();
		result.exponent = finale.clone();
		return result;
	}

	public double evaluate(double x){
		double result = 0;
		
		for (int i = 0; i < coefficients.length; i++){
			result += coefficients[i]*Math.pow(x, exponent[i]);
		}
		
		return result;
	
	}

	public boolean hasRoot(double x){

		if (this.evaluate(x) == 0){
			return true;
		}

		return false;

	}

	public Polynomial multiply(Polynomial p)
	{
		Polynomial pnew = new Polynomial();

		int max1 = 0;

		for(int x = 0; x < coefficients.length; x++)
		{
			if(coefficients[x] == 0)
			{
				max1 = x;
				break;
			}
		}
		
		int max2 = 0;
		for(int y = 0; y < p.coefficients.length; y++)
		{
			if(p.coefficients[y] == 0)
			{
				max2 = y;
				break;
			}
		}
		for (int i = 0; i < max1; i++)
		{
		
			double [] newc = new double[100];
			int [] newe = new int[100];

			for (int j = 0; j < max2; j++)
			{
				newc[j] = coefficients[i] * p.coefficients[j];
				newe[j] = exponent[i] + p.exponent[j];
			}

			Polynomial result = new Polynomial(newc, newe);

			pnew = pnew.add(result);

		
		}

		return pnew;		
	}
	
	public void saveToFile(String file_name) throws FileNotFoundException
	{
		int max = 0;
		String poly = "";
		for(int x = 0; x < coefficients.length; x++)
		{
			if(coefficients[x] == 0)
			{
				max = x;
				break;
			}
		}
		
		for(int i = 0; i < max; i++)
		{
			if(exponent[i] == 0)
			{
				if (poly.compareTo("") == 0)
				{
					if (coefficients[i] > 0)
					{
						poly = poly.concat(Double.toString(coefficients[i]));
					}
					else
					{
						poly = poly.concat("-" + Double.toString(coefficients[i]));
					}
					
				}
				else
				{
					if (coefficients[i] > 0)
					{
						poly = poly.concat("+" +Double.toString(coefficients[i]));
					}
					else
					{
						poly = poly.concat("-" +Double.toString(coefficients[i]));
					}
				}
			}
			else
			{
				if (poly.compareTo("") == 0)
				{
					if (coefficients[i] > 0)
					{
						poly = poly.concat(Double.toString(coefficients[i]) + "x" + String.valueOf(exponent[i]));
					}
					else
					{
						poly = poly.concat("-" + Double.toString(coefficients[i]) + "x" + String.valueOf(exponent[i]));
					}
					
				}
				else
				{
					if (coefficients[i] > 0)
					{
						poly = poly.concat("+" + Double.toString(coefficients[i]) + "x" + String.valueOf(exponent[i]));
					}
					else
					{
						poly = poly.concat("-" + Double.toString(coefficients[i]) + "x" + String.valueOf(exponent[i]));
					}
				}
			}
		}

		PrintStream ps = new PrintStream(file_name);
		ps.println(poly);
		ps.close();
	}

	
}