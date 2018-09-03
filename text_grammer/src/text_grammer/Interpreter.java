package text_grammer;

import java.io.FileNotFoundException;


public class Interpreter
{

	public static void main(String[] args)
	{
		try
		{
			Parser p = new Parser("expr1.txt");
			Assignment assn = p.parse();
			assn.execute();
			Memory.displayMemory(); // to see results of assignment statement
		}
		catch (ParserException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (LexicalException e)
		{
			e.printStackTrace();
		}
	}

}
