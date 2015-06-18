package week8.lesson2.Task1;

import java.util.ArrayList;

//******************************************************************
public class MainCalculator {
	public static void main(String[] args) {
		// Создаем экземпляр класса MyOpFactory

		AbstractProcessor processor = new MyProcessor();
		Calc calc = new Calc(processor);

		calc.inSymv('1');
		calc.inSymv('2');
		calc.inSymv('+');
		calc.inSymv('4');
		calc.inSymv('5');
		calc.inSymv('=');
	}
}

// ******************************************************************
class Calc {
	AbstractProcessor proc;

	public Calc(AbstractProcessor pr) {
		proc = pr;
	}

	public void inSymv(Character c) {
		proc.inputChar(c);
		proc.printResult();
	}

	// Это вызывать из графики
	public String calcSymv(Character c) {
		proc.inputChar(c);
		return Integer.toString(proc.getResult());
	}
}

// ******************************************************************
interface Processorable {
	public void inputChar(Character c);

	public void printResult();
}

// ******************************************************************
abstract class AbstractProcessor implements Processorable {
	private int result;
	private int temp;

	@Override
	public void printResult() {
		System.out.println("\tResult = " + getResult());
	}

	protected void setResult(int r) {
		result = r;
	}

	public int getResult() {
		return result;
	}
}

// ******************************************************************
abstract class CharCalculator {
	private final static Character[] charNumeric = new Character[] { '0', '1',
	        '2', '3', '4', '5', '6', '7', '8', '9' };
	private final static Character[] charOperation = new Character[] { '+',
	        '-', '*', '/', '=' };

	public static boolean charIsNumber(Character c) {
		for (int i = 0; i < charNumeric.length; i++) {
			if (charNumeric[i].equals(c))
				return true;
		}
		return false;
	}

	public boolean charIsOperation(Character c) {
		for (int i = 0; i < charOperation.length; i++) {
			if (charNumeric[i].equals(c))
				return true;
		}
		return false;
	}

}

// ******************************************************************
class MyProcessor extends AbstractProcessor {
	private Integer result = 0;
	private ArrayList<Object> list = new ArrayList<Object>();

	public MyProcessor() {
		list.clear();
	}

	private void arithmeticOp(Character c) {
		if (c != '=') {
			// {1.Число}
			// {1.Число} {2.Оператор}
			// {1.Число} {2.Оператор} {3.Число}
			// {1.Число} {2.Оператор} {3.Число} {4.Оператор} -> {1.Число |
			// Результат} {2.Оператор}
			switch (list.size()) {
			case 0:
				list.add((Integer) result);
				list.add(c);
				break;
			case 1:
			case 2:
				list.add(c);
				break;
			case 3:
				result = calcOperation((Integer) list.get(0),
				        (Character) list.get(1), (Integer) list.get(2));
				list.set(0, (Integer) result);
				list.remove(2);
				break;
			}
		} else {
			// {1.Число}
			// {1.Число} {2.Оператор} -> {1.Число | Результат}
			// {1.Число} {2.Оператор} {3.Число} -> {1.Число | Результат}
			// {1.Число} {2.Оператор} {3.Число} -> {1.Число | Результат}
			Integer valY = 0;
			switch (list.size()) {

			case 2:
				valY = (Integer) list.get(0);
			case 3:
				valY = (Integer) list.get(2);
				break;
			}
			if (list.size() > 1) {
				result = calcOperation((Integer) list.get(0),
				        (Character) list.get(1), (Integer) valY);
				list.clear();				
			}
		}
	}

	private Integer calcOperation(Integer valX, Character oper, Integer valY) {
		switch (oper) {
		case '+':
			return valX + valY;
		case '-':
			return valX - valY;
		case '*':
			return valX * valY;
		case '/':
			return valX / valY;
		}
		return 0;
	}

	private void addNum(Character c) {
		Integer num = (Integer) Character.getNumericValue(c);
		switch (list.size()) {
		case 0:
		case 2:			
			list.add(num);
			result = num;
			break;
		case 1:
		case 3:
			Integer value = (Integer) list.get(list.size() - 1);
			result =  (value * 10 + num); 
			list.set(list.size() - 1, (Integer) result);
			break;
		}
	}

	@Override
	public void inputChar(Character c) {
		if (CharCalculator.charIsNumber(c)) {
			System.out.print(c + " is Number");
			addNum(c);
		} else {
			System.out.print(c + " is Operation");
			arithmeticOp(c);
		}
		printAbout();
		// setResult(c - (int) ('0'));
	}

	@Override
	public int getResult() {
		return result;
	}

	public void printAbout() {
		System.out.println();
		System.out.print("Length=" + list.size() + "\t\t");
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print("\t\t" + "'" + list.get(i) + "'");
			}
		} else {
			System.out.print("Array list empty");
		}
		System.out.println();
	}
}
