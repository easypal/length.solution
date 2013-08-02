package easypal.javer.length;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;

public class Length
{
	public static void main(String[] args) throws Exception
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintStream pw = System.out;
		
		Map<String,Double> conv = parseConvertor(br);
		/*			for (String k : conv.keySet()) {
				System.out.println(k + ":" + conv.get(k));
			}*/
		String line;
		while (null != (line = br.readLine())) {
			String exp[] = line.split("\\s");
			Length length = new Length();
			length.setConvertor(conv);

			int pos = 0;
			//System.out.println(exp.length);
			
			if (2 <= exp.length) {
				length.add(Length.parseLength(exp[0], exp[1]));
				pos += 2;
			} else {
				break;
			}
			while (pos < exp.length) {
				if (pos + 3 <= exp.length) {
					String op = exp[pos];
					
					if ("-".equals(op)) {
						length.minus(Length.parseLength(exp[pos + 1], exp[pos + 2]));
					} else if ("+".equals(op)) {
						length.add(Length.parseLength(exp[pos + 1], exp[pos + 2]));
					} else {
					}
					pos += 3;
				} else {
					break;
				}
			}
			pw.println(length.toString());
			
			
		}
	}
	
	protected static Map<String,Double> parseConvertor(BufferedReader br) throws IOException {
		Map<String, Double> conv  = new HashMap<String, Double>();
		String line;
		while (null != (line = br.readLine())) {
			if ("".equals(line)) break;
			
			String[] equ = line.split("\\s");
			conv.put(equ[1], Double.parseDouble(equ[3]));
		}
		return conv;
	}
	
	private double quantity;
	private String unit;
	private Map<String, Double> convertor  = new HashMap<String, Double>();
	private static final Map<String, String> pluralToNonplural;
	
	static {
		pluralToNonplural = new HashMap<String,String>();
		pluralToNonplural.put("miles", "mile");
		pluralToNonplural.put("yards", "yard");
		pluralToNonplural.put("inches", "inch");
		pluralToNonplural.put("furlongs", "furlong");
		pluralToNonplural.put("faths", "fath");
		pluralToNonplural.put("feet", "foot");
	}
	
	public Length() {
		this(0.0,"m");
	}
	public Length(double quantity, String unit) {
		this.quantity = quantity;
		this.unit = unit;
	}
	public double getQuantity()
	{
		return quantity;
	}
	public String getUnit()
	{
		return unit;
	}
	public Map<String, Double> getConvertor() {
		return convertor;
	}
	public void setConvertor(Map<String, Double> conv) {
		convertor = conv;
	}
	public Length add(Length l) {
		this.quantity += l.getQuantity() * convertor.get(l.getUnit());
		return this;
	}
	public Length minus(Length l)
	{
		this.quantity -= l.getQuantity() * convertor.get(l.getUnit());
		return this;
	}
	public static Length parseLength(String quantity, String unit) {
		String nonplural = pluralToNonplural.get(unit);
		if (nonplural == null) nonplural = unit;
		return new Length(Double.parseDouble(quantity), nonplural);
	}
	@Override
	public String toString()
	{	
		return _round(this.quantity, 2) + " " + this.unit;

	}
	
	private String _round(double v, int fra)
	{
		StringBuffer sb = new StringBuffer(Double.toString(v));
		int d = sb.indexOf(".");
		
		if (d == -1) {
			sb.append(".00");
		} else if (sb.length() < d + fra + 1) {
			for (int i = 0; i < fra - (sb.length() - (d + 1)); i++)
				sb.append("0");
		} else if (sb.length() == d + fra + 1) {
		} else {
			boolean carry = false;
			int i = d + fra + 1;
			if (sb.charAt(i) >= '5') carry = true;

			while (i > 1 && carry) {
				i--;
				if (sb.charAt(i) == '.') continue;
				
				if (sb.charAt(i) >= '9') {
					sb.setCharAt(i, '0');
					carry = true;
				} else {
					sb.setCharAt(i, (char)((byte)(sb.charAt(i)) + 1));
					carry = false;
				}
				
				
			}
		}
		
		return sb.substring(0, d + fra + 1);
		
	}
	
	
	
}
