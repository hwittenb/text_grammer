package text_grammer;

public class Constant implements Expression
{

	private int value;
	
	public Constant(int value)
	{
		this.value = value;
	}

	@Override
	public int evaluate()
	{
		return value;
	}

}